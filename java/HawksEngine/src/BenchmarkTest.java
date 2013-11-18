
import java.io.File;
import java.nio.ByteBuffer;
import com.elusivehawk.engine.core.FileHelper;
import com.elusivehawk.engine.core.GameLog;
import com.elusivehawk.engine.sound.EnumSoundFormat;
import com.elusivehawk.engine.sound.SoundDecoderOGG;
import com.elusivehawk.engine.sound.SoundDecoderOGG.OggPage;

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
		
		File file = FileHelper.createFile(".", "Test_sound.ogg");
		
		try
		{
			OggPage[] pages = ((SoundDecoderOGG)EnumSoundFormat.OGG.decoder).decode(file);
			
			if (pages != null)
			{
				GameLog.info("Page count: " + pages.length);
				
				for (OggPage p : pages)
				{
					GameLog.info("OGG page found; Header type: " + p.type.name());
					
					ByteBuffer buf = p.data;
					
					GameLog.info("Byte count: " + buf.capacity());
					
					while (buf.remaining() != 0)
					{
						GameLog.info("Byte found: " + buf.get());
						
					}
					
					buf.rewind();
					
				}
				
			}
			
		}
		catch (Exception e)
		{
			GameLog.error(e);
			
		}
		
		GameLog.info("Th-th-th-th-That's all, folks!");
		
	}
	
}
