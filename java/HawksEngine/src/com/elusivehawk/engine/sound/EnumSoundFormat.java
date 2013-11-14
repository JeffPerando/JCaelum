
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
	IDK(null);
	
	public final ISoundDecoder decoder;
	
	EnumSoundFormat(ISoundDecoder sd)
	{
		decoder = sd;
		
	}
	
}
