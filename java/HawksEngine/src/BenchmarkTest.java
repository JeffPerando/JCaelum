
import java.nio.IntBuffer;
import elusivehawk.engine.util.BufferHelper;
import elusivehawk.engine.util.GameLog;

/**
 * 
 * Test log:
 * 
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
		
		IntBuffer buf = BufferHelper.makeIntBuffer(4, 8, 15, 16, 23, 42);
		
		buf.position(0);
		buf.put(0);
		buf.put(9);
		
		buf.rewind();
		
		StringBuilder b = new StringBuilder();
		
		while (buf.remaining() > 0)
		{
			b.append(buf.get() + (buf.remaining() == 0 ? ";" : ", "));
			
		}
		
		buf.rewind();
		
		GameLog.info(b.toString());
		
		GameLog.info("Th-th-th-th-That's all, folks!");
		
	}
	
}
