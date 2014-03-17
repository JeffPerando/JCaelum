
package com.elusivehawk.engine.render.opengl;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import com.elusivehawk.engine.core.CaelumEngine;
import com.elusivehawk.engine.render.RenderHelper;
import com.elusivehawk.engine.util.Buffer;
import com.elusivehawk.engine.util.BufferHelper;

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
		IGL1 gl1 = RenderHelper.gl1();
		
		t = target;
		id = gl1.glIsBuffer(vbo) ? vbo : gl1.glGenBuffers();
		loadMode = mode;
		
		CaelumEngine.renderContext().registerCleanable(this);
		
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
		IGL1 gl1 = RenderHelper.gl1();
		IGL3 gl3 = RenderHelper.gl3();
		
		int vba = gl1.glGetInteger(GLConst.GL_VERTEX_ARRAY_BINDING);
		
		if (vba != 0)
		{
			gl3.glBindVertexArray(0);
			
		}
		
		gl1.glBindBuffer(this);
		gl1.glBufferData(this.id, GLConst.GL_FLOAT, buf, this.loadMode);
		gl1.glBindBuffer(this.t, 0);
		
		if (vba != 0)
		{
			gl3.glBindVertexArray(vba);
			
		}
		
	}
	
	protected void loadData(IntBuffer buf)
	{
		IGL1 gl1 = RenderHelper.gl1();
		IGL3 gl3 = RenderHelper.gl3();
		
		int vba = gl1.glGetInteger(GLConst.GL_VERTEX_ARRAY_BINDING);
		
		if (vba != 0)
		{
			gl3.glBindVertexArray(0);
			
		}
		
		gl1.glBindBuffer(this);
		gl1.glBufferData(this.t, GLConst.GL_INT, buf, this.loadMode);
		gl1.glBindBuffer(this.t, 0);
		
		if (vba != 0)
		{
			gl3.glBindVertexArray(vba);
			
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
	public boolean bind(int... extras)
	{
		RenderHelper.gl1().glBindBuffer(this);
		
		try
		{
			RenderHelper.checkForGLError();
			
		}
		catch (Exception e)
		{
			this.unbind();
			
			return false;
		}
		
		return true;
	}
	
	@Override
	public void unbind(int... extras)
	{
		RenderHelper.gl1().glBindBuffer(this.t, 0);
		
	}
	
	@Override
	public void glDelete()
	{
		RenderHelper.gl1().glDeleteBuffers(this);
		
	}
	
}
