
package com.elusivehawk.util.task;

import com.elusivehawk.util.Logger;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public abstract class Task
{
	private final ITaskListener listener;
	private int tries = 0;
	private boolean complete = false;
	
	public Task()
	{
		this(null);
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public Task(ITaskListener tlis)
	{
		listener = tlis;
		
	}
	
	public final boolean completeTask()
	{
		if (this.complete)
		{
			return true;
		}
		
		if (this.tries == 5)
		{
			return true;
		}
		
		try
		{
			this.complete = this.finishTask();
			
		}
		catch (Throwable e)
		{
			Logger.log().err(e);
			
		}
		
		if (this.complete)
		{
			if (this.listener != null)
			{
				this.listener.onTaskComplete(this);
				
			}
			
		}
		else
		{
			this.tries++;
			
		}
		
		return this.complete;
	}
	
	protected abstract boolean finishTask() throws Throwable;
	
}
