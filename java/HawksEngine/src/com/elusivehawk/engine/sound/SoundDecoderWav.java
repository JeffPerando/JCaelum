
package com.elusivehawk.engine.sound;

import java.io.File;
import org.lwjgl.openal.AL10;
import org.lwjgl.util.WaveData;
import com.elusivehawk.engine.core.GameLog;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class SoundDecoderWav implements ISoundDecoder
{
	@Override
	public int decodeSound(File file)
	{
		WaveData wd = null;
		
		try
		{
			wd = WaveData.create(file.toURI().toURL());
			
		}
		catch (Exception e)
		{
			GameLog.error(e);
			
		}
		
		int ret = 0;
		
		if (wd != null)
		{
			ret = AL10.alGenBuffers();
			
			AL10.alBufferData(ret, wd.format, wd.data, wd.samplerate);
			
			wd.dispose();
			
		}
		
		return ret;
	}
	
}
