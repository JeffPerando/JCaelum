
package com.elusivehawk.caelum.render.gl;

import com.elusivehawk.caelum.render.RenderConst;
import com.elusivehawk.caelum.render.RenderContext;
import com.elusivehawk.util.IDirty;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class Shaders implements IGLDeletable, IDirty
{
	private final Shader[] shaders = new Shader[RenderConst.SHADER_COUNT];
	
	private int shCount = 0;
	private boolean dirty = false;
	
	@Override
	public boolean isDirty()
	{
		return this.dirty;
	}

	@Override
	public synchronized void setIsDirty(boolean b)
	{
		this.dirty = b;
		
	}
	
	@Override
	public void delete(RenderContext rcon)
	{
		for (Shader sh : this.shaders)
		{
			if (sh == null)
			{
				continue;
			}
			
			if (sh.isLoaded())
			{
				sh.delete(rcon);
				
			}
			
		}
		
	}
	
	public int getShaderCount()
	{
		return this.shCount;
	}
	
	public boolean attachShaders(RenderContext rcon, GLProgram p) throws GLException
	{
		boolean ret = false;
		
		for (Shader sh : this.shaders)
		{
			if (sh == null)
			{
				continue;
			}
			
			if (!sh.isLoaded())
			{
				sh.initiate(rcon);
				
			}
			
			if (!GL2.glIsShader(sh))
			{
				continue;
			}
			
			GL2.glAttachShader(p, sh);
			
			ret = true;
			
		}
		
		return ret;
	}
	
	public boolean addShader(Shader sh)
	{
		assert sh != null;
		
		if (this.shCount == RenderConst.SHADER_COUNT)
		{
			return false;
		}
		
		if (this.shaders[sh.gltype.ordinal()] != null)
		{
			return false;
		}
		
		synchronized (this)
		{
			this.shaders[sh.gltype.ordinal()] = sh;
			this.dirty = true;
			this.shCount++;
			
		}
		
		return true;
	}
	
	public void detach(RenderContext rcon, GLProgram p)
	{
		if (this.shCount == 0)
		{
			return;
		}
		
		for (Shader sh : this.shaders)
		{
			if (sh == null)
			{
				continue;
			}
			
			if (!sh.isLoaded())
			{
				continue;
			}
			
			GL2.glDetachShader(p, sh);
			
		}
		
	}
	
	public Shader getShader(GLEnumShader type)
	{
		return this.shaders[type.ordinal()];
	}
	
}
