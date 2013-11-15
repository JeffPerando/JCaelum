
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import com.elusivehawk.engine.core.FileHelper;
import com.elusivehawk.engine.core.GameLog;

/**
 * 
 * Test log:
 * 
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
		
		File file = FileHelper.createFile(".", "Test_sound.ogg");
		File out = FileHelper.createFile(".", "Log.txt");
		
		try
		{
			if (!out.exists() && !out.createNewFile())
			{
				return;
			}
			
		}
		catch (IOException e)
		{
			GameLog.error(e);
			
		}
		
		try
		{
			GameLog.EnumLogType.INFO.addOutput(new PrintStream(out));
			
		}
		catch (FileNotFoundException e)
		{
			GameLog.error(e);
			
		}
		
		if (file.exists() && file.canRead())
		{
			try
			{
				DataInputStream s = new DataInputStream(new BufferedInputStream(new FileInputStream(file)));
				
				while (s.available() > 0)
				{
					byte b = s.readByte();
					
					GameLog.info("Byte found: " + b + ", char equivalent: " + (char)b);
					
				}
				
				s.close();
				
			}
			catch (Exception e)
			{
				GameLog.error(e);
				
			}
			
		}
		
		GameLog.info("Th-th-th-th-That's all, folks!");
		
	}
	
}
