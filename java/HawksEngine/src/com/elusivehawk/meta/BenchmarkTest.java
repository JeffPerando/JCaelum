
package com.elusivehawk.meta;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.PrintStream;
import java.nio.ByteBuffer;
import org.lwjgl.BufferUtils;
import com.elusivehawk.engine.core.FileHelper;
import com.elusivehawk.engine.core.GameLog;

/**
 * 
 * Test log:
 * 
 * Testing sound decoding.
 * Testing file byte reading.
 * Testing "++".
 * Using PrintStream.
 * More buffer testing.
 * List iterating.
 * Buffer.put(int, int) testing.
 * Instanceof speed benchmarking.
 * 
 * @author Elusivehawk
 */
public class BenchmarkTest
{
	public static final int TESTS = 1024;
	
	public static void main(String[] args)
	{
		GameLog.info("Beginning bench testing...");
		
		File file = FileHelper.createFile(".", "Test_sound3.ogg");
		File log = FileHelper.createFile(".", "Log.txt");
		
		try
		{
			if (!log.exists() && !log.createNewFile())
			{
				return;
			}
			
		}
		catch (Exception e)
		{
			GameLog.error(e);
			
			return;
		}
		
		try
		{
			PrintStream ps = new PrintStream(log);
			
			GameLog.EnumLogType.DEBUG.addOutput(ps);
			
		}
		catch (Exception e)
		{
			GameLog.error(e);
			
		}
		
		FileInputStream fis = FileHelper.createStream(file);
		
		if (fis != null)
		{
			BufferedInputStream is = new BufferedInputStream(fis);
			
			try
			{
				ByteBuffer buf = BufferUtils.createByteBuffer(is.available());
				
				while (buf.remaining() != 0)
				{
					buf.put((byte)is.read());
					
				}
				
				buf.flip();
				
				while (buf.remaining() != 0)
				{
					byte b = buf.get();
					
					GameLog.debug("Position: " + buf.position() + ", byte: " + b + ", char: " + (char)b);
					
				}
				
			}
			catch (Exception e)
			{
				GameLog.error(e);
				
			}
			
		}
		
		
		GameLog.info("Th-th-th-th-That's all, folks!");
		
	}
	
}
