
package com.elusivehawk.engine.util.task;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public abstract class Task
{
	private final ITaskListener listener;
	
	public Task()
	{
		this(null);
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public Task(ITaskListener tlis)
	{
		listener = tlis;
		
	}
	
	public boolean completeTask()
	{
		boolean finish = false;
		
		try
		{
			finish = this.finishTask();
			
		}
		catch (Throwable e)
		{
			e.printStackTrace();
			
		}
		
		if (finish && this.listener != null)
		{
			this.listener.onTaskComplete(this);
			
		}
		
		return finish;
	}
	
	protected abstract boolean finishTask() throws Throwable;
	
}
