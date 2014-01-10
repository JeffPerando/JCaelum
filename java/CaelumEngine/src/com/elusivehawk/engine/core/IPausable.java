
package com.elusivehawk.engine.core;

import com.elusivehawk.engine.util.IDirty;

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
