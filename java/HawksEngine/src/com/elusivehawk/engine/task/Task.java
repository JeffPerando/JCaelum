
package com.elusivehawk.engine.task;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public abstract class Task<T>
{
	protected final ITaskReceiver<T> receiver;
	protected final int id;
	
	protected Task(int idNo, ITaskReceiver<T> handler)
	{
		receiver = handler;
		id = idNo;
		
	}
	
	public abstract boolean isTaskFinished();
	
	protected abstract T tryTask();
	
	public final void doTask()
	{
		T obj = this.tryTask();
		
		if (this.isTaskFinished() && this.receiver != null)
		{
			this.receiver.onTaskFinished(this.id, obj);
			
		}
		
	}
	
}
