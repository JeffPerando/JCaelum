
package com.elusivehawk.caelum.input;

import com.elusivehawk.caelum.CaelumException;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class InputException extends CaelumException
{
	public InputException(){}
	
	public InputException(String err)
	{
		super(err);
		
	}
	
	public InputException(Throwable e)
	{
		super(e);
		
	}
	
	public InputException(String err, Throwable e, Object... args)
	{
		super(err, e, args);
		
	}
	
	public InputException(String err, Object... args)
	{
		super(err, args);
		
	}
	
}
