
package com.elusivehawk.engine.render;

import com.elusivehawk.util.Internal;
import com.elusivehawk.util.concurrent.IThreadStoppable;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
@Internal
public interface IRenderEnvironment
{
	public static final int GL_1 = 1;
	public static final int GL_2 = 2;
	public static final int GL_3 = 3;
	public static final int GL_4 = 4;
	
	public boolean initiate();
	
	/**
	 * 
	 * Returns an OpenGL context object.
	 * 
	 * @param version The version of the context being requested.
	 * @return The OpenGL context object requested, or null if it couldn't be found.
	 */
	public Object getGL(int version);
	
	public IThreadStoppable createRenderThread(RenderContext rcon);
	
}
