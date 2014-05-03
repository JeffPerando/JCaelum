
package com.elusivehawk.engine.util.task;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class TaskManager
{
	private final ThreadTaskWorker[] threads;
	private boolean started = false;
	private int next = 0;
	
	public TaskManager()
	{
		this(Runtime.getRuntime().availableProcessors());
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public TaskManager(int threadCount)
	{
		threads = new ThreadTaskWorker[threadCount];
		
		for (int c = 0; c < threadCount; c++)
		{
			threads[c] = new ThreadTaskWorker();
			
		}
		
	}
	
	public void start()
	{
		if (this.started)
		{
			return;
		}
		
		synchronized (this)
		{
			for (ThreadTaskWorker t : this.threads)
			{
				t.start();
				
			}
			
		}
		
		this.started = true;
		
	}
	
	public boolean scheduleTask(Task t)
	{
		if (!this.started)
		{
			return false;
		}
		
		this.threads[this.next++].scheduleTask(t);
		
		if (this.next == this.threads.length)
		{
			this.next = 0;
			
		}
		
		return true;
	}
	
}
