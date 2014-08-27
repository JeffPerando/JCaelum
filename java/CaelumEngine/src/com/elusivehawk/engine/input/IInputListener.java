
package com.elusivehawk.engine.input;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
@FunctionalInterface
public interface IInputListener
{
	public void onInputReceived(double delta, Input input);
	
}
