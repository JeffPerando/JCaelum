
package com.elusivehawk.engine.util.task;

import java.net.URL;
import java.net.URLConnection;
import com.elusivehawk.engine.util.io.ByteStreams;

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
		URLConnection con = this.url.openConnection();
		
		con.connect();
		
		ByteStreams s = new ByteStreams(con.getInputStream());
		
		byte[] incoming = s.readAll();
		
		return false;
	}
	
}
