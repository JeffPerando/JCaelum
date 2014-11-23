
package com.elusivehawk.caelum.input;

import com.elusivehawk.caelum.Display;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class PasteEvent extends InputEvent
{
	public final String pasted;
	
	@SuppressWarnings("unqualified-field-access")
	public PasteEvent(Display displayUsed, String str)
	{
		super(EnumInputType.KEYBOARD, displayUsed);
		
		assert str != null;
		
		pasted = str;
		
	}
	
}
