
package com.elusivehawk.caelum.input;

/**
 * 
 * It's baaaaack...
 * 
 * @author Elusivehawk
 */
@FunctionalInterface
public interface IInputListener
{
	void onInputReceived(InputEvent event, double delta);
	
}
