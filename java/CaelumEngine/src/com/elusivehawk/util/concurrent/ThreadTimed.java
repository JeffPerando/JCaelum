
package com.elusivehawk.util.concurrent;

import com.elusivehawk.util.IUpdatable;

/**
 * 
 * Abstract class for timed threading.
 * 
 * @author Elusivehawk
 */
public abstract class ThreadTimed extends ThreadStoppable implements IUpdatable
{
	public static final double DIV = 1000000000000000.0;
	
	private int updates = 0, updateCount = 0;
	private long sleepTime = 0L;
	private double time, lastTime, delta;
	private boolean initiated = false;
	
	@Override
	public boolean initiate()
	{
		if (this.initiated)
		{
			return false;
		}
		
		this.updateCount = this.getTargetUpdateCount();
		this.delta = (this.updateCount / DIV);
		this.time = (DIV / System.nanoTime()) + this.delta;
		this.initiated = true;
		
		return true;
	}
	
	@Override
	public final void rawUpdate()
	{
		if (!this.initiated)
		{
			System.err.println(String.format("Thread %s failed to initiate, remember to call super.initiate()!", this));
			
			this.stopThread();
			
			return;
		}
		
		this.time = System.nanoTime() / DIV;
		
		if (this.getMaxDelta() > 0 && (this.lastTime - this.time) > this.getMaxDelta()) this.lastTime = this.time;
		
		if ((this.time + this.delta) >= this.lastTime)
		{
			this.updates++;
			
			try
			{
				this.update(this.time - this.lastTime);
				
			}
			catch (Throwable e)
			{
				this.handleException(e);
				
			}
			
			this.lastTime += this.delta;
			
			if (this.updates >= this.updateCount)
			{
				this.sleepTime = 1L;
				
				this.updates = 0;
				
			}
			
		}
		else this.sleepTime = (long)(1000.0 * (this.time - this.lastTime));
		
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
	
	public final boolean isInitiated()
	{
		return this.initiated;
	}
	
	public abstract int getTargetUpdateCount();
	
	public abstract double getMaxDelta();
	
}
