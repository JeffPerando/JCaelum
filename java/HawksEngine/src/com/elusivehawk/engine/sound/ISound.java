
package com.elusivehawk.engine.sound;

import com.elusivehawk.engine.math.Vector3f;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public interface ISound
{
	public int getId();
	
	public Vector3f getPosition();
	
	public Vector3f getVelocity();
	
	public float getPitch();
	
	public float getVolume();
	
	public boolean isMusic();
	
}
