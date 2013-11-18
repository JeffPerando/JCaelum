
package com.elusivehawk.engine.core;

/**
 * 
 * Helper class for timed threading.
 * 
 * @author Elusivehawk
 */
public abstract class ThreadTimed extends ThreadStoppable
{
	private int updates = 0;
	private long sleepTime = 0L;
	private double time, nextTime;
	
	@Override
	public boolean initiate()
	{
		this.nextTime = System.nanoTime() / 1000000000.0;
		
		return true;
	}
	
	@Override
	public final void update()
	{
		this.time = System.nanoTime() / 1000000000.0;
		
		if ((this.nextTime - this.time) > this.getMaxDelta()){}
		
		if ((this.time + this.getDelta()) >= this.nextTime)
		{
			this.nextTime += this.getDelta();
			this.updates++;
			
			this.timedUpdate(this.time - this.nextTime);
			
			if (this.updates == this.getTargetUpdateCount())
			{
				this.sleepTime = 1L;
				
			}
			
		}
		else this.sleepTime = (long)(1000.0 * (this.time - this.nextTime));
		
		if (this.sleepTime > 0L)
		{
			try
			{
				Thread.sleep(this.sleepTime);
				
			}
			catch (Exception e){}
			
			this.sleepTime = 0L;
			
		}
		
	}
	
	public abstract void timedUpdate(double delta);
	
	public abstract double getDelta();
	
	public abstract int getTargetUpdateCount();
	
	public abstract double getMaxDelta();
	
}
