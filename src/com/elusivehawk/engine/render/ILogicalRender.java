
package com.elusivehawk.engine.render;

import com.elusivehawk.engine.render.opengl.GLConst;
import com.elusivehawk.engine.render.opengl.GLEnumPolyType;
import com.elusivehawk.engine.render.opengl.GLProgram;
import com.elusivehawk.engine.render.opengl.VertexArray;

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
		GLProgram p = this.getProgram();
		
		if (p == null)
		{
			p = rcon.getDefaultProgram();
			
		}
		
		if (!p.bind(rcon))
		{
			return;
		}
		
		VertexArray vao = this.getVAO();
		
		if (vao == null)
		{
			return;
		}
		
		if (!vao.bind(rcon))
		{
			return;
		}
		
		rcon.getGL1().glDrawElements(this.getPolygonType(), this.getPolyCount(), GLConst.GL_UNSIGNED_INT, 0);
		
		vao.unbind(rcon);
		
		p.unbind(rcon);
		
	}
	
	default GLProgram getProgram()
	{
		return null;
	}
	
	public VertexArray getVAO();
	
	public GLEnumPolyType getPolygonType();
	
	public int getPolyCount();
	
}
