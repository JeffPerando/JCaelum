
package com.elusivehawk.engine.render;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import org.lwjgl.BufferUtils;
import com.elusivehawk.engine.util.GameLog;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class GLProgram implements IGLCleanable
{
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
		
		GL.register(this);
		
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
		
		this.attachVertexAttribs(new String[]{"in_position", "in_color", "in_texcoord"}, new int[]{0, 1, 2}, false);
		
		GL.glVertexAttribPointer(0, 3, false, GL.VERTEX_OFFSET, buf);
		GL.glVertexAttribPointer(1, 4, false, GL.COLOR_OFFSET, buf);
		GL.glVertexAttribPointer(2, 2, false, GL.TEXCOORD_OFFSET, buf);
		
		this.unbind();
		
	}
	
	public void attachUniform(String name, FloatBuffer info, EnumUniformType type)
	{
		int loc = GL.glGetUniformLocation(this.id, name);
		
		if (loc == 0)
		{
			GameLog.warn("You can't use the non-existent uniform: " + name);
			return;
		}
		
		type.loadUniform(loc, info);
		
	}
	
	public void attachUniform(String name, IntBuffer info, EnumUniformType type)
	{
		int loc = GL.glGetUniformLocation(this.id, name);
		
		if (loc == 0)
		{
			GameLog.warn("You can't use the non-existent uniform: " + name);
			return;
		}
		
		type.loadUniform(loc, info);
		
	}
	
	public IntBuffer attachVertexAttribs(String[] attribs, int[] loc, boolean retLocs)
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
	
	@Override
	public void glDelete()
	{
		this.unbind();
		
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
	
	private static interface IUniformType
	{
		public void loadUniform(int loc, FloatBuffer buf);
		
		public void loadUniform(int loc, IntBuffer buf);
		
	}
	
	public static enum EnumUniformType implements IUniformType
	{
		ONE
		{
			@Override
			public void loadUniform(int loc, FloatBuffer buf)
			{
				GL.glUniform1(loc, buf);
				
			}

			@Override
			public void loadUniform(int loc, IntBuffer buf)
			{
				GL.glUniform1(loc, buf);
				
			}
			
		},
		TWO
		{
			@Override
			public void loadUniform(int loc, FloatBuffer buf)
			{
				GL.glUniform2(loc, buf);
				
			}

			@Override
			public void loadUniform(int loc, IntBuffer buf)
			{
				GL.glUniform2(loc, buf);
				
			}
			
		},
		THREE
		{
			@Override
			public void loadUniform(int loc, FloatBuffer buf)
			{
				GL.glUniform3(loc, buf);
				
			}

			@Override
			public void loadUniform(int loc, IntBuffer buf)
			{
				GL.glUniform3(loc, buf);
				
			}
			
		},
		FOUR
		{
			@Override
			public void loadUniform(int loc, FloatBuffer buf)
			{
				GL.glUniform4(loc, buf);
				
			}

			@Override
			public void loadUniform(int loc, IntBuffer buf)
			{
				GL.glUniform4(loc, buf);
				
			}
			
		},
		M_TWO
		{
			@Override
			public void loadUniform(int loc, FloatBuffer buf)
			{
				GL.glUniformMatrix2(loc, false, buf);
			}

			@Override
			public void loadUniform(int loc, IntBuffer buf){}
			
		},
		M_THREE
		{
			@Override
			public void loadUniform(int loc, FloatBuffer buf)
			{
				GL.glUniformMatrix3(loc, false, buf);
			}

			@Override
			public void loadUniform(int loc, IntBuffer buf){}
			
		},
		M_FOUR
		{
			@Override
			public void loadUniform(int loc, FloatBuffer buf)
			{
				GL.glUniformMatrix4(loc, false, buf);
			}

			@Override
			public void loadUniform(int loc, IntBuffer buf){}
			
		};
		
	}
	
}
