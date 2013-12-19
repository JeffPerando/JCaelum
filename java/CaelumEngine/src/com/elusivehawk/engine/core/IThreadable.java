
package com.elusivehawk.engine.core;

/**
 * 
 * If you want to make a new thread, implement this and create a new {@link ThreadTimedWrapper}.
 * 
 * @author Elusivehawk
 */
public interface IThreadable extends IUpdatable, IPausable
{
	public boolean initiate();
	
	public void onThreadStopped();
	
	public void handleException(Throwable e);
	
	public int getTargetUpdateCount();
	
	public double getMaxDelta();
	
}
