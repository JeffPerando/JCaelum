
package com.elusivehawk.engine.render.opengl;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import com.elusivehawk.engine.core.CaelumEngine;
import com.elusivehawk.engine.render.RenderHelper;
import com.elusivehawk.engine.render.RenderContext;
import com.elusivehawk.util.BufferHelper;
import com.elusivehawk.util.storage.Buffer;

/**
 * 
 * Similar in concept to {@link GLProgram}, but with VBOs.
 * 
 * @author Elusivehawk
 */
public class VertexBuffer implements IGLBindable
{
	public final int id, t, loadMode;
	
	@SuppressWarnings("unqualified-field-access")
	private VertexBuffer(int vbo, int target, int mode)
	{
		RenderContext con = CaelumEngine.renderContext();
		
		t = target;
		id = con.getGL1().glIsBuffer(vbo) ? vbo : con.getGL1().glGenBuffers();
		loadMode = mode;
		
		con.registerCleanable(this);
		
	}
	
	public VertexBuffer(int target, FloatBuffer buf, int mode)
	{
		this(RenderHelper.gl1().glGenBuffers(), target, buf, mode);
		
	}
	
	public VertexBuffer(int target, IntBuffer buf, int mode)
	{
		this(RenderHelper.gl1().glGenBuffers(), target, buf, mode);
		
	}
	
	public VertexBuffer(int vbo, int target, FloatBuffer buf, int mode)
	{
		this(vbo, target, mode);
		loadData(buf);
		
	}
	
	public VertexBuffer(int vbo, int target, IntBuffer buf, int mode)
	{
		this(vbo, target, mode);
		loadData(buf);
		
	}
	
	protected void loadData(FloatBuffer buf)
	{
		RenderContext con = CaelumEngine.renderContext();
		
		IGL1 gl1 = con.getGL1();
		
		int vba = gl1.glGetInteger(GLConst.GL_VERTEX_ARRAY_BINDING);
		
		if (vba != 0)
		{
			con.getGL3().glBindVertexArray(0);
			
		}
		
		gl1.glBindBuffer(this);
		gl1.glBufferData(this.id, GLConst.GL_FLOAT, buf, this.loadMode);
		gl1.glBindBuffer(this.t, 0);
		
		if (vba != 0)
		{
			con.getGL3().glBindVertexArray(vba);
			
		}
		
	}
	
	protected void loadData(IntBuffer buf)
	{
		RenderContext con = CaelumEngine.renderContext();
		
		int vba = con.getGL1().glGetInteger(GLConst.GL_VERTEX_ARRAY_BINDING);
		
		if (vba != 0)
		{
			con.getGL3().glBindVertexArray(0);
			
		}
		
		IGL1 gl1 = con.getGL1();
		
		gl1.glBindBuffer(this);
		gl1.glBufferData(this.t, GLConst.GL_INT, buf, this.loadMode);
		gl1.glBindBuffer(this.t, 0);
		
		if (vba != 0)
		{
			con.getGL3().glBindVertexArray(vba);
			
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
	public boolean bind(RenderContext con)
	{
		IGL1 gl1 = con.getGL1();
		
		gl1.glBindBuffer(this);
		
		try
		{
			RenderHelper.checkForGLError(gl1);
			
		}
		catch (Exception e)
		{
			this.unbind(con);
			
			return false;
		}
		
		return true;
	}
	
	@Override
	public void unbind(RenderContext con)
	{
		con.getGL1().glBindBuffer(this.t, 0);
		
	}
	
	@Override
	public void glDelete(RenderContext con)
	{
		con.getGL1().glDeleteBuffers(this);
		
	}
	
}
