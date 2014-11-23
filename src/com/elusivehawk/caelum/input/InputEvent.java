
package com.elusivehawk.caelum.input;

import com.elusivehawk.caelum.Display;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class InputEvent
{
	public final EnumInputType type;
	public final Display display;
	
	@SuppressWarnings("unqualified-field-access")
	public InputEvent(EnumInputType inType, Display displayUsed)
	{
		assert inType != null;
		assert displayUsed != null;
		
		type = inType;
		display = displayUsed;
		
	}
	
}
