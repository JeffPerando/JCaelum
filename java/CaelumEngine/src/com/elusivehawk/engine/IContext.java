
package com.elusivehawk.engine;

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
