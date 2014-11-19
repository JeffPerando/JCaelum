
package com.elusivehawk.caelum.input;

import com.elusivehawk.caelum.Display;

/**
 * 
 * It's baaaaack...
 * 
 * @author Elusivehawk
 */
@FunctionalInterface
public interface IInputListener
{
	void onInputReceived(Display display, InputEvent inEvent, double delta);
	
}
