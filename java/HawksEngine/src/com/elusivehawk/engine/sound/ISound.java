
package com.elusivehawk.engine.sound;

import com.elusivehawk.engine.math.Vector;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public interface ISound
{
	public int getId();
	
	public Vector<Float> getPosition();
	
	public Vector<Float> getVelocity();
	
	public float getPitch();
	
	public float getVolume();
	
	public boolean isMusic();
	
}
