
package com.elusivehawk.util.task;

import java.util.Iterator;
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
	
	@Override
	public void rawUpdate() throws Throwable
	{
		if (this.isPaused() || this.tasks.isEmpty())
		{
			Thread.sleep(1L);
			
		}
		
		Iterator<Task> itr = this.tasks.iterator();
		
		itr.forEachRemaining(((t) ->
		{
			if (t.completeTask())
			{
				itr.remove();
				
			}
			
		}));
		
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
