
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
		id = context.getGL1().glIsBuffer(vbo) ? vbo : context.getGL1().glGenBuffers();
		loadMode = mode;
		
		RenderHelper.register(this);
		
	}
	
	public VertexBufferObject(int target, FloatBuffer buf, int mode, RenderContext context)
	{
		this(context.getGL1().glGenBuffers(), target, buf, mode, context);
		
	}
	
	public VertexBufferObject(int target, IntBuffer buf, int mode, RenderContext context)
	{
		this(context.getGL1().glGenBuffers(), target, buf, mode, context);
		
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
		int vba = context.getGL1().glGetInteger(GLConst.GL_VERTEX_ARRAY_BINDING);
		
		if (vba != 0)
		{
			context.getGL3().glBindVertexArray(0);
			
		}
		
		context.getGL1().glBindBuffer(this);
		context.getGL1().glBufferData(this.id, GLConst.GL_FLOAT, buf, this.loadMode);
		context.getGL1().glBindBuffer(this.t, 0);
		
		if (vba != 0)
		{
			context.getGL3().glBindVertexArray(vba);
			
		}
		
	}
	
	protected void loadData(IntBuffer buf, RenderContext context)
	{
		int vba = context.getGL1().glGetInteger(GLConst.GL_VERTEX_ARRAY_BINDING);
		
		if (vba != 0)
		{
			context.getGL3().glBindVertexArray(0);
			
		}
		
		context.getGL1().glBindBuffer(this);
		context.getGL1().glBufferData(this.t, GLConst.GL_INT, buf, this.loadMode);
		context.getGL1().glBindBuffer(this.t, 0);
		
		if (vba != 0)
		{
			context.getGL3().glBindVertexArray(vba);
			
		}
		
	}
	
	public void updateEntireVBO(FloatBuffer buf, RenderContext context)
	{
		context.getGL1().glBindBuffer(this);
		context.getGL1().glBufferSubData(this.t, 0, GLConst.GL_FLOAT, buf);
		context.getGL1().glBindBuffer(this.t, 0);
		
	}
	
	public void updateEntireVBO(IntBuffer buf, RenderContext context)
	{
		context.getGL1().glBindBuffer(this);
		context.getGL1().glBufferSubData(this.t, 0, GLConst.GL_INT, buf);
		context.getGL1().glBindBuffer(this.t, 0);
		
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
		context.getGL1().glBindBuffer(this);
		context.getGL1().glBufferSubData(this.t, offset * 4, GLConst.GL_FLOAT, buf);
		context.getGL1().glBindBuffer(this.t, 0);
		
	}
	
	public void updateVBO(IntBuffer buf, int offset, RenderContext context)
	{
		context.getGL1().glBindBuffer(this);
		context.getGL1().glBufferSubData(this.t, offset * 4, GLConst.GL_INT, buf);
		context.getGL1().glBindBuffer(this.t, 0);
		
	}
	
	@Override
	public void glDelete(RenderContext context)
	{
		context.getGL1().glDeleteBuffers(this);
		
	}
	
}
