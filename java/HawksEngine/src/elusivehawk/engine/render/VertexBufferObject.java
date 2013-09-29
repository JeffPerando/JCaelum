
package elusivehawk.engine.render;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import elusivehawk.engine.util.GameLog;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class VertexBufferObject
{
	public final int id, t;
	
	public VertexBufferObject(int target)
	{
		this(GL.glGenBuffers(), target);
		
	}
	
	public VertexBufferObject(IntBuffer buf, int target)
	{
		this(buf.get(), target);
		
	}
	
	public VertexBufferObject(int vbo, int target)
	{
		if (!GL.glIsBuffer(vbo))
		{
			GameLog.warn("VBO ID isn't valid: " + vbo + ", rectifying...");
			
			vbo = GL.glGenBuffers();
			
		}
		
		t = target;
		id = vbo;
		
	}
	
	public VertexBufferObject(int target, FloatBuffer buf, int mode)
	{
		this(target);
		loadData(buf, mode);
		
	}
	
	public VertexBufferObject(int target, IntBuffer buf, int mode)
	{
		this(target);
		loadData(buf, mode);
		
	}
	
	public VertexBufferObject(int vbo, int target, FloatBuffer buf, int mode)
	{
		this(vbo, target);
		loadData(buf, mode);
		
	}
	
	public VertexBufferObject(int vbo, int target, IntBuffer buf, int mode)
	{
		this(vbo, target);
		loadData(buf, mode);
		
	}
	
	public void loadData(FloatBuffer buf, int mode)
	{
		int vba = GL.glGetInteger(GL.GL_VERTEX_ARRAY_BINDING);
		
		if (vba != 0)
		{
			GL.glBindVertexArray(0);
			
		}
		
		GL.glBindBuffer(this);
		GL.glBufferData(this.id, buf, mode);
		GL.glBindBuffer(this.t, 0);
		
		if (vba != 0)
		{
			GL.glBindVertexArray(vba);
			
		}
		
	}
	
	public void loadData(IntBuffer buf, int mode)
	{
		int vba = GL.glGetInteger(GL.GL_VERTEX_ARRAY_BINDING);
		
		if (vba != 0)
		{
			GL.glBindVertexArray(0);
			
		}
		
		GL.glBindBuffer(this);
		GL.glBufferData(this.t, buf, mode);
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
	
}
