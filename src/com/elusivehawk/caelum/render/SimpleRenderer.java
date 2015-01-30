
package com.elusivehawk.caelum.render;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import com.elusivehawk.caelum.render.gl.GL1;
import com.elusivehawk.caelum.render.gl.GLBuffer;
import com.elusivehawk.caelum.render.gl.GLConst;
import com.elusivehawk.caelum.render.gl.GLEnumBufferTarget;
import com.elusivehawk.caelum.render.gl.GLEnumDataUsage;
import com.elusivehawk.caelum.render.gl.GLEnumDrawType;
import com.elusivehawk.caelum.render.gl.GLProgram;
import com.elusivehawk.caelum.render.gl.GLVertexArray;
import com.elusivehawk.caelum.render.tex.ITexture;
import com.elusivehawk.caelum.render.tex.TextureAsset;
import com.elusivehawk.util.Logger;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class SimpleRenderer implements IRenderable
{
	private final GLBuffer vertex, indices;
	private final GLEnumDrawType type;
	private final int polyCount;
	
	private final GLVertexArray vao = new GLVertexArray();
	private final ITexture tex;
	
	private GLProgram program = null;
	
	private boolean initiated = false;
	
	@SuppressWarnings("unqualified-field-access")
	public SimpleRenderer(FloatBuffer vtx, IntBuffer ind, GLEnumDrawType dtype, int count)
	{
		vertex = new GLBuffer(GLEnumBufferTarget.GL_ARRAY_BUFFER, GLEnumDataUsage.GL_STATIC_DRAW, vtx);
		indices = new GLBuffer(GLEnumBufferTarget.GL_ELEMENT_ARRAY_BUFFER, GLEnumDataUsage.GL_STATIC_DRAW, ind);
		type = dtype;
		polyCount = count;
		
		tex = new TextureAsset("/res/test.png");
		
		vertex.addAttrib(0, 3, GLConst.GL_FLOAT, false, 12, 0);
		
		vao.addVBO(vertex);
		vao.addVBO(indices);
		
	}
	
	@Override
	public void preRender(RenderContext rcon) throws RenderException
	{
		if (!this.initiated)
		{
			this.program = new GLProgram(rcon.getDefaultShaders());
			
			this.initiated = true;
			
		}
		
	}
	
	@Override
	public void postRender(RenderContext rcon) throws RenderException{}
	
	@Override
	public void render(RenderContext rcon) throws RenderException
	{
		if (this.program.bind(rcon))
		{
			Logger.debug("PROGRAM BOUND");
			
			if (this.vao.bind(rcon))
			{
				Logger.debug("VAO BOUND");
				
				GL1.glActiveTexture(GLConst.GL_TEXTURE0);
				GL1.glBindTexture(this.tex);
				
				GL1.glDrawArrays(this.type, 0, this.polyCount);
				
				GL1.glBindTexture(GLConst.GL_TEXTURE_2D, 0);
				
			}
			
			this.vao.unbind(rcon);
			
		}
		
		this.program.unbind(rcon);
		
	}
	
}
