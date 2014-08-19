
package com.elusivehawk.util.concurrent;

import com.elusivehawk.util.IUpdatable;
import com.elusivehawk.util.Timer;

/**
 * 
 * Abstract class for timed threading.
 * 
 * @author Elusivehawk
 */
public abstract class ThreadTimed extends ThreadStoppable implements IUpdatable
{
	public static final int MILI_SEC = 1000;
	
	private int updates = 0, updateCount = 0;
	private double time = 0, lastTime, timeUsed = 0, delta;
	
	private Timer timer = new Timer();
	
	@Override
	public boolean initiate()
	{
		this.updateCount = this.getTargetUpdateCount();
		this.delta = (Timer.NANO_SEC / this.updateCount) / Timer.NANO_SEC;
		
		return true;
	}
	
	@Override
	public final void firstUpdate()
	{
		this.lastTime = System.nanoTime() / Timer.NANO_SEC;
		
		this.timer.start();
		this.timer.stop();
		
	}
	
	@Override
	public final void rawUpdate()
	{
		if (this.timeUsed > 1.0 || this.updates == this.updateCount)//Have we run out of time?
		{
			long sleep = (long)(MILI_SEC - (Math.abs(this.timeUsed - 1.0) * MILI_SEC));
			
			if (sleep > 0)
			{
				try
				{
					Thread.sleep(sleep);//Sleep for the remainder of the second
					
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
					
				}
				
			}
			
			//Zero out both timers for the new second.
			
			this.timeUsed = 0;
			this.updates = 0;
			return;
		}
		
		this.time = System.nanoTime() / Timer.NANO_SEC;
		this.updates++;
		
		this.timer.start();//Start the timer
		
		try
		{
			this.update(this.time - this.lastTime);//Update the thread
			
		}
		catch (Throwable e)
		{
			this.handleException(e);
			
		}
		
		this.timer.stop();//Stop the timer
		
		//System.out.println(String.format("Time: %s; Last Time: %s", this.time, this.lastTime));
		
		this.timeUsed += this.timer.time();
		this.lastTime = this.time;
		
		if (this.timer.time() < this.delta)//What if we actually have MORE time than we know what to do with?
		{
			try
			{
				Thread.sleep((long)((this.delta - this.timer.time()) * MILI_SEC));//Put the thread asleep for the remaining time.
				
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
				
			}
			
		}
		
	}
	
	public abstract int getTargetUpdateCount();
	
}
