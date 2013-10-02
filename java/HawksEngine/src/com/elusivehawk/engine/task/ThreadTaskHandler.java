
package com.elusivehawk.engine.task;

import java.util.ArrayList;
import java.util.List;
import com.elusivehawk.engine.util.ThreadStoppable;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public final class ThreadTaskHandler extends ThreadStoppable
{
	private List<Task<?>> tasks = new ArrayList<Task<?>>();
	
	@Override
	public void run()
	{
		while (this.running)
		{
			for (Task<?> t : this.tasks)
			{
				t.doTask();
				
				if (t.isTaskFinished())
				{
					this.tasks.remove(t);
					
				}
				
			}
			
		}
		
	}
	
	public synchronized void scheduleTask(Task<?> t)
	{
		this.tasks.add(t);
		
	}
	
}
