
package com.elusivehawk.util;

/**
 * 
 * ...Do I really need to explain it?
 * 
 * @author Elusivehawk
 */
@FunctionalInterface
public interface IUpdatable
{
	public void update(double delta, Object... extra) throws Throwable;
	
}
