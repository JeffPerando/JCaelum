
package com.elusivehawk.caelum.render;

import com.elusivehawk.caelum.render.gl.GLConst;
import com.elusivehawk.caelum.render.gl.GLEnumDrawType;
import com.elusivehawk.caelum.render.gl.GLProgram;
import com.elusivehawk.caelum.render.gl.VertexArray;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public interface ILogicalRender extends IRenderable
{
	@Override
	default void preRender(RenderContext rcon, double delta){}
	
	@Override
	default void render(RenderContext rcon)
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
	
	@Override
	default void postRender(RenderContext rcon){}
	
	default GLProgram getProgram()
	{
		return null;
	}
	
	VertexArray getVAO();
	
	GLEnumDrawType getPolygonType();
	
	int getPolyCount();
	
}
