
package com.elusivehawk.caelum.render.glsl;

import com.elusivehawk.caelum.render.RenderConst;
import com.elusivehawk.caelum.render.RenderContext;
import com.elusivehawk.caelum.render.gl.GL2;
import com.elusivehawk.caelum.render.gl.GLException;
import com.elusivehawk.caelum.render.gl.GLProgram;
import com.elusivehawk.caelum.render.gl.IGLDeletable;
import com.elusivehawk.util.IDirty;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class Shaders implements IGLDeletable, IDirty
{
	private final IShader[] shaders = new ShaderAsset[RenderConst.SHADER_COUNT];
	
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
		for (IShader sh : this.shaders)
		{
			if (sh == null)
			{
				continue;
			}
			
			if (sh.isCompiled())
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
		
		for (IShader sh : this.shaders)
		{
			if (sh == null)
			{
				continue;
			}
			
			if (!sh.isCompiled())
			{
				sh.compile(rcon);
				
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
	
	public boolean addShader(IShader sh)
	{
		assert sh != null;
		
		if (this.shCount == RenderConst.SHADER_COUNT)
		{
			return false;
		}
		
		if (this.shaders[sh.getType().ordinal()] != null)
		{
			return false;
		}
		
		synchronized (this)
		{
			this.shaders[sh.getType().ordinal()] = sh;
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
		
		for (IShader sh : this.shaders)
		{
			if (sh == null)
			{
				continue;
			}
			
			if (!sh.isCompiled())
			{
				continue;
			}
			
			GL2.glDetachShader(p, sh);
			
		}
		
	}
	
	public IShader getShader(GLSLEnumShaderType type)
	{
		return this.shaders[type.ordinal()];
	}
	
}
