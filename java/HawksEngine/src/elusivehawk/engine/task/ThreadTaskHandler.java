
package elusivehawk.engine.task;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public final class ThreadTaskHandler extends Thread
{
	private List<Task<?>> tasks = new ArrayList<Task<?>>();
	
	@Override
	public void run()
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
	
	public synchronized void scheduleTask(Task<?> t)
	{
		this.tasks.add(t);
		
	}
	
}
