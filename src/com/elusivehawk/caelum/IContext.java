
package com.elusivehawk.caelum;

import com.elusivehawk.util.Internal;

/**
 * 
 * 
 * 
 * @deprecated To be removed once OpenGL NG comes out, because rendering is its only use.
 * 
 * @author Elusivehawk
 */
@Deprecated
@Internal
public interface IContext
{
	boolean initContext();
	
	void cleanup();
	
}
