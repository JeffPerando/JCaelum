
package elusivehawk.engine.util;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public abstract class ThreadStoppable extends Thread
{
	protected boolean running = true;
	
	public abstract void update();
	
	@Override
	public void run()
	{
		while (this.running)
		{
			this.update();
			
		}
		
	}
	
	public synchronized final void stopThread()
	{
		this.running = false;
		
	}
	
}
