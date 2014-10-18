
package com.elusivehawk.engine.render;

import com.elusivehawk.engine.render.gl.GLException;
import com.elusivehawk.engine.render.gl.GLProgram;

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
