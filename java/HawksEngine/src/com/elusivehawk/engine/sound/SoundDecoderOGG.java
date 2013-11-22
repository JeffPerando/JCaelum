
package com.elusivehawk.engine.sound;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import org.lwjgl.BufferUtils;
import com.elusivehawk.engine.core.BufferHelper;
import com.elusivehawk.engine.core.GameLog;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class SoundDecoderOGG implements ISoundDecoder
{
	@Override
	public int decodeSound(File file)
	{
		return 0;
	}
	
	public OggPage[] decode(File file)
	{
		FileInputStream fis = null;
		
		try
		{
			fis = new FileInputStream(file);
			
		}
		catch (FileNotFoundException e)
		{
			GameLog.error(e);
			
			return null;
		}
		
		BufferedInputStream is = new BufferedInputStream(fis);
		
		ByteBuffer buf = null;
		
		try
		{
			byte[] data = new byte[is.available()];
			
			buf = BufferHelper.makeByteBuffer(is.read(data), data);
			
		}
		catch (Exception e)
		{
			GameLog.error(e);
			
		}
		
		//int id = 0;
		OggPage[] ret = null;
		
		if (buf != null)
		{
			GameLog.debug("Byte count: " + buf.capacity());
			
			List<OggPage> unsortedPages = new ArrayList<OggPage>();
			
			while (buf.remaining() != 0)
			{
				try
				{
					String capture = ""; //Capture pattern
					
					for (int c = 0; c < 4; c++)//Converts the byte array into a String.
					{
						capture += (char)buf.get();
						
					}
					
					if (!capture.equals("OggS")) //OggS is what the capture pattern needs to be.
					{
						GameLog.warn("Sound file corrupted: " + file.getAbsolutePath() + ", capture pattern is not OggS. Instead, it's " + capture + ".");
						
						for (char c : capture.toCharArray())
						{
							GameLog.warn("Corrupted byte: " + (byte)c);
							
						}
						
						break;
					}
					
					if (buf.get() != 0) //The next byte is for the version.
					{
						GameLog.warn("Sound file corrupted: " + file.getAbsolutePath() + ", version is not 0.");
						
						break;
					}
					
					EnumOGGHeaderType t = null;
					
					try
					{
						t = EnumOGGHeaderType.values()[buf.get()];
						
					}
					catch (ArrayIndexOutOfBoundsException e){}
					
					if (t == null) //If the above failed...
					{
						GameLog.warn("Sound file corrupted: " + file.getAbsolutePath() + ", invalid header type.");
						
						break; //...Stop EVERYTHING!
					}
					
					long granule = 0L;
					int serial = 0, sequence = 0, checksum = 0;
					
					for (byte c = 0; c < 8; c++)
					{
						granule = ((granule << 8) | buf.get());
						
					}
					
					GameLog.debug("Granule found: " + granule);
					
					for (byte c = 0; c < 4; c++)
					{
						serial = ((serial << 8) | buf.get());
						
					}
					
					GameLog.debug("Serial number found: " + serial);
					
					for (byte c = 0; c < 4; c++)
					{
						sequence = ((sequence << 8) | buf.get());
						
					}
					
					GameLog.debug("Sequence number found: " + sequence);
					
					for (byte c = 0; c < 4; c++)
					{
						checksum = ((checksum << 8) | buf.get());
						
					}
					
					GameLog.debug("Checksum: " + checksum);
					
					//TODO Handle checksum.
					
					byte segCount = buf.get();
					
					GameLog.debug("Segment count: " + segCount + ", remaining byte count: " + buf.remaining());
					
					ByteBuffer segments = BufferUtils.createByteBuffer(segCount);
					
					for (int c = 0; c < segCount; c++)
					{
						byte b = buf.get();
						
						GameLog.info("Byte found: " + b + ", char equivalent: " + (char)b);
						
						segments.put(b);
						
					}
					
					segments.flip();
					
					unsortedPages.add(new OggPage(segments, t));
					
				}
				catch (Exception e)
				{
					GameLog.error(e);
					
				}
				
				OggPage[] pages = new OggPage[unsortedPages.size()];
				
				for (OggPage p : unsortedPages)
				{
					switch (p.type)
					{
						case BEGIN: assert (pages[0] == null) : "Corrupted OGG file! This is a bug!"; pages[0] = p;
						case END: assert (pages[pages.length - 1] == null) : "Corrupted OGG file! This is a bug!"; pages[pages.length - 1] = p;
						default:
						{
							for (int c = 1; c < pages.length - 2; c++)
							{
								if (pages[c] == null)
								{
									pages[c] = p;
									
								}
								
							}
							
						}
						
					}
					
				}
				
				ret = pages;
				
				//TODO Continue
				
			}
			
			//TODO Set the id field.
			
		}
		
		try
		{
			fis.close();
			is.close();
			
		}
		catch (Exception e){}
		
		return ret;
	}
	
	public static enum EnumOGGHeaderType
	{
		CONTINUE, BEGIN, END;
		
	}
	
	public static class OggPage
	{
		public final ByteBuffer data;
		public final EnumOGGHeaderType type;
		
		OggPage(ByteBuffer info, EnumOGGHeaderType t)
		{
			data = info;
			type = t;
			
		}
		
	}
	
}
