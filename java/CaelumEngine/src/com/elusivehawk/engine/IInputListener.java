
package com.elusivehawk.engine;

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
