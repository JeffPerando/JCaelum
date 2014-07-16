
package com.elusivehawk.util;

/**
 * 
 * ...Do I really need to explain it?
 * <p>
 * (For the record, I wouldn't recommend using this for your game; API changes are bound to occur.)
 * 
 * @author Elusivehawk
 */
public interface IUpdatable
{
	public void update(double delta) throws Throwable;
	
}
