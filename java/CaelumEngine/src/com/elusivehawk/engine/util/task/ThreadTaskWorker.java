
package com.elusivehawk.engine.util.task;

import java.util.List;
import com.elusivehawk.engine.util.concurrent.ThreadStoppable;
import com.google.common.collect.Lists;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class ThreadTaskWorker extends ThreadStoppable
{
	protected final List<Task> tasks = Lists.newArrayList();
	
	@Override
	public void rawUpdate() throws Throwable
	{
		if (this.isPaused() || this.tasks.isEmpty())
		{
			Thread.sleep(1L);
			
		}
		
		Task t;
		
		for (int c = 0; c < this.tasks.size(); c++)
		{
			t = this.tasks.get(c);
			
			if (t.completeTask())
			{
				this.tasks.remove(c);
				
			}
			
		}
		
	}
	
	public int getTaskCount()
	{
		return this.tasks.size();
	}
	
	public synchronized void scheduleTask(Task t)
	{
		this.tasks.add(t);
		
	}
	
}
