
package com.elusivehawk.engine.sound;

import java.io.File;
import com.elusivehawk.engine.math.Vector3f;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class Sound implements ISound
{
	protected int id;
	protected float pitch;
	
	public Sound(File file, float p)
	{
		this(0, p);
		
	}
	
	public Sound(int i, float p)
	{
		id = i;
		pitch = p;
		
	}
	
	@Override
	public int getId()
	{
		return this.id;
	}
	
	@Override
	public Vector3f getPosition()
	{
		return null;
	}
	
	@Override
	public Vector3f getVelocity()
	{
		return null;
	}
	
	@Override
	public float getPitch()
	{
		return 0;
	}
	
	@Override
	public float getVolume()
	{
		return 0;
	}
	
	@Override
	public boolean isMusic()
	{
		return false;
	}
	
}
