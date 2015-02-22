
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

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class SimpleRenderer extends Renderable
{
	private final FloatBuffer vertex;
	private final IntBuffer indices;
	private final GLEnumDrawType type;
	private final int indCount;
	
	private final ITexture tex;
	
	private final GLVertexArray vao = new GLVertexArray();
	private final GLBuffer vtxbuf = new GLBuffer(GLEnumBufferTarget.GL_ARRAY_BUFFER);
	private final GLBuffer indbuf = new GLBuffer(GLEnumBufferTarget.GL_ARRAY_BUFFER);
	
	private GLProgram program = null;
	
	@SuppressWarnings("unqualified-field-access")
	public SimpleRenderer(FloatBuffer vtx, IntBuffer ind, GLEnumDrawType dtype)
	{
		vertex = vtx;
		indices = ind;
		type = dtype;
		indCount = ind.capacity();
		
		tex = new TextureAsset("/res/test.png");
		
	}
	
	@Override
	public void delete(RenderContext rcon)
	{
		this.program.delete(rcon);
		this.vao.delete(rcon);
		this.vtxbuf.delete(rcon);
		this.indbuf.delete(rcon);
		
	}
	
	@Override
	public boolean initiate(RenderContext rcon)
	{
		this.program = new GLProgram(rcon.getDefaultShaders());
		
		this.vtxbuf.init(rcon, this.vertex, GLEnumDataUsage.GL_STATIC_DRAW);
		this.indbuf.init(rcon, this.indices, GLEnumDataUsage.GL_STATIC_DRAW);
		
		this.vtxbuf.addAttrib(0, 3, GLConst.GL_FLOAT, false, 12, 0);
		
		this.vao.addVBO(this.vtxbuf);
		this.vao.addVBO(this.indbuf);
		
		return true;
	}
	
	@Override
	public void renderImpl(RenderContext rcon) throws RenderException
	{
		if (this.program.bind(rcon))
		{
			if (this.vao.bind(rcon))
			{
				GL1.glActiveTexture(GLConst.GL_TEXTURE0);
				GL1.glBindTexture(this.tex);
				
				GL1.glDrawArrays(this.type, 0, this.indCount);
				
				GL1.glBindTexture(GLConst.GL_TEXTURE_2D, 0);
				
			}
			
			this.vao.unbind(rcon);
			
		}
		
		this.program.unbind(rcon);
		
	}
	
}
