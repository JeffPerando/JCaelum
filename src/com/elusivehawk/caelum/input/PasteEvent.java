
package com.elusivehawk.caelum.input;

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
	public PasteEvent(String str)
	{
		super(EnumInputType.KEYBOARD);
		
		assert str != null;
		
		pasted = str;
		
	}
	
}
