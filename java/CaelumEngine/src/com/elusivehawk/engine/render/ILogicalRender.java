
package com.elusivehawk.engine.render;

import com.elusivehawk.engine.render.opengl.GLConst;
import com.elusivehawk.engine.render.opengl.GLEnumPolyType;
import com.elusivehawk.engine.render.opengl.GLProgram;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public interface ILogicalRender extends IRenderable
{
	@Override
	default void render(RenderContext rcon, double delta)
	{
		if (!this.updateBeforeRender(rcon, delta))
		{
			return;
		}
		
		GLProgram p = this.getProgram();
		
		if (p == null)
		{
			return;
		}
		
		if (!p.bind(rcon))
		{
			return;
		}
		
		rcon.getGL1().glDrawElements(this.getPolygonType(), this.getPolyCount(), GLConst.GL_UNSIGNED_INT, 0);
		
		p.unbind(rcon);
		
	}
	
	public GLProgram getProgram();
	
	public GLEnumPolyType getPolygonType();
	
	public int getPolyCount();
	
}
