
import com.elusivehawk.engine.core.GameLog;

/**
 * 
 * Test log:
 * 
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
		
		int test = 0;
		
		GameLog.info("" + test++);
		GameLog.info("" + test);
		GameLog.info("" + ++test);
		GameLog.info("" + test);
		
		GameLog.info("Th-th-th-th-That's all, folks!");
		
	}
	
}
