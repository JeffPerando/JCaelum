
package com.elusivehawk.engine.input;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public enum EnumMouseClick
{
	DOWN,
	DRAG,
	UP;
	
	public boolean isDown()
	{
		return this != UP;
	}
	
}
