
package com.elusivehawk.util.concurrent;

import com.elusivehawk.util.IPausable;

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
