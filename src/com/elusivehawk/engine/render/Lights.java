
package com.elusivehawk.engine.render;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class Lights
{
	public static final int LIGHT_CAP = 1024;
	
	protected final Light[] lights = new Light[LIGHT_CAP];
	protected int size = 0;
	
	public Lights()
	{
		
	}
	
	public Light attachLight(Light l)
	{
		if (this.lights[this.size] != null)
		{
			return null;
		}
		
		this.lights[this.size++] = l;
		
		return l;
	}
	
}
