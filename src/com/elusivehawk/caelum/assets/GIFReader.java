
package com.elusivehawk.caelum.assets;

import java.io.DataInputStream;
import java.nio.ByteBuffer;
import java.util.List;
import com.elusivehawk.caelum.render.ILegibleImage;
import com.elusivehawk.caelum.render.tex.Color;
import com.elusivehawk.caelum.render.tex.ColorFormat;
import com.elusivehawk.util.storage.BufferHelper;
import com.elusivehawk.util.storage.Tuple;
import com.google.common.base.Charsets;
import com.google.common.collect.Lists;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class GIFReader implements IAssetReader
{
	public static final int
				COLOR_TABLE_SIZE =	0b00000111,
				SORT_FLAG =			0b00001000,
				COLOR_RES =			0b01110000,
				COLOR_TABLE_FLAG =	0b10000000,
				//GCE flags
				TRANSPARENT_FLAG =	0b00000001,
				REQUIRES_USER_IN =	0b00000010,
				DISPOSAL_METHOD =	0b00011100,
				RESERVED =			0b11100000,
				//Image Descriptor flags
				L_COLOR_TABLE_FLAG=	0b10000000,
				INTERLACE =			0b01000000,
				SORT_IMG_FLAG =		0b00100000,
				RESERVED_IMG =		0b00011000,
				L_COLOR_TABLE_SIZE=	0b00000111;
	
	@Override
	public Object readAsset(DataInputStream in) throws Throwable
	{
		byte[] sigBytes = new byte[3];
		
		in.read(sigBytes);
		
		String sig = new String(sigBytes, Charsets.US_ASCII);
		
		if (!"GIF".equals(sig))
		{
			throw new RuntimeException("Signature is not \"GIF\"!");
		}
		
		byte[] verBytes = new byte[2];
		
		in.read(verBytes);
		
		int version = Integer.parseInt(new String(verBytes, Charsets.US_ASCII));
		char extra = in.readChar();
		
		int width = Short.toUnsignedInt(in.readShort());
		int height = Short.toUnsignedInt(in.readShort());
		
		int flags = in.read();
		
		boolean colorTable = (flags & COLOR_TABLE_FLAG) != 0;
		boolean sorted = (flags & SORT_FLAG) != 0;
		
		int bgColor = in.read();
		
		Color[] table = null;
		
		if (colorTable)
		{
			table = new Color[(int)Math.pow(2, ((flags & COLOR_TABLE_SIZE) + 1))];
			
			for (int c = 0; c < table.length; c++)
			{
				table[c] = new Color(ColorFormat.RGBA, in.read(), in.read(), in.read());
				
			}
			
		}
		
		List<ILegibleImage> ret = Lists.newArrayList();
		List<Tuple<Integer, ByteBuffer>> comments = Lists.newArrayList();
		
		while (true)
		{
			int b = in.read();
			
			if (b == 0x3B)
			{
				break;
			}
			
			if (b == 0x21)
			{
				Tuple<Integer, ByteBuffer> ext = readExt(in);
				
				if (ext != null)
				{
					if (ext.one == 0xF9)
					{
						ByteBuffer data = ext.two;
						
						byte packed = data.get();
						
						float delay = (float)Short.toUnsignedInt(data.getShort()) / 100;
						int transparent = Byte.toUnsignedInt(data.get());
						
						b = in.read();
						
					}
					else
					{
						comments.add(ext);
						
					}
					
				}
				
			}
			
			if (b == 0x2C)
			{
				int x = Short.toUnsignedInt(in.readShort());
				int y = Short.toUnsignedInt(in.readShort());
				int w = Short.toUnsignedInt(in.readShort());
				int h = Short.toUnsignedInt(in.readShort());
				
				int imgFlags = in.read();
				
				boolean useLCT = (imgFlags & L_COLOR_TABLE_FLAG) != 0;
				boolean interlace = (imgFlags & INTERLACE) != 0;
				boolean sort = (imgFlags & SORT_IMG_FLAG) != 0;
				
				Color[] colors;
				
				if (useLCT)
				{
					colors = new Color[(int)Math.pow(2, ((imgFlags & L_COLOR_TABLE_SIZE) + 1))];
					
					for (int c = 0; c < colors.length; c++)
					{
						colors[c] = new Color(ColorFormat.RGBA, in.read(), in.read(), in.read());
						
					}
					
				}
				else
				{
					colors = table;
					
				}
				
				//TODO Read image data
				
			}
			
		}
		
		return ret;
	}
	
	private static Tuple<Integer, ByteBuffer> readExt(DataInputStream in) throws Throwable
	{
		int type = in.read();
		
		byte[] data = new byte[in.read()];
		
		in.read(data);
		
		if (in.read() != 0)
		{
			return null;
		}
		
		return Tuple.create(type, BufferHelper.createWrapper(data));
	}
	
}
