
package com.elusivehawk.engine.core;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public interface IThreadable extends IUpdatable
{
	public boolean initiate();
	
	public boolean isPaused();
	
	public void setPaused(boolean p);
	
	public void onThreadStopped();
	
	public void handleException(Throwable e);
	
	public int getTargetUpdateCount();
	
	public double getMaxDelta();
	
}
