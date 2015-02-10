
package com.elusivehawk.caelum.render.glsl;

import com.elusivehawk.caelum.render.IDeletable;
import com.elusivehawk.caelum.render.RenderConst;
import com.elusivehawk.caelum.render.RenderContext;
import com.elusivehawk.caelum.render.gl.GL2;
import com.elusivehawk.caelum.render.gl.GLException;
import com.elusivehawk.caelum.render.gl.GLProgram;
import com.elusivehawk.util.Dirtable;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class Shaders extends Dirtable implements IDeletable
{
	private final IShader[] shaders = new ShaderAsset[RenderConst.SHADER_COUNT];
	
	private int shCount = 0;
	
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
			this.shCount++;
			this.setIsDirty(true);
			
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
