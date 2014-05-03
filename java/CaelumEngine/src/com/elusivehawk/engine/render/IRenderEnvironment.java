
package com.elusivehawk.engine.render;

import com.elusivehawk.engine.util.concurrent.IThreadStoppable;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
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
	
	/**
	 * 
	 * Called during startup.
	 * 
	 * @param name The name of the {@link IDisplay} to create.
	 * @param settings The settings to create the display under.
	 * @return The display created.
	 */
	public IDisplay createDisplay(String name, DisplaySettings settings);
	
	public IThreadStoppable createRenderThread(RenderSystem rsys);
	
}
