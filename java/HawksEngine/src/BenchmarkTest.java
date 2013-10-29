
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import com.elusivehawk.engine.core.GameLog;
import com.elusivehawk.engine.core.TextParser;

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
		
		File file = TextParser.createFile(".", "Test_file.txt");
		
		if (file.exists())
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
