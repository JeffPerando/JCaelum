
package com.elusivehawk.util.task;

import com.elusivehawk.util.Internal;
import com.elusivehawk.util.concurrent.ThreadStoppable;
import com.elusivehawk.util.storage.SyncList;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
@Internal
public class ThreadTaskWorker extends ThreadStoppable
{
	protected final SyncList<Task> tasks = new SyncList<Task>();
	
	ThreadTaskWorker(int core)
	{
		super(String.format("Worker-%s", core));
		setDaemon(true);
		
	}
	
	@Override
	public void rawUpdate() throws Throwable
	{
		if (this.isPaused() || this.tasks.isEmpty())
		{
			Thread.sleep(1L);
			return;
		}
		
		for (Task t : this.tasks)
		{
			if (t.completeTask())
			{
				this.tasks.remove(t);
				
			}
			
		}
		
	}
	
	public int getTaskCount()
	{
		return this.tasks.size();
	}
	
	public void scheduleTask(Task t)
	{
		this.tasks.add(t);
		
	}
	
}
