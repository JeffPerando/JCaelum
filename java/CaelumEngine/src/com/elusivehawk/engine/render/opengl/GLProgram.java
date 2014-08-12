
package com.elusivehawk.engine.render.opengl;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import com.elusivehawk.engine.assets.Asset;
import com.elusivehawk.engine.assets.IAssetReceiver;
import com.elusivehawk.engine.render.Model;
import com.elusivehawk.engine.render.RenderConst;
import com.elusivehawk.engine.render.RenderContext;
import com.elusivehawk.engine.render.RenderHelper;
import com.elusivehawk.engine.render.Shader;
import com.elusivehawk.util.ArrayHelper;
import com.elusivehawk.util.storage.Few;

/**
 * 
 * A class to help work with OpenGL's program system.
 * 
 * @author Elusivehawk
 */
public final class GLProgram implements IGLBindable, IAssetReceiver
{
	private final Shader[] shaders = RenderHelper.createShaders();
	private final HashMap<VertexBuffer, List<Integer>> vbos = new HashMap<VertexBuffer, List<Integer>>();
	
	private int id = -1, vba = -1, shaderCount = 0;
	private boolean bound = false, relink = true;
	
	public GLProgram()
	{
		this(null);
		
	}
	
	public GLProgram(Shader[] sh)
	{
		if (!ArrayHelper.isNullOrEmpty(sh))
		{
			for (Shader s : sh)
			{
				attachShader(s);
				
			}
			
		}
		
	}
	
	@Override
	public boolean bind(RenderContext con)
	{
		if (this.shaderCount == 0)
		{
			for (Shader sh : con.getDefaultShaders())
			{
				this.attachShader(sh);
				
			}
			
		}
		
		if (this.id == -1)
		{
			this.id = con.getGL2().glCreateProgram();
			this.vba = con.getGL3().glGenVertexArrays();
			
			con.registerCleanable(this);
			
			RenderHelper.checkForGLError(con);
			
		}
		
		if (this.relink)
		{
			IGL2 gl2 = con.getGL2();
			
			for (Shader s : this.shaders)
			{
				if (s != null)
				{
					gl2.glAttachShader(this, s);
					
				}
				
			}
			
			gl2.glLinkProgram(this);
			gl2.glValidateProgram(this);
			
			try
			{
				RenderHelper.checkForGLError(con);
				
				this.relink = false;
				
			}
			catch (Exception e){}
			
		}
		
		if (!this.bind0(con))
		{
			this.unbind(con);
			
			return false;
		}
		
		return true;
	}
	
	private boolean bind0(RenderContext con)
	{
		if (this.bound)
		{
			return true;
		}
		
		if (con.getGL1().glGetInteger(GLConst.GL_CURRENT_PROGRAM) != 0)
		{
			return false;
		}
		
		con.getGL2().glUseProgram(this);
		
		con.getGL3().glBindVertexArray(this.vba);
		
		if (!this.vbos.isEmpty())
		{
			for (Entry<VertexBuffer, List<Integer>> entry : this.vbos.entrySet())
			{
				con.getGL1().glBindBuffer(entry.getKey());
				
				if (entry.getValue() != null)
				{
					for (int attrib : entry.getValue())
					{
						con.getGL2().glEnableVertexAttribArray(attrib);
						
					}
					
				}
				
			}
			
		}
		
		this.bound = true;
		
		return true;
	}
	
	@Override
	public void unbind(RenderContext con)
	{
		if (!this.bound)
		{
			return;
		}
		
		if (!this.vbos.isEmpty())
		{
			for (Entry<VertexBuffer, List<Integer>> entry : this.vbos.entrySet())
			{
				if (entry.getValue() != null)
				{
					for (int a : entry.getValue())
					{
						con.getGL2().glDisableVertexAttribArray(a);
						
					}
					
				}
				
				con.getGL1().glBindBuffer(entry.getKey().t, 0);
				
			}
			
		}
		
		con.getGL3().glBindVertexArray(0);
		
		con.getGL2().glUseProgram(0);
		
		this.bound = false;
		
	}
	
	@Override
	public void glDelete(RenderContext con)
	{
		if (this.bound)
		{
			this.unbind(con);
			
		}
		
		IGL2 gl2 = con.getGL2();
		
		con.getGL3().glDeleteVertexArrays(this.vba);
		
		for (Shader s : this.shaders)
		{
			if (s == null)
			{
				continue;
			}
			
			gl2.glDetachShader(this, s);
			gl2.glDeleteShader(s);
			
		}
		
		gl2.glDeleteProgram(this);
		
	}
	
	@Override
	public synchronized void onAssetLoaded(Asset a)
	{
		if (this.shaderCount < RenderConst.SHADER_COUNT)
		{
			if (a instanceof Shader)
			{
				this.attachShader((Shader)a);
				
			}
			
		}
		
	}
	
	public void attachVBO(VertexBuffer vbo, List<Integer> attribs)
	{
		if (attribs == null || attribs.size() == 0)
		{
			this.vbos.put(vbo, null);
			
			return;
		}
		
		List<Integer> valid = new ArrayList<Integer>(attribs);
		
		for (Entry<VertexBuffer, List<Integer>> entry : this.vbos.entrySet())
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
		Few<VertexBuffer> vbos = m.getVBOs();
		
		this.attachVBO(vbos.one, Arrays.asList(0, 1, 2));
		this.attachVBO(vbos.two, null);
		this.attachVBO(vbos.three, Arrays.asList(4));
		
	}
	
	public synchronized boolean attachShader(Shader sh)
	{
		if (sh == null)
		{
			return false;
		}
		
		if (this.shaders[sh.gltype.ordinal()] == null)
		{
			this.shaderCount++;
			
		}
		
		this.shaders[sh.gltype.ordinal()] = sh;
		this.relink = true;
		
		return true;
	}
	
	public void attachUniform(String name, FloatBuffer info, EnumUniformType type)
	{
		RenderContext con = RenderHelper.renderContext();
		
		if (!this.bound && !this.bind(con))
		{
			return;
		}
		
		int loc = con.getGL2().glGetUniformLocation(this.id, name);
		
		if (loc == 0)
		{
			return;
		}
		
		type.loadUniform(loc, info);
		
	}
	
	public void attachUniform(String name, IntBuffer info, EnumUniformType type)
	{
		RenderContext con = RenderHelper.renderContext();
		
		if (!this.bound && !this.bind(con))
		{
			return;
		}
		
		int loc = con.getGL2().glGetUniformLocation(this.id, name);
		
		if (loc == 0)
		{
			return;
		}
		
		type.loadUniform(loc, info);
		
	}
	
	public int getId()
	{
		return this.id;
	}
	
	public Shader getShader(GLEnumShader type)
	{
		return this.shaders[type.ordinal()];
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
				RenderHelper.gl2().glUniform1f(loc, buf.get());
				
			}
			
			@Override
			public void loadUniform(int loc, IntBuffer buf)
			{
				RenderHelper.gl2().glUniform1i(loc, buf.get());
				
			}
			
		},
		TWO
		{
			@Override
			public void loadUniform(int loc, FloatBuffer buf)
			{
				RenderHelper.gl2().glUniform2f(loc, buf.get(), buf.get());
				
			}
			
			@Override
			public void loadUniform(int loc, IntBuffer buf)
			{
				RenderHelper.gl2().glUniform2i(loc, buf.get(), buf.get());
				
			}
			
		},
		THREE
		{
			@Override
			public void loadUniform(int loc, FloatBuffer buf)
			{
				RenderHelper.gl2().glUniform3f(loc, buf.get(), buf.get(), buf.get());
				
			}
			
			@Override
			public void loadUniform(int loc, IntBuffer buf)
			{
				RenderHelper.gl2().glUniform3i(loc, buf.get(), buf.get(), buf.get());
				
			}
			
		},
		FOUR
		{
			@Override
			public void loadUniform(int loc, FloatBuffer buf)
			{
				RenderHelper.gl2().glUniform4f(loc, buf.get(), buf.get(), buf.get(), buf.get());
				
			}
			
			@Override
			public void loadUniform(int loc, IntBuffer buf)
			{
				RenderHelper.gl2().glUniform4i(loc, buf.get(), buf.get(), buf.get(), buf.get());
				
			}
			
		},
		M_TWO
		{
			@Override
			public void loadUniform(int loc, FloatBuffer buf)
			{
				RenderHelper.gl2().glUniformMatrix2fv(loc, 1, false, buf);
				
			}
			
			@Override
			public void loadUniform(int loc, IntBuffer buf){}
			
		},
		M_THREE
		{
			@Override
			public void loadUniform(int loc, FloatBuffer buf)
			{
				RenderHelper.gl2().glUniformMatrix3fv(loc, 1, false, buf);
				
			}
			
			@Override
			public void loadUniform(int loc, IntBuffer buf){}
			
		},
		M_FOUR
		{
			@Override
			public void loadUniform(int loc, FloatBuffer buf)
			{
				RenderHelper.gl2().glUniformMatrix4fv(loc, 1, false, buf);
				
			}
			
			@Override
			public void loadUniform(int loc, IntBuffer buf){}
			
		};
		
	}
	
}
