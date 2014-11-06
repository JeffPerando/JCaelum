
package com.elusivehawk.caelum.input;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public enum EnumMouseClick
{
	DOWN(true),
	DRAG(true),
	LIFTED(false),
	UP(false);
	
	private boolean down;
	
	@SuppressWarnings("unqualified-field-access")
	EnumMouseClick(boolean b)
	{
		down = b;
		
	}
	
	public boolean isDown()
	{
		return this.down;
	}
	
}
