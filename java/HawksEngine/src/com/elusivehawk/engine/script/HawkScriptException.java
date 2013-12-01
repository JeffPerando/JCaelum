
package com.elusivehawk.engine.script;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class HawkScriptException extends RuntimeException
{
	private static final long serialVersionUID = 6741477386637244549L;
	
	public HawkScriptException()
	{
		this("");
		
	}
	
	public HawkScriptException(String msg)
	{
		super(msg);
		
	}
	
}
