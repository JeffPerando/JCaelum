
package com.elusivehawk.caelum.prefab;

import com.elusivehawk.caelum.CaelumException;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class WorldException extends CaelumException
{
	public WorldException(){}
	
	public WorldException(String err)
	{
		super(err);
		
	}
	
	public WorldException(Throwable e)
	{
		super(e);
		
	}
	
	public WorldException(String err, Throwable e, Object... args)
	{
		super(err, e, args);
		
	}
	
	public WorldException(String err, Object... args)
	{
		super(err, args);
		
	}
	
}
