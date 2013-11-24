
package com.elusivehawk.engine.core;

/**
 * 
 * 
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
