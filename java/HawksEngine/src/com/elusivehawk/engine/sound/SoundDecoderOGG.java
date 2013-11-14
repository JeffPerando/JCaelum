
package com.elusivehawk.engine.sound;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import org.lwjgl.BufferUtils;
import com.elusivehawk.engine.core.BufferHelper;
import com.elusivehawk.engine.core.GameLog;
import com.elusivehawk.engine.core.TextParser;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class SoundDecoderOGG implements ISoundDecoder
{
	@Override
	public ISound decodeSound(File file)
	{
		FileInputStream fis = TextParser.createStream(file);
		
		if (fis == null)
		{
			return null;
		}
		
		BufferedInputStream is = new BufferedInputStream(fis);
		
		ByteBuffer buf = null;
		
		try
		{
			int r = is.available();
			
			byte[] data = new byte[r];
			
			is.read(data);
			
			buf = BufferHelper.makeByteBuffer(data);
			
		}
		catch (Exception e)
		{
			GameLog.error(e);
			
		}
		
		ISound s = null;
		
		if (buf != null)
		{
			while (buf.remaining() != 0)
			{
				try
				{
					String capture = ""; //Capture pattern
					
					for (int c = 0; c < 4; c++)//Converts the byte array into a String.
					{
						capture += (char)buf.get();
						
					}
					
					if (capture != "OggS") //OggS is what the capture pattern needs to be.
					{
						break;
					}
					
					if (buf.get() != 0) //The next byte is for the version.
					{
						break;
					}
					
					byte h = buf.get(); //The following byte is the header type.
					boolean cont = false;
					
					for (EnumOGGHeaderType type : EnumOGGHeaderType.values()) //Checks if the header type is invalid.
					{
						if (type.ordinal() == h)
						{
							cont = true;
							
							break;
						}
						
					}
					
					if (!cont) //If the above failed...
					{
						break; //...Stop EVERYTHING!
					}
					
					cont = false;
					
					long granule = 0L;
					int serial = 0, sequence = 0, checksum = 0;
					
					for (byte c = 0; c < 8; c++)
					{
						granule = ((granule << 8) | buf.get());
						
					}
					
					for (byte c = 0; c < 4; c++)
					{
						serial = ((serial << 8) | buf.get());
						
					}
					
					for (byte c = 0; c < 4; c++)
					{
						sequence = ((sequence << 8) | buf.get());
						
					}
					
					for (byte c = 0; c < 4; c++)
					{
						checksum = ((checksum << 8) | buf.get());
						
					}
					
					//TODO Handle checksum.
					
					ByteBuffer segments = BufferUtils.createByteBuffer(buf.get());
					
					segments.put(buf);
					
					//TODO Continue
					
				}
				catch (Exception e){}
				
			}
			
			//TODO Set the s field.
			
		}
		
		try
		{
			fis.close();
			is.close();
			
		}
		catch (IOException e){}
		
		return s;
	}
	
	public static enum EnumOGGHeaderType
	{
		CONTINUE, BEGIN, END;
		
	}
	
}
