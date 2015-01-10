
package com.elusivehawk.caelum.render;

import com.elusivehawk.caelum.render.gl.GL1;
import com.elusivehawk.caelum.render.gl.GL2;
import com.elusivehawk.caelum.render.gl.GLConst;
import com.elusivehawk.caelum.render.gl.GLProgram;
import com.elusivehawk.caelum.render.tex.Color;
import com.elusivehawk.caelum.render.tex.ITexture;
import com.elusivehawk.caelum.render.tex.Material;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public abstract class RenderableObj implements /*IFilterable, */IRenderable
{
	protected final GLProgram program;
	
	protected boolean initiated = false, zBuffer = true;
	
	//protected Filters filters = null;
	
	protected int renderCount = 0;
	
	@SuppressWarnings("unqualified-field-access")
	protected RenderableObj(GLProgram p)
	{
		assert p != null;
		
		program = p;
		
	}
	
	@Override
	public boolean render(RenderContext rcon) throws RenderException
	{
		if (!this.initiated)
		{
			return false;
		}
		
		if (!this.canRender(rcon))
		{
			return false;
		}
		
		if (this.renderCount == RenderConst.RECURSIVE_LIMIT)
		{
			return false;
		}
		
		this.renderCount++;
		
		boolean zBuffer = GL1.glIsEnabled(GLConst.GL_DEPTH_TEST);
		
		if (zBuffer != this.zBuffer)
		{
			if (this.zBuffer)
			{
				GL1.glEnable(GLConst.GL_DEPTH_TEST);
				
			}
			else
			{
				GL1.glDisable(GLConst.GL_DEPTH_TEST);
				
			}
			
		}
		
		boolean ret = false;
		
		if (this.program.bind(rcon))
		{
			if (rcon.doUpdateCamera())
			{
				ICamera cam = rcon.getCamera();
				
				GL2.glUniformMatrix4fv("view", cam.getView().asBuffer());
				GL2.glUniformMatrix4fv("proj", cam.getProjection().asBuffer());
				
			}
			
			ret = this.doRender(rcon);
			
			rcon.releaseTextures();
			
		}
		
		this.program.unbind(rcon);
		
		this.renderCount--;
		
		return ret;
	}
	
	@Override
	public void preRender(RenderContext rcon)
	{
		if (!this.initiated)
		{
			if (!this.initiate(rcon))
			{
				return;
			}
			
			this.initiated = true;
			
		}
		
		if (this.program.bind(rcon))
		{
			//this.manipulateProgram(rcon);
			
			if (rcon.doUpdateScreenFlipUniform())
			{
				GL2.glUniform1i("flip", rcon.isScreenFlipped() ? 1 : 0);
				
			}
			
		}
		
		this.program.unbind(rcon);
		
	}
	
	/*@Override
	public int addFilter(UUID type, IFilter f)
	{
		if (this.filters == null)
		{
			this.setFilters(new Filters());
			
		}
		
		return this.filters.addFilter(type, f);
	}
	
	@Override
	public void removeFilter(UUID type, IFilter f)
	{
		if (this.filters != null)
		{
			this.filters.removeFilter(type, f);
			
		}
		
	}
	
	@Override
	public void removeFilter(UUID type, int i)
	{
		if (this.filters != null)
		{
			this.filters.removeFilter(type, i);
			
		}
		
	}
	
	public synchronized RenderableObj setFilters(Filters fs)
	{
		assert fs != null;
		
		this.filters = fs;
		
		return this;
	}*/
	
	public void setMaterial(Color col)
	{
		this.setMaterial(new Material().filter(col).lock());
		
	}
	
	public void setMaterial(ITexture tex)
	{
		this.setMaterial(new Material().tex(tex).lock());
		
	}
	
	public void setMaterial(ITexture tex, Color col)
	{
		this.setMaterial(new Material().tex(tex).filter(col).lock());
		
	}
	
	public void setMaterial(ITexture tex, boolean invert)
	{
		this.setMaterial(new Material().tex(tex).invert(invert).lock());
		
	}
	
	public synchronized RenderableObj setEnableZBuffer(boolean z)
	{
		this.zBuffer = z;
		
		return this;
	}
	
	public boolean canRender(RenderContext rcon)
	{
		return true;
	}
	
	protected abstract boolean initiate(RenderContext rcon);
	
	protected abstract boolean doRender(RenderContext rcon) throws RenderException;
	
	public abstract void setMaterial(Material mat);
	
}
