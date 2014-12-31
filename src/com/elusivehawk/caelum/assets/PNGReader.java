
package com.elusivehawk.caelum.assets;

import java.io.DataInputStream;
import java.nio.ByteBuffer;
import java.util.List;
import com.elusivehawk.caelum.CaelumException;
import com.elusivehawk.caelum.Experimental;
import com.elusivehawk.caelum.render.tex.Color;
import com.elusivehawk.caelum.render.tex.ColorFormat;
import com.elusivehawk.caelum.render.tex.LegibleByteImage;
import com.elusivehawk.util.HashGen;
import com.elusivehawk.util.storage.BufferHelper;
import com.elusivehawk.util.storage.Tuple;
import com.google.common.collect.Lists;
import de.matthiasmann.twl.utils.PNGDecoder;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
@Experimental
public class PNGReader implements IAssetReader
{
	public static final long HEADER = 0x89504E470D0A1A01L;
	
	@Override
	public LegibleByteImage readAsset(DataInputStream in) throws Throwable
	{
		return null;
		/*long header = in.readLong();
		
		if (header != HEADER)//Check the header
		{
			throw new CaelumException("PNG file is corrupt: Header did not match! Header: 0x%s, expected: 0x%s", Long.toHexString(header), Long.toHexString(HEADER));
		}
		
		Tuple<String, ByteBuffer> first = readChunk(in);//Read one chunk
		
		assert first != null;
		assert first.one.equals("IHDR");//If the name of the first chunk isn't IHDR, we throw a hissy fit. Literally.
		
		//We read the necessary information, e.g. width, height, compression, etc.
		
		ByteBuffer br = first.two;
		
		int width = br.getInt();
		int height = br.getInt();
		
		assert width != 0 && height != 0;
		
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
		
		decloop: while (in.available() > 0)
		{
			chunk = readChunk(in);
			
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
					if (in.available() > 0)
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
						palette.add(new Color(ColorFormat.RGBA, buf.get(), buf.get(), buf.get()));
					
					break;
				}
				case "IDAT": idats.add(chunk); break;
				default: throw new CaelumException("Unknown chunk name found: %s", chunk.one);
				
			}
			
		}
		
		//TODO Use unused chunks
		
		return ret;*/
	}
	
	private static Tuple<String, ByteBuffer> readChunk(DataInputStream in) throws Throwable
	{
		int size = in.readInt();
		
		char[] type = new char[4];
		
		for (int c = 0; c < 4; c++)
		{
			type[c] = in.readChar();
			
		}
		
		byte[] data = new byte[size];
		
		in.read(data);
		
		long crc = Integer.toUnsignedLong(in.readInt());
		
		if (crc != HashGen.crc32(data))
		{
			return null;
		}
		
		return Tuple.create(new String(type), BufferHelper.createWrapper(data));
	}
	
}
