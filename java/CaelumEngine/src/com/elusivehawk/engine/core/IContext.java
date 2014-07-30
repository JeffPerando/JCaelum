
package com.elusivehawk.engine.core;

import com.elusivehawk.util.Internal;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
@Internal
public interface IContext
{
	public boolean initContext();
	
	public void cleanup();
	
}
