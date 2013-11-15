
package com.elusivehawk.engine.core;

/**
 * 
 * Helper class for timed threading.
 * 
 * @author Elusivehawk
 */
public abstract class ThreadTimed extends ThreadStoppable
{
	protected int updates = 0;
	protected long delta, lastTime = System.currentTimeMillis();
	
	@Override
	public final void update()
	{
		this.delta = (System.currentTimeMillis() - this.getDelta());
		
		if (this.delta >= this.lastTime)
		{
			this.updates++;
			
			try
			{
				this.timedUpdate(this.delta);
				
			}
			catch (Throwable e)
			{
				GameLog.error(e);
				
			}
			
			if (this.updates == this.getTargetUpdateCount())
			{
				this.updates = 0;
				
				try
				{
					Thread.sleep(1L);
					
				}
				catch (InterruptedException e){}
				
			}
			
		}
		
	}
	
	public abstract void timedUpdate(long delta);
	
	public abstract int getDelta();
	
	public abstract int getTargetUpdateCount();
	
}
