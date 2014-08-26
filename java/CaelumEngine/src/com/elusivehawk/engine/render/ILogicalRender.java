
package com.elusivehawk.engine.render;

import com.elusivehawk.engine.render.opengl.GLEnumPolyType;
import com.elusivehawk.engine.render.opengl.GLProgram;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public interface ILogicalRender
{
	public boolean updateBeforeRender(RenderContext rcon, double delta);
	
	public GLProgram getProgram();
	
	public GLEnumPolyType getPolygonType();
	
	public int getPolyCount();
	
}
