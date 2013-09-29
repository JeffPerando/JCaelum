
import java.io.File;
import java.io.PrintStream;
import java.lang.reflect.Field;
import elusivehawk.engine.render.GL;
import elusivehawk.engine.util.GameLog;
import elusivehawk.engine.util.Timer;

/**
 * 
 * Test log:
 * 
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
		
		File file = new File(".", "/GLFields.txt");
		
		try
		{
			if (!file.createNewFile())
			{
				return;
			}
			
			PrintStream ps = new PrintStream(file);
			
			Timer timer = new Timer(false);
			
			timer.start();
			
			for (Field f : GL.class.getFields())
			{
				ps.println(f.getName());
				
			}
			
			timer.stop();
			
			ps.close();
			
			GameLog.info("Time: " + timer.report());
			
		}
		catch (Exception e)
		{
			GameLog.error(e);
			
		}
		
		GameLog.info("Th-th-th-th-That's all, folks!");
		
	}
	
}
