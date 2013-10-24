
package com.elusivehawk.engine.render;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import com.elusivehawk.engine.core.GameLog;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class VertexBufferObject implements IGLCleanable
{
	public final int id, t, loadMode;
	
	private VertexBufferObject(int vbo, int target, int mode)
	{
		if (!GL.glIsBuffer(vbo))
		{
			GameLog.warn("VBO ID isn't valid: " + vbo + ", rectifying...");
			
			vbo = GL.glGenBuffers();
			
		}
		
		t = target;
		id = vbo;
		loadMode = mode;
		
		GL.register(this);
		
	}
	
	public VertexBufferObject(int target, FloatBuffer buf, int mode)
	{
		this(GL.glGenBuffers(), target, buf, mode);
		
	}
	
	public VertexBufferObject(int target, IntBuffer buf, int mode)
	{
		this(GL.glGenBuffers(), target, buf, mode);
		
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
		int vba = GL.glGetInteger(GL.GL_VERTEX_ARRAY_BINDING);
		
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
	
	protected void loadData(IntBuffer buf)
	{
		int vba = GL.glGetInteger(GL.GL_VERTEX_ARRAY_BINDING);
		
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
	
	public void updateEntireVBO(FloatBuffer buf)
	{
		GL.glBindBuffer(this);
		GL.glBufferSubData(this.t, 0, buf);
		GL.glBindBuffer(this.t, 0);
		
	}
	
	public void updateEntireVBO(IntBuffer buf)
	{
		GL.glBindBuffer(this);
		GL.glBufferSubData(this.t, 0, buf);
		GL.glBindBuffer(this.t, 0);
		
	}
	
	public void updateVBO(FloatBuffer buf, int offset)
	{
		GL.glBindBuffer(this);
		GL.glBufferSubData(this.t, offset * 4, buf);
		GL.glBindBuffer(this.t, 0);
		
	}
	
	public void updateVBO(IntBuffer buf, int offset)
	{
		GL.glBindBuffer(this);
		GL.glBufferSubData(this.t, offset * 4, buf);
		GL.glBindBuffer(this.t, 0);
		
	}
	
	@Override
	public void glDelete()
	{
		GL.glDeleteBuffers(this);
		
	}
	
}
