
package com.elusivehawk.engine.render;

import com.elusivehawk.engine.render.opengl.GLException;
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
	void filter(RenderContext rcon, GLProgram p) throws GLException;
	
}
