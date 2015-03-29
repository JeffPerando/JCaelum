
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
	
	private final GLProgram program = new GLProgram(RenderConst.SHADERS_3D);
	private final GLVertexArray vao = new GLVertexArray();
	private final GLBuffer vtxbuf = new GLBuffer(GLEnumBufferTarget.GL_ARRAY_BUFFER);
	private final GLBuffer indbuf = new GLBuffer(GLEnumBufferTarget.GL_ARRAY_BUFFER);
	
	@SuppressWarnings("unqualified-field-access")
	public SimpleRenderer(FloatBuffer vtx, IntBuffer ind, GLEnumDrawType dtype)
	{
		vertex = vtx;
		indices = ind;
		type = dtype;
		indCount = ind.capacity();
		
		tex = (ITexture)new TextureAsset("/res/test.png").readLater();
		
	}
	
	@Override
	public void dispose()
	{
		this.program.dispose();
		this.vao.dispose();
		this.vtxbuf.dispose();
		this.indbuf.dispose();
		
	}
	
	@Override
	protected boolean preRenderImpl(RenderContext rcon) throws RenderException
	{
		return true;
	}
	
	@Override
	protected void renderImpl(RenderContext rcon) throws RenderException
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
	
	@Override
	protected void postRenderImpl(RenderContext rcon) throws RenderException{}
	
	@Override
	public boolean initiate(RenderContext rcon)
	{
		this.vtxbuf.init(rcon, this.vertex, GLEnumDataUsage.GL_STATIC_DRAW);
		this.indbuf.init(rcon, this.indices, GLEnumDataUsage.GL_STATIC_DRAW);
		
		this.vtxbuf.addAttrib(0, 3, GLConst.GL_FLOAT, false, 12, 0);
		
		this.vao.addVBO(this.vtxbuf);
		this.vao.addVBO(this.indbuf);
		
		return true;
	}
	
}
