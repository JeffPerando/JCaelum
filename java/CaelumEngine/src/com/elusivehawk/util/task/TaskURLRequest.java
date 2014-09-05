
package com.elusivehawk.util.task;

import java.net.URL;
import java.net.URLConnection;
import com.elusivehawk.util.io.ByteStreams;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class TaskURLRequest extends TaskURL
{
	protected byte[] result = null;
	
	public TaskURLRequest(ITaskListener tlis, URL adr)
	{
		super(tlis, adr);
		
	}
	
	@Override
	protected boolean finishTask() throws Throwable
	{
		URLConnection con = this.url.openConnection();
		
		con.connect();
		
		if (con.getDoInput())
		{
			ByteStreams s = new ByteStreams(con.getInputStream());
			
			this.result = s.readAll();
			
			s.close();
			
		}
		
		return true;
	}
	
	
	public byte[] getResult()
	{
		return this.result;
	}
}
