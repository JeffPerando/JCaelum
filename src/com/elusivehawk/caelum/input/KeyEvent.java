
package com.elusivehawk.caelum.input;

import com.elusivehawk.caelum.Display;

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
	public KeyEvent(Display displayUsed, Key pressed, boolean isDown, boolean wasDownBefore)
	{
		super(EnumInputType.KEYBOARD, displayUsed);
		
		key = pressed;
		down = isDown;
		downBefore = wasDownBefore;
		
	}
	
}
