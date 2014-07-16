
package com.elusivehawk.util;

/**
 * 
 * Helper class (Yes, another one) for timing.
 * 
 * @author Elusivehawk
 */
public class Timer
{
	protected long start = 0, time = 0;
	protected boolean started = false;
	public final boolean nanoTime;
	
	public Timer()
	{
		this(true);
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public Timer(boolean nano)
	{
		nanoTime = nano;
		
	}
	
	public void start()
	{
		if (!this.started)
		{
			this.started = true;
			
			this.start = this.nanoTime ? System.nanoTime() : System.currentTimeMillis();
			
		}
		
	}
	
	public void stop()
	{
		if (this.started)
		{
			this.started = false;
			
			this.time = (this.nanoTime ? System.nanoTime() : System.currentTimeMillis()) - this.start;
			
		}
		
	}
	
	public long report()
	{
		return this.started ? 0 : this.time;
	}
	
}
