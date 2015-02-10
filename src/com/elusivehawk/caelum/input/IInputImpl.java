
package com.elusivehawk.caelum.input;

import java.io.Closeable;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public interface IInputImpl extends Closeable
{
	void updateInput(double delta, Input input) throws Throwable;
	
}
