
package com.elusivehawk.engine.core;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public interface IInput
{
	public void updateInput();
	
	public String getName();
	
	public EnumInputType getType();
	
	public boolean getBool(int name);
	
	public float getFloat(int name);
	
	public int getInt(int name);
	
	public boolean setBool(int name, boolean value);
	
}
