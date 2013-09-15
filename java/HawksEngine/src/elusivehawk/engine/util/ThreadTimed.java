
package elusivehawk.engine.util;

/**
 * 
 * Helper class for timed threading.
 * 
 * @author Elusivehawk
 */
public abstract class ThreadTimed extends Thread
{
	@Override
	public void run()
	{
		//TODO Finish
		
	}
	
	public abstract void updateThread();
	
	public abstract int getMinimumDelta();
	
	public abstract int getTargetUpdateCount();
	
}
