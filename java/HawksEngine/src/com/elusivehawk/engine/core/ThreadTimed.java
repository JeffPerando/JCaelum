
package com.elusivehawk.engine.core;

/**
 * 
 * Helper class for timed threading.
 * 
 * @author Elusivehawk
 */
public abstract class ThreadTimed extends ThreadStoppable
{
	@Override
	public void run()
	{
		int updates = 0;
		long delta, lastTime = System.currentTimeMillis();
		
		while (this.running)
		{
			if (this.paused)
			{
				continue;
			}
			
			delta = (System.currentTimeMillis() - this.getDelta());
			
			if (delta >= lastTime)
			{
				updates++;
				
				try
				{
					this.update(delta);
					
				}
				catch (Throwable e)
				{
					GameLog.error(e);
					
				}
				
				if (updates == this.getTargetUpdateCount())
				{
					try
					{
						Thread.sleep(1L);
						
					}
					catch (InterruptedException e){}
					
				}
				
			}
			
		}
		
	}
	
	public abstract void update(long delta);
	
	public abstract int getDelta();
	
	public abstract int getTargetUpdateCount();
	
}
