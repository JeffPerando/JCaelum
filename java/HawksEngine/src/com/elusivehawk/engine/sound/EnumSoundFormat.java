
package com.elusivehawk.engine.sound;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public enum EnumSoundFormat
{
	OGG(new SoundDecoderOGG()),
	WAV(new SoundDecoderWav());
	
	public final ISoundDecoder decoder;
	
	EnumSoundFormat(ISoundDecoder sd)
	{
		decoder = sd;
		
	}
	
}
