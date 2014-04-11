
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
import com.elusivehawk.engine.assets.Shader;
import com.elusivehawk.engine.core.CaelumEngine;
import com.elusivehawk.engine.render.RenderContext;
import com.elusivehawk.engine.render.RenderHelper;
import com.elusivehawk.engine.render.RenderTicket;
import com.elusivehawk.engine.render.Tessellator;
import com.elusivehawk.engine.util.ArrayHelper;

/**
 * 
 * A class to help work with OpenGL's program system.
 * 
 * @author Elusivehawk
 */
public class GLProgram implements IGLBindable, IAssetReceiver
{
	private final int id, vba;
	private final Shader[] shaders = new Shader[GLEnumShader.values().length];
	private HashMap<VertexBuffer, List<Integer>> vbos = new HashMap<VertexBuffer, List<Integer>>();
	private boolean bound = false, relink = true;
	
	@SuppressWarnings("unqualified-field-access")
	public GLProgram()
	{
		RenderContext context = CaelumEngine.renderContext();
		IGL2 gl2 = context.getGL2();
		
		id = gl2.glCreateProgram();
		vba = context.getGL3().glGenVertexArrays();
		
		context.registerCleanable(this);
		
	}
	
	@Override
	public boolean bind(RenderContext context)
	{
		if (ArrayHelper.isEmpty(this.shaders))
		{
			return false;
		}
		
		if (this.relink)
		{
			for (GLEnumShader stype : GLEnumShader.values())
			{
				Shader s = this.shaders[stype.ordinal()];
				
				if (s != null)
				{
					context.getGL2().glAttachShader(this, s);
					
				}
				
			}
			
			context.getGL2().glLinkProgram(this);
			context.getGL2().glValidateProgram(this);
			
			try
			{
				RenderHelper.checkForGLError(context);
				
				this.relink =  false;
				
			}
			catch (Exception e){}
			
		}
		
		if (!this.bind0(context))
		{
			this.unbind(context);
			
			return false;
		}
		
		return true;
	}
	
	private boolean bind0(RenderContext context)
	{
		if (this.bound)
		{
			return true;
		}
		
		if (context.getGL1().glGetInteger(GLConst.GL_CURRENT_PROGRAM) != 0)
		{
			return false;
		}
		
		context.getGL2().glUseProgram(this);
		
		context.getGL3().glBindVertexArray(this.vba);
		
		if (!this.vbos.isEmpty())
		{
			for (Entry<VertexBuffer, List<Integer>> entry : this.vbos.entrySet())
			{
				context.getGL1().glBindBuffer(entry.getKey());
				
				if (entry.getValue() != null)
				{
					for (int attrib : entry.getValue())
					{
						context.getGL2().glEnableVertexAttribArray(attrib);
						
					}
					
				}
				
			}
			
		}
		
		this.bound = true;
		
		return true;
	}
	
	@Override
	public void unbind(RenderContext context)
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
						context.getGL2().glDisableVertexAttribArray(a);
						
					}
					
				}
				
				context.getGL1().glBindBuffer(entry.getKey().t, 0);
				
			}
			
		}
		
		context.getGL3().glBindVertexArray(0);
		
		context.getGL2().glUseProgram(0);
		
		this.bound = false;
		
	}
	
	@Override
	public void glDelete(RenderContext context)
	{
		if (this.bound)
		{
			this.unbind(context);
			
		}
		
		context.getGL3().glDeleteVertexArrays(this.vba);
		
		for (Shader s : this.shaders)
		{
			if (s == null)
			{
				continue;
			}
			
			context.getGL2().glDetachShader(this, s);
			context.getGL2().glDeleteShader(s);
			
		}
		
		context.getGL2().glDeleteProgram(this);
		
	}
	
	@Override
	public synchronized void onAssetLoaded(Asset a)
	{
		if (a instanceof Shader)
		{
			this.shaders[((Shader)a).gltype.ordinal()] = (Shader)a;
			this.relink = true;
			
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
	
	@Deprecated
	public void attachModel(Tessellator m)
	{
		this.attachVBO(new VertexBuffer(GLConst.GL_ARRAY_BUFFER, m.getPolygons(), GLConst.GL_STREAM_DRAW), Arrays.asList(0, 1, 2));
		this.attachVBO(new VertexBuffer(GLConst.GL_ARRAY_BUFFER, m.getIndices(), GLConst.GL_STATIC_DRAW), null);
		
	}
	
	public void attachRenderTicket(RenderTicket tkt)
	{
		this.attachModel(tkt.getModel());
		this.attachVBO(tkt.getExtraVBO(), Arrays.asList(3, 4, 5));
		
		RenderContext context = CaelumEngine.renderContext();
		
		if (!this.bound && !this.bind(context))
		{
			return;
		}
		
		IGL2 gl2 = context.getGL2();
		
		gl2.glVertexAttribPointer(3, 3, GLConst.GL_FLOAT, false, 0, tkt.getBuffer());
		gl2.glVertexAttribPointer(4, 3, GLConst.GL_FLOAT, false, 3, tkt.getBuffer());
		gl2.glVertexAttribPointer(5, 3, GLConst.GL_FLOAT, false, 6, tkt.getBuffer());
		
		this.unbind(context);
		
	}
	
	public void attachUniform(String name, FloatBuffer info, EnumUniformType type)
	{
		RenderContext context = CaelumEngine.renderContext();
		
		if (!this.bound && !this.bind(context))
		{
			return;
		}
		
		int loc = context.getGL2().glGetUniformLocation(this.id, name);
		
		if (loc == 0)
		{
			return;
		}
		
		type.loadUniform(loc, info);
		
	}
	
	public void attachUniform(String name, IntBuffer info, EnumUniformType type)
	{
		RenderContext context = CaelumEngine.renderContext();
		
		if (!this.bound && !this.bind(context))
		{
			return;
		}
		
		int loc = context.getGL2().glGetUniformLocation(this.id, name);
		
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
