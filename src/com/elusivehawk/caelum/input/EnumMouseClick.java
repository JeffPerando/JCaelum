
package com.elusivehawk.caelum.input;

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
