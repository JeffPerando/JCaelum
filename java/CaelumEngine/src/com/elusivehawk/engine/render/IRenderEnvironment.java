
package com.elusivehawk.engine.render;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public interface IRenderEnvironment
{
	/**
	 * 
	 * Returns an OpenGL context object.
	 * 
	 * @param version The version of the context being requested. For instance, giving 1.1f returns an instance of {@link IGL11}.
	 * @return The OpenGL context object requested, or null if it couldn't be found.
	 */
	public Object getGL(float version);
	
}
