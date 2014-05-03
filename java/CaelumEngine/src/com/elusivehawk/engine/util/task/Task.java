
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
	
	@SuppressWarnings("unqualified-field-access")
	public Task(ITaskListener tlis)
	{
		assert tlis != null;
		
		listener = tlis;
		
	}
	
	public boolean completeTask(Object... args)
	{
		boolean finish = false;
		
		try
		{
			finish = this.finishTask(args);
			
		}
		catch (Throwable e)
		{
			e.printStackTrace();
			
		}
		
		if (finish)
		{
			this.listener.onTaskComplete(this);
			
		}
		
		return finish;
	}
	
	protected abstract boolean finishTask(Object... args) throws Throwable;
	
}
