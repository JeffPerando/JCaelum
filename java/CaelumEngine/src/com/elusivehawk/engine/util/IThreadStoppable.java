
package com.elusivehawk.engine.util;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public interface IThreadStoppable extends IPausable
{
	public boolean isRunning();
	
	public void stopThread();
	
}
