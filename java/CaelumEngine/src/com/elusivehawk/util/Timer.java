
package com.elusivehawk.util;

/**
 * 
 * Helper class (Yes, another one) for timing.
 * 
 * @author Elusivehawk
 */
public class Timer
{
	public static final double NANO_SEC = 1000000000D;
	
	protected double start = 0, time = 0;
	protected boolean started = false;
	
	public void start()
	{
		if (!this.started)
		{
			this.started = true;
			
			this.start = System.nanoTime() / NANO_SEC;
			
		}
		
	}
	
	public void stop()
	{
		if (this.started)
		{
			this.started = false;
			
			this.time = (System.nanoTime() / NANO_SEC) - this.start;
			
		}
		
	}
	
	public double time()
	{
		return this.started ? 0 : this.time;
	}
	
	public double timeCall(Runnable r)
	{
		this.start();
		
		r.run();
		
		this.stop();
		
		return this.time;
	}
	
}
