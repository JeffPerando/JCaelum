
package com.elusivehawk.engine.util.task;

import java.net.URL;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public abstract class TaskURL extends Task
{
	protected final URL url;
	
	@SuppressWarnings("unqualified-field-access")
	public TaskURL(URL adr)
	{
		url = adr;
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public TaskURL(ITaskListener tlis, URL adr)
	{
		super(tlis);
		url = adr;
		
	}
	
}
