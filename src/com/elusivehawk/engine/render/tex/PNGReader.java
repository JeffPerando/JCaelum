
package com.elusivehawk.engine.render.tex;

import java.nio.ByteBuffer;
import java.util.List;
import com.elusivehawk.engine.CaelumException;
import com.elusivehawk.engine.Experimental;
import com.elusivehawk.engine.render.LegibleByteImage;
import com.elusivehawk.util.BufferHelper;
import com.elusivehawk.util.io.IByteReader;
import com.elusivehawk.util.storage.Tuple;
import com.google.common.collect.Lists;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
@Experimental
public class PNGReader
{
	public static final long HEADER = 0x89504E470D0A1A01L;
	
	public static LegibleByteImage readPNG(IByteReader r) throws Throwable
	{
		long header = r.readLong();
		
		if (header != HEADER)//Check the header
		{
			throw new CaelumException("PNG file is corrupt: Header did not match! Header: 0x%s, expected: 0x%s", Long.toHexString(header), Long.toHexString(HEADER));
		}
		
		Tuple<String, ByteBuffer> first = readChunk(r);//Read one chunk
		
		assert first != null;
		assert first.one.equals("IHDR");//If the name of the first chunk isn't IHDR, we throw a hissy fit. Literally.
		
		//We read the necessary information, e.g. width, height, compression, etc.
		
		ByteBuffer br = first.two;
		
		int width = br.getInt();
		int height = br.getInt();
		
		if (width == 0 || height == 0)
		{
			return null;
		}
		
		byte depth = br.get();
		byte colorType = br.get();
		byte comp = br.get();
		byte filter = br.get();
		byte interlace = br.get();
		
		br.rewind();
		
		//Now we read the remaining chunks
		
		List<Tuple<String, ByteBuffer>>
					unused = Lists.newArrayList(),
					idats = Lists.newArrayList();
		
		boolean foundPalette = false;
		
		Tuple<String, ByteBuffer> chunk = null;
		ByteBuffer buf = null;
		List<Color> palette = Lists.newArrayList();
		LegibleByteImage ret = new LegibleByteImage(width, height);
		
		decloop: while (r.remaining() > 0)
		{
			chunk = readChunk(r);
			
			if (chunk == null)
			{
				continue;
			}
			
			buf = chunk.two;
			
			if (Character.isUpperCase(chunk.one.charAt(2)))
			{
				unused.add(chunk);
				continue;
			}
			
			if (!Character.isUpperCase(chunk.one.charAt(0)))
			{
				unused.add(chunk);
				continue;
			}
			
			switch (chunk.one)
			{
				case "IEND": {
					if (r.remaining() > 0)
						throw new CaelumException("IEND is not last chunk! This is a bug!");
					if (idats.isEmpty())
						throw new CaelumException("Did not find IDAT chunk!");
					
					break decloop;
				}
				case "PLTE": {
					if (foundPalette)
						throw new CaelumException("Extra palette chunk found");
					if (!idats.isEmpty())
						throw new CaelumException("Cannot use palette chunk after IDAT chunks start to be used");
					if (buf.capacity() % 3 != 0)
						throw new CaelumException("Palette size is not divisible by 3");
					
					foundPalette = true;
					
					while (buf.remaining() > 0)
					{
						palette.add(new Color(ColorFormat.RGBA, buf.get(), buf.get(), buf.get()));
						
					}
					
					break;
				}
				case "IDAT":
					idats.add(chunk);
					break;
				default: throw new CaelumException("Unknown chunk name found: %s", chunk.one);
				
			}
			
		}
		
		//TODO Use unused chunks
		
		return ret;
	}
	
	private static Tuple<String, ByteBuffer> readChunk(IByteReader r) throws Throwable
	{
		int size = r.readInt();
		
		char[] type = new char[4];
		
		for (int c = 0; c < 4; c++)
		{
			type[c] = (char)r.read();
			
		}
		
		byte[] data = r.read(size);
		
		int crc = r.readInt();
		
		//TODO Return null if CRC check fails
		
		return Tuple.create(new String(type), BufferHelper.createWrapper(data));
	}
	
}
