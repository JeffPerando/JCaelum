
package com.elusivehawk.engine.util.task;

import java.net.URL;
import java.net.URLConnection;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class TaskURLRequest extends TaskURL
{
	public TaskURLRequest(ITaskListener tlis, URL adr)
	{
		super(tlis, adr);
		
	}
	
	@Override
	protected boolean finishTask() throws Throwable
	{
		//URLConnection con = new URLConnection();
		
		return false;
	}
	
}
