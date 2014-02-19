
package com.elusivehawk.engine.render.opengl;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import com.elusivehawk.engine.render2.RenderContext;
import com.elusivehawk.engine.render2.RenderHelper;
import com.elusivehawk.engine.util.Buffer;
import com.elusivehawk.engine.util.BufferHelper;

/**
 * 
 * Similar in concept to {@link GLProgram}, but with VBOs.
 * 
 * @author Elusivehawk
 */
public class VertexBufferObject implements IGLCleanable
{
	public final int id, t, loadMode;
	
	@SuppressWarnings("unqualified-field-access")
	private VertexBufferObject(int vbo, int target, int mode, RenderContext context)
	{
		t = target;
		id = GL.glIsBuffer(vbo) ? vbo : GL.glGenBuffers();
		loadMode = mode;
		
		RenderHelper.register(this);
		
	}
	
	public VertexBufferObject(int target, FloatBuffer buf, int mode, RenderContext context)
	{
		this(GL.glGenBuffers(), target, buf, mode);
		
	}
	
	public VertexBufferObject(int target, IntBuffer buf, int mode, RenderContext context)
	{
		this(GL.glGenBuffers(), target, buf, mode);
		
	}
	
	public VertexBufferObject(int vbo, int target, FloatBuffer buf, int mode, RenderContext context)
	{
		this(vbo, target, mode, context);
		loadData(buf, context);
		
	}
	
	public VertexBufferObject(int vbo, int target, IntBuffer buf, int mode, RenderContext context)
	{
		this(vbo, target, mode, context);
		loadData(buf, context);
		
	}
	
	protected void loadData(FloatBuffer buf, RenderContext context)
	{
		int vba = GL.glGetInteger(GLConst.GL_VERTEX_ARRAY_BINDING);
		
		if (vba != 0)
		{
			GL.glBindVertexArray(0);
			
		}
		
		GL.glBindBuffer(this);
		GL.glBufferData(this.id, buf, this.loadMode);
		GL.glBindBuffer(this.t, 0);
		
		if (vba != 0)
		{
			GL.glBindVertexArray(vba);
			
		}
		
	}
	
	protected void loadData(IntBuffer buf, RenderContext context)
	{
		int vba = GL.glGetInteger(GLConst.GL_VERTEX_ARRAY_BINDING);
		
		if (vba != 0)
		{
			GL.glBindVertexArray(0);
			
		}
		
		GL.glBindBuffer(this);
		GL.glBufferData(this.t, buf, this.loadMode);
		GL.glBindBuffer(this.t, 0);
		
		if (vba != 0)
		{
			GL.glBindVertexArray(vba);
			
		}
		
	}
	
	public void updateEntireVBO(FloatBuffer buf, RenderContext context)
	{
		GL.glBindBuffer(this);
		GL.glBufferSubData(this.t, 0, buf);
		GL.glBindBuffer(this.t, 0);
		
	}
	
	public void updateEntireVBO(IntBuffer buf, RenderContext context)
	{
		GL.glBindBuffer(this);
		GL.glBufferSubData(this.t, 0, buf);
		GL.glBindBuffer(this.t, 0);
		
	}
	
	public void updateVBOf(Buffer<Float> buf, int offset, RenderContext context)
	{
		FloatBuffer nio = BufferHelper.createFloatBuffer(buf.remaining());
		
		for (float f : buf)
		{
			nio.put(f);
			
		}
		
		this.updateVBO(nio, offset, context);
		
	}
	
	public void updateVBOi(Buffer<Integer> buf, int offset, RenderContext context)
	{
		IntBuffer nio = BufferHelper.createIntBuffer(buf.remaining());
		
		for (int i : buf)
		{
			nio.put(i);
			
		}
		
		this.updateVBO(nio, offset, context);
		
	}
	
	public void updateVBO(FloatBuffer buf, int offset, RenderContext context)
	{
		GL.glBindBuffer(this);
		GL.glBufferSubData(this.t, offset * 4, buf);
		GL.glBindBuffer(this.t, 0);
		
	}
	
	public void updateVBO(IntBuffer buf, int offset, RenderContext context)
	{
		GL.glBindBuffer(this);
		GL.glBufferSubData(this.t, offset * 4, buf);
		GL.glBindBuffer(this.t, 0);
		
	}
	
	@Override
	public void glDelete(RenderContext context)
	{
		GL.glDeleteBuffers(this);
		
	}
	
}
