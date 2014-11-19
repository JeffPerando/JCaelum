
package com.elusivehawk.caelum.input;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class KeyEvent extends InputEvent
{
	public final Key key;
	public final boolean down, downBefore;
	
	@SuppressWarnings("unqualified-field-access")
	public KeyEvent(Key pressed, boolean isDown, boolean wasDownBefore)
	{
		super(EnumInputType.KEYBOARD);
		
		key = pressed;
		down = isDown;
		downBefore = wasDownBefore;
		
	}
	
}
