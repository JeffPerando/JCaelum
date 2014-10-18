
package com.elusivehawk.engine.render;

import com.elusivehawk.engine.render.gl.GLConst;
import com.elusivehawk.engine.render.gl.GLEnumDrawType;
import com.elusivehawk.engine.render.gl.GLProgram;
import com.elusivehawk.engine.render.gl.VertexArray;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public interface ILogicalRender extends IRenderable
{
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
	
	default GLProgram getProgram()
	{
		return null;
	}
	
	VertexArray getVAO();
	
	GLEnumDrawType getPolygonType();
	
	int getPolyCount();
	
}
