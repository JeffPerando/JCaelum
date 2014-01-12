
package com.elusivehawk.engine.util;


/**
 * 
 * "Standardisation" interface for pausing things; Suspiciously similar to {@link IDirty}.
 * 
 * @author Elusivehawk
 */
public interface IPausable
{
	public boolean isPaused();
	
	public void setPaused(boolean p);
	
}
