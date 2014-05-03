
package com.elusivehawk.engine.util.concurrent;

import com.elusivehawk.engine.util.IPausable;

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
