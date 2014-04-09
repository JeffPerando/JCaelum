
package com.elusivehawk.engine.render.opengl;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import com.elusivehawk.engine.core.CaelumEngine;
import com.elusivehawk.engine.render.RenderContext;
import com.elusivehawk.engine.render.RenderHelper;
import com.elusivehawk.engine.util.BufferHelper;
import com.elusivehawk.engine.util.storage.Buffer;

/**
 * 
 * Similar in concept to {@link GLProgram}, but with VBOs.
 * 
 * @author Elusivehawk
 */
public class VertexBufferObject implements IGLBindable
{
	public final int id, t, loadMode;
	
	@SuppressWarnings("unqualified-field-access")
	private VertexBufferObject(int vbo, int target, int mode)
	{
		RenderContext context = CaelumEngine.renderContext();
		
		t = target;
		id = context.getGL1().glIsBuffer(vbo) ? vbo : context.getGL1().glGenBuffers();
		loadMode = mode;
		
		context.registerCleanable(this);
		
	}
	
	public VertexBufferObject(int target, FloatBuffer buf, int mode)
	{
		this(RenderHelper.gl1().glGenBuffers(), target, buf, mode);
		
	}
	
	public VertexBufferObject(int target, IntBuffer buf, int mode)
	{
		this(RenderHelper.gl1().glGenBuffers(), target, buf, mode);
		
	}
	
	public VertexBufferObject(int vbo, int target, FloatBuffer buf, int mode)
	{
		this(vbo, target, mode);
		loadData(buf);
		
	}
	
	public VertexBufferObject(int vbo, int target, IntBuffer buf, int mode)
	{
		this(vbo, target, mode);
		loadData(buf);
		
	}
	
	protected void loadData(FloatBuffer buf)
	{
		RenderContext context = CaelumEngine.renderContext();
		
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
	
	protected void loadData(IntBuffer buf)
	{
		RenderContext context = CaelumEngine.renderContext();
		
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
	
	public void updateEntireVBO(FloatBuffer buf)
	{
		IGL1 gl1 = RenderHelper.gl1();
		
		gl1.glBindBuffer(this);
		gl1.glBufferSubData(this.t, 0, GLConst.GL_FLOAT, buf);
		gl1.glBindBuffer(this.t, 0);
		
	}
	
	public void updateEntireVBO(IntBuffer buf)
	{
		IGL1 gl1 = RenderHelper.gl1();
		
		gl1.glBindBuffer(this);
		gl1.glBufferSubData(this.t, 0, GLConst.GL_INT, buf);
		gl1.glBindBuffer(this.t, 0);
		
	}
	
	public void updateVBOf(Buffer<Float> buf, int offset)
	{
		FloatBuffer nio = BufferHelper.createFloatBuffer(buf.remaining());
		
		for (float f : buf)
		{
			nio.put(f);
			
		}
		
		this.updateVBO(nio, offset);
		
	}
	
	public void updateVBOi(Buffer<Integer> buf, int offset)
	{
		IntBuffer nio = BufferHelper.createIntBuffer(buf.remaining());
		
		for (int i : buf)
		{
			nio.put(i);
			
		}
		
		this.updateVBO(nio, offset);
		
	}
	
	public void updateVBO(FloatBuffer buf, int offset)
	{
		IGL1 gl1 = RenderHelper.gl1();
		
		gl1.glBindBuffer(this);
		gl1.glBufferSubData(this.t, offset * 4, GLConst.GL_FLOAT, buf);
		gl1.glBindBuffer(this.t, 0);
		
	}
	
	public void updateVBO(IntBuffer buf, int offset)
	{
		IGL1 gl1 = RenderHelper.gl1();
		
		gl1.glBindBuffer(this);
		gl1.glBufferSubData(this.t, offset * 4, GLConst.GL_INT, buf);
		gl1.glBindBuffer(this.t, 0);
		
	}
	
	@Override
	public boolean bind(RenderContext context)
	{
		context.getGL1().glBindBuffer(this);
		
		try
		{
			RenderHelper.checkForGLError(context);
			
		}
		catch (Exception e)
		{
			this.unbind(context);
			
			return false;
		}
		
		return true;
	}
	
	@Override
	public void unbind(RenderContext context)
	{
		context.getGL1().glBindBuffer(this.t, 0);
		
	}
	
	@Override
	public void glDelete(RenderContext context)
	{
		context.getGL1().glDeleteBuffers(this);
		
	}
	
}
