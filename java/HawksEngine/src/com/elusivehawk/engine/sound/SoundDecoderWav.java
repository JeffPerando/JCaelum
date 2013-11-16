
package com.elusivehawk.engine.sound;

import org.lwjgl.util.WaveData;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class SoundDecoderWav implements ISoundDecoder
{
	@Override
	public int decodeSound(String path)
	{
		WaveData wd = WaveData.create(path);
		
		if (wd == null)
		{
			return 0;
		}
		
		return 0;
	}
	
}
