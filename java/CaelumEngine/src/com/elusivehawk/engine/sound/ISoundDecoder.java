
package com.elusivehawk.engine.sound;

import java.io.File;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public interface ISoundDecoder
{
	public static final ISoundDecoder OGG = new SoundDecoderOGG();
	public static final ISoundDecoder WAV = new SoundDecoderWav();
	
	public int decodeSound(File file);
	
}
