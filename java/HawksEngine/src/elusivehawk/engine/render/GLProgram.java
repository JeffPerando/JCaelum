
package elusivehawk.engine.render;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import org.lwjgl.BufferUtils;
import elusivehawk.engine.util.GameLog;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class GLProgram
{
	public static final int M_TWO = 8, M_THREE = 9, M_FOUR = 10;
	
	private static final List<GLProgram> PROGRAMS = new ArrayList<GLProgram>();
	
	public final int id, vba;
	public final int[] shaders;
	private HashMap<Integer, VertexBufferObject> vbos = new HashMap<Integer, VertexBufferObject>();
	private HashMap<String, Integer> attribs = new HashMap<String, Integer>();
	private boolean linkedRecently = false;
	
	public GLProgram(int... sh)
	{
		id = GL.glCreateProgram();
		
		vba = GL.glGenVertexArrays();
		
		for (int s : sh)
		{
			if (s == 0)
			{
				continue;
			}
			
			GL.glAttachShader(id, s);
			
		}
		
		shaders = sh;
		
		PROGRAMS.add(this);
		
	}
	
	public int attachVBOs(VertexBufferObject... vbos)
	{
		List<VertexBufferObject> valid = new ArrayList<VertexBufferObject>();
		
		for (VertexBufferObject vbo : vbos)
		{
			if (!this.vbos.containsKey(vbo.t))
			{
				valid.add(vbo);
				
			}
			
		}
		
		if (valid.size() == 0)
		{
			return 0;
		}
		
		for (VertexBufferObject obj : valid)
		{
			this.vbos.put(obj.t, obj);
			
		}
		
		return valid.size();
	}
	
	public void attachModel(Model m)
	{
		this.attachVBOs(m.finBuf, m.indiceBuf);
		this.createAttribPointers(m.fin);
		
	}
	
	public void createAttribPointers(FloatBuffer buf)
	{
		this.bind();
		
		this.attachVertex(new String[]{"in_position", "in_color", "in_texcoord"}, new int[]{0, 1, 2}, false);
		
		GL.glVertexAttribPointer(0, 4, false, Model.VERTEX_OFFSET, buf);
		GL.glVertexAttribPointer(1, 4, false, Model.COLOR_OFFSET, buf);
		GL.glVertexAttribPointer(2, 2, false, Model.TEXCOORD_OFFSET, buf);
		
		this.unbind();
		
	}
	
	public void attachUniform(String name, FloatBuffer info, int mode)
	{
		int loc = GL.glGetUniformLocation(this.id, name);
		
		if (loc == 0)
		{
			GameLog.warn("You can't use the non-existent uniform: " + name);
			return;
		}
		
		switch (mode)
		{
			case 1: GL.glUniform1(loc, info);
			case 2: GL.glUniform2(loc, info);
			case 3: GL.glUniform3(loc, info);
			case 4: GL.glUniform4(loc, info);
			case M_TWO: GL.glUniformMatrix2(loc, false, info);
			case M_THREE: GL.glUniformMatrix3(loc, false, info);
			case M_FOUR: GL.glUniformMatrix4(loc, false, info);
			default: throw new RuntimeException("Invalid mode: " + mode);
		}
		
	}
	
	public void attachUniform(String name, IntBuffer info, int mode)
	{
		int loc = GL.glGetUniformLocation(this.id, name);
		
		if (loc == 0)
		{
			GameLog.warn("You can't use the non-existent uniform: " + name);
			return;
		}
		
		switch (mode)
		{
			case 1: GL.glUniform1(loc, info);
			case 2: GL.glUniform2(loc, info);
			case 3: GL.glUniform3(loc, info);
			case 4: GL.glUniform4(loc, info);
			default: throw new RuntimeException("Invalid id: " + mode);
		}
		
	}
	
	public IntBuffer attachVertex(String[] attribs, int[] loc, boolean retLocs)
	{
		if (attribs.length != loc.length)
		{
			throw new ArrayIndexOutOfBoundsException("Uneven attribute/location array!");
		}
		
		if (this.linkedRecently)
		{
			this.linkedRecently = false;
			
		}
		
		IntBuffer ret = retLocs ? BufferUtils.createIntBuffer(loc.length) : null;
		
		for (int c = 0; c < loc.length; c++)
		{
			String attrib = attribs[c];
			int location = loc[c];
			
			if (this.attribs.containsValue(location))
			{
				GameLog.warn("Location " + location + " already exists in program " + this.id);
				continue;
			}
			
			if (this.attribs.containsKey(attrib))
			{
				GameLog.warn("Attribute " + attrib + " already exists in program " + this.id);
				continue;
			}
			
			GL.glBindAttribLocation(this.id, loc[c], attribs[c]);
			
			this.attribs.put(attrib, location);
			
			if (ret != null)
			{
				ret.put(location);
				
			}
			
		}
		
		if (ret != null)
		{
			ret.flip();
			
		}
		
		return ret;
	}
	
	public int getAttrib(String attrib)
	{
		for (String a : this.attribs.keySet())
		{
			if (a.contains(attrib))
			{
				return this.attribs.get(a);
			}
			
		}
		
		return 0;
	}
	
	public void finish()
	{
		GL.glLinkProgram(this);
		GL.glValidateProgram(this);
		
		try
		{
			RenderHelper.checkForGLError();
			
			this.linkedRecently = true;
			
		}
		catch (Exception e)
		{
			GameLog.error(e);
			
		}
		
	}
	
	public boolean bind()
	{
		if (!this.bind0())
		{
			this.unbind();
			
			return false;
		}
		
		return true;
	}
	
	private boolean bind0()
	{
		if (!this.linkedRecently)
		{
			return false;
		}
		
		int p = GL.glGetInteger(GL.GL_CURRENT_PROGRAM);
		
		if (p == this.id)
		{
			return true;
		}
		
		if (p != 0)
		{
			return false;
		}
		
		GL.glUseProgram(this);
		
		GL.glBindVertexArray(this.vba);
		
		if (!this.vbos.isEmpty())
		{
			for (VertexBufferObject vbo : this.vbos.values())
			{
				GL.glBindBuffer(vbo);
				
			}
			
		}
		
		return true;
	}
	
	public void unbind()
	{
		if (!this.vbos.isEmpty())
		{
			for (Integer target : this.vbos.keySet())
			{
				GL.glBindBuffer(target, 0);
				
			}
			
		}
		
		GL.glBindVertexArray(0);
		
		GL.glUseProgram(0);
		
	}
	
	public void delete()
	{
		this.unbind();
		
		if (!this.vbos.isEmpty())
		{
			for (VertexBufferObject vbo : this.vbos.values())
			{
				GL.glDeleteBuffers(vbo);
				
			}
			
		}
		
		GL.glDeleteVertexArrays(this.vba);
		
		for (int shader : this.shaders)
		{
			GL.glDeleteShader(shader);
			
		}
		
		GL.glDeleteProgram(this);
		
	}
	
	public Collection<Integer> getVertexAttribs()
	{
		return this.attribs.values();
	}
	
	public static void deletePrograms()
	{
		if (!PROGRAMS.isEmpty())
		{
			return;
		}
		
		for (GLProgram p : PROGRAMS)
		{
			p.delete();
			
		}
		
	}
	
}
