
package com.elusivehawk.caelum.render.gl;

import com.elusivehawk.caelum.render.RenderContext;
import com.elusivehawk.caelum.render.glsl.GLSLEnumShaderType;
import com.elusivehawk.caelum.render.glsl.IShader;
import com.elusivehawk.caelum.render.glsl.Shaders;
import com.elusivehawk.util.IDirty;
import com.elusivehawk.util.IPopulator;
import com.elusivehawk.util.Logger;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public final class GLProgram extends GLObject implements IDirty
{
	private final Shaders shaders;
	
	private int id = 0;
	private boolean relink = true;
	
	public GLProgram()
	{
		this(new Shaders());
		
	}
	
	public GLProgram(IPopulator<GLProgram> pop)
	{
		this();
		
		pop.populate(this);
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public GLProgram(Shaders shs)
	{
		assert shs != null;
		
		shaders = shs;
		
	}
	
	@Override
	public void unbind(RenderContext rcon)
	{
		GL2.glUseProgram(0);
		
	}
	
	@Override
	public boolean isBound(RenderContext rcon)
	{
		return this.id != 0 && GL1.glGetInteger(GLConst.GL_CURRENT_PROGRAM) == this.id;
	}
	
	@Override
	public boolean isDirty()
	{
		return this.shaders.isDirty();
	}
	
	@Override
	public void setIsDirty(boolean b)
	{
		this.shaders.setIsDirty(b);
		
	}
	
	@Override
	public boolean bindImpl(RenderContext rcon)
	{
		int bp = GL1.glGetInteger(GLConst.GL_CURRENT_PROGRAM);
		
		if (bp != 0 && bp != this.id)
		{
			Logger.debug("Failed to bind program %s; Current program ID: %s", this.id, bp);
			
			return false;
		}
		
		if ((this.relink || this.shaders.isDirty()) && !this.relink(rcon))
		{
			Logger.debug("Could not relink program %s; Info log: %s", this.id, GL2.glGetProgramInfoLog(this.id, 2048));
			
			return false;
		}
		
		GL2.glUseProgram(this);
		
		return true;
	}
	
	@Override
	protected void initiate(RenderContext rcon)
	{
		this.id = GL2.glCreateProgram();
		
	}
	
	@Override
	protected void deleteImpl(RenderContext rcon)
	{
		GL2.glDeleteProgram(this);
		
	}
	
	@Override
	public int hashCode()
	{
		return this.getId();
	}
	
	private boolean relink(RenderContext rcon)
	{
		if (!this.shaders.attachShaders(rcon, this))
		{
			return false;
		}
		
		GL2.glLinkProgram(this);
		GL2.glValidateProgram(this);
		
		this.relink = false;
		this.shaders.setIsDirty(false);
		
		return true;
	}
	
	public boolean attachShader(IShader sh)
	{
		if (this.isDeleted())
		{
			return false;
		}
		
		if (this.shaders.addShader(sh))
		{
			this.relink = true;
			return true;
		}
		
		return false;
	}
	
	public int attachShaders(Shaders shs)
	{
		if (this.isDeleted())
		{
			return 0;
		}
		
		int ret = 0;
		
		for (GLSLEnumShaderType st : GLSLEnumShaderType.values())
		{
			if (this.shaders.getShader(st) != null)
			{
				continue;
			}
			
			IShader s = shs.getShader(st);
			
			if (s != null && this.shaders.addShader(s))
			{
				ret++;
				
			}
			
		}
		
		return ret;
	}
	
	public int getId()
	{
		return this.id;
	}
	
	public int shaderCount()
	{
		return this.shaders.getShaderCount();
	}
	
}
