
package com.elusivehawk.engine.render;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import org.lwjgl.BufferUtils;
import com.elusivehawk.engine.core.GameLog;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class GLProgram implements IGLCleanable
{
	private final int id, vba;
	private final int[] shaders;
	private HashMap<VertexBufferObject, List<Integer>> vbos = new HashMap<VertexBufferObject, List<Integer>>();
	private HashMap<String, Integer> attribs = new HashMap<String, Integer>();
	private boolean linkedRecently = false;
	
	public GLProgram()
	{
		this(RenderHelper.VERTEX_SHADER_3D, RenderHelper.FRAGMENT_SHADER_3D);
		
	}
	
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
	
	public void attachVBO(VertexBufferObject vbo, List<Integer> attribs)
	{
		if (attribs == null || attribs.size() == 0)
		{
			this.vbos.put(vbo, null);
			
			return;
		}
		
		List<Integer> valid = new ArrayList<Integer>();
		
		for (int a : attribs)
		{
			if (!this.attribs.containsValue(a))
			{
				valid.add(a);
				
			}
			
		}
		
		this.vbos.put(vbo, valid.isEmpty() ? attribs : valid);
		
	}
	
	public void attachModel(Model m)
	{
		this.attachVBO(m.finBuf, Arrays.asList(0, 1, 2));
		this.attachVBO(m.indiceBuf, null);
		this.createModelAttribPointers(m.fin);
		
	}
	
	public void attachRenderTicket(RenderTicket tkt)
	{
		this.attachModel(tkt.getModel());
		this.attachVertexAttribs(new String[]{"in_rot", "in_trans", "in_scale"}, new int[]{3, 4, 5}, false);
		this.attachVBO(tkt.getExtraVBO(), Arrays.asList(3, 4, 5));
		
		GL.glVertexAttribPointer(3, 3, false, 0, tkt.getBuffer());
		GL.glVertexAttribPointer(4, 3, false, 3, tkt.getBuffer());
		GL.glVertexAttribPointer(5, 3, false, 6, tkt.getBuffer());
		
	}
	
	public void createModelAttribPointers(FloatBuffer buf)
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
	
	public int getId()
	{
		return this.id;
	}
	
	public int[] getShaders()
	{
		return this.shaders;
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
		if (!this.linkedRecently)
		{
			return false;
		}
		
		if (!this.bind0())
		{
			this.unbind();
			
			return false;
		}
		
		return true;
	}
	
	private boolean bind0()
	{
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
			for (Entry<VertexBufferObject, List<Integer>> entry : this.vbos.entrySet())
			{
				GL.glBindBuffer(entry.getKey());
				
				if (entry.getValue() != null)
				{
					for (int attrib : entry.getValue())
					{
						GL.glEnableVertexAttribArray(attrib);
						
					}
					
				}
				
			}
			
		}
		
		return true;
	}
	
	public void unbind()
	{
		if (!this.vbos.isEmpty())
		{
			for (Entry<VertexBufferObject, List<Integer>> entry : this.vbos.entrySet())
			{
				if (entry.getValue() != null)
				{
					for (int a : entry.getValue())
					{
						GL.glDisableVertexAttribArray(a);
						
					}
					
				}
				
				GL.glBindBuffer(entry.getKey().t, 0);
				
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
	
	@Override
	public int hashCode()
	{
		return this.getId();
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
