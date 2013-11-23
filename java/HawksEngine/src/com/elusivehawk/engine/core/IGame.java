
package com.elusivehawk.engine.core;

import com.elusivehawk.engine.render.IRenderHUB;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public interface IGame extends IThreadable
{
	public String getLWJGLPath();
	
	public IRenderHUB getRenderHUB();
	
}
