
package com.elusivehawk.engine.render;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import com.elusivehawk.engine.core.CaelumEngine;
import com.elusivehawk.engine.core.EnumLogType;

/**
 * 
 * A class to help work with OpenGL's program system.
 * 
 * @author Elusivehawk
 */
public class GLProgram implements IGLCleanable
{
	private final int id, vba;
	private final int[] shaders;
	private HashMap<VertexBufferObject, List<Integer>> vbos = new HashMap<VertexBufferObject, List<Integer>>();
	
	private GLProgram()
	{
		this(RenderHelper.VERTEX_SHADER_3D, RenderHelper.FRAGMENT_SHADER_3D);
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	private GLProgram(int... sh)
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
		
		GL.glLinkProgram(this);
		GL.glValidateProgram(this);
		
		try
		{
			RenderHelper.checkForGLError();
			
		}
		catch (Exception e)
		{
			CaelumEngine.instance().getLog().log(EnumLogType.ERROR, null, e);
			
		}
		
		shaders = sh;
		
		GL.register(this);
		
	}
	
	public static GLProgram create(int... sh)
	{
		return (sh == null || sh.length == 0) ? new GLProgram() : new GLProgram(sh);
	}
	
	public void attachVBO(VertexBufferObject vbo, List<Integer> attribs)
	{
		if (attribs == null || attribs.size() == 0)
		{
			this.vbos.put(vbo, null);
			
			return;
		}
		
		List<Integer> valid = new ArrayList<Integer>(attribs);
		
		for (Entry<VertexBufferObject, List<Integer>> entry : this.vbos.entrySet())
		{
			for (int a : attribs)
			{
				if (entry.getValue().contains(a))
				{
					valid.remove((Integer)a);
					
				}
				
			}
			
		}
		
		if (valid.isEmpty())
		{
			return;
		}
		
		this.vbos.put(vbo, valid);
		
	}
	
	public void attachModel(Model m)
	{
		this.attachVBO(m.finBuf.get(), Arrays.asList(0, 1, 2));
		this.attachVBO(m.indiceBuf.get(), null);
		
	}
	
	public void attachRenderTicket(RenderTicket tkt)
	{
		this.attachModel(tkt.getModel());
		this.attachVBO(tkt.getExtraVBO(), Arrays.asList(3, 4, 5));
		
		GL.glVertexAttribPointer(3, 3, false, 0, tkt.getBuffer());
		GL.glVertexAttribPointer(4, 3, false, 3, tkt.getBuffer());
		GL.glVertexAttribPointer(5, 3, false, 6, tkt.getBuffer());
		
	}
	
	public void attachUniform(String name, FloatBuffer info, EnumUniformType type)
	{
		int loc = GL.glGetUniformLocation(this.id, name);
		
		if (loc == 0)
		{
			CaelumEngine.instance().getLog().log(EnumLogType.WARN, "You can't use the non-existent uniform: " + name);
			return;
		}
		
		type.loadUniform(loc, info);
		
	}
	
	public void attachUniform(String name, IntBuffer info, EnumUniformType type)
	{
		int loc = GL.glGetUniformLocation(this.id, name);
		
		if (loc == 0)
		{
			CaelumEngine.instance().getLog().log(EnumLogType.WARN, "You can't use the non-existent uniform: " + name);
			return;
		}
		
		type.loadUniform(loc, info);
		
	}
	
	public int getId()
	{
		return this.id;
	}
	
	public int[] getShaders()
	{
		return this.shaders;
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
