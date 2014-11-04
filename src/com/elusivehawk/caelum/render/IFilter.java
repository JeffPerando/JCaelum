
package com.elusivehawk.caelum.render;

import com.elusivehawk.caelum.render.gl.GLException;
import com.elusivehawk.caelum.render.gl.GLProgram;

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
