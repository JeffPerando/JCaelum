
package com.elusivehawk.engine;

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
