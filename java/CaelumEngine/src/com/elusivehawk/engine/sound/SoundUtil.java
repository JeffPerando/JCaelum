
package com.elusivehawk.engine.sound;

import org.lwjgl.openal.AL10;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public final class SoundUtil
{
	private SoundUtil(){}
	
	public static void checkForALError() throws RuntimeException
	{
		int error = AL10.alGetError();
		
		if (error == AL10.AL_NO_ERROR)
		{
			return;
		}
		
		throw new RuntimeException("Found error: " + AL10.alGetString(error));
	}
	
}
