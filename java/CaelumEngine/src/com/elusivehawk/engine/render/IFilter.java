
package com.elusivehawk.engine.render;

import com.elusivehawk.engine.render.opengl.GLProgram;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
@FunctionalInterface
public interface IFilter
{
	public void filter(RenderContext rcon, GLProgram p);
	
	
}
