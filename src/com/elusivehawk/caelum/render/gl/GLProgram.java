
package com.elusivehawk.caelum.render.gl;

import com.elusivehawk.caelum.render.IBindable;
import com.elusivehawk.caelum.render.IDeletable;
import com.elusivehawk.caelum.render.RenderContext;
import com.elusivehawk.caelum.render.glsl.GLSLEnumShaderType;
import com.elusivehawk.caelum.render.glsl.IShader;
import com.elusivehawk.caelum.render.glsl.Shaders;
import com.elusivehawk.util.IDirty;
import com.elusivehawk.util.IPopulator;
import com.elusivehawk.util.Logger;
import com.elusivehawk.util.storage.ArrayHelper;

/**
 * 
 * A class to help work with OpenGL's program system.
 * 
 * @author Elusivehawk
 */
public final class GLProgram implements IBindable, IDeletable, IDirty
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
	
	public GLProgram(IShader[] sh)
	{
		this();
		
		if (!ArrayHelper.isNullOrEmpty(sh))
		{
			for (IShader s : sh)
			{
				attachShader(s);
				
			}
			
		}
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public GLProgram(Shaders shs)
	{
		shaders = shs;
		
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
	public void delete(RenderContext rcon)
	{
		if (this.isBound(rcon))
		{
			this.unbind(rcon);
			
		}
		
		this.shaders.delete(rcon);
		
		GL2.glDeleteProgram(this);
		
	}
	
	@Override
	public boolean bind(RenderContext rcon)
	{
		int bp = GL1.glGetInteger(GLConst.GL_CURRENT_PROGRAM);
		
		if (bp != 0 && bp != this.id)
		{
			Logger.debug("Failed to bind program %s; Current program ID: %s", this.id, bp);
			
			return false;
		}
		
		if (this.id == 0)
		{
			this.id = GL2.glCreateProgram();
			rcon.registerCleanable(this);
			
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
	public int hashCode()
	{
		return this.getId();
	}
	
	private boolean relink(RenderContext rcon)
	{
		this.shaders.detach(rcon, this);
		
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
		if (this.shaders.addShader(sh))
		{
			this.relink = true;
			return true;
		}
		
		return false;
	}
	
	public int attachShaders(Shaders shs)
	{
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
