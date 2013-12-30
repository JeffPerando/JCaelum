
package com.elusivehawk.engine.core;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public interface IInput
{
	public String getName();
	
	public boolean getBool(int name);
	
	public float getFloat(int name);
	
	public int getInt(int name);
	
}
