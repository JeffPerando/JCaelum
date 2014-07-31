
package com.elusivehawk.util.task;

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
		if (this.tries == 5)
		{
			return true;
		}
		
		boolean finish = false;
		
		try
		{
			finish = this.finishTask();
			
		}
		catch (Throwable e)
		{
			e.printStackTrace();
			
		}
		
		if (finish)
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
		
		return finish;
	}
	
	protected abstract boolean finishTask() throws Throwable;
	
}
