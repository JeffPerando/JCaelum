
package com.elusivehawk.caelum.render;

import java.util.UUID;
import com.elusivehawk.caelum.render.gl.GL1;
import com.elusivehawk.caelum.render.gl.GL2;
import com.elusivehawk.caelum.render.gl.GLConst;
import com.elusivehawk.caelum.render.gl.GLEnumUType;
import com.elusivehawk.caelum.render.gl.GLProgram;
import com.elusivehawk.caelum.render.gl.GLVertexArray;
import com.elusivehawk.caelum.render.tex.Material;
import com.elusivehawk.caelum.render.tex.Materials;
import com.elusivehawk.util.IDirty;
import com.elusivehawk.util.Logger;
import com.elusivehawk.util.storage.BufferHelper;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public abstract class RenderableObj implements IDirty, IFilterable, IRenderable
{
	protected final GLProgram p;
	
	protected final GLVertexArray vao = new GLVertexArray();
	
	protected boolean dirty = true, zBuffer = true;
	protected boolean initiated = false;
	
	protected Filters filters = null;
	protected Materials matSet = null;
	
	protected int renderCount = 0;
	
	protected RenderableObj()
	{
		this(new GLProgram());
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	protected RenderableObj(GLProgram program)
	{
		assert program != null;
		
		p = program;
		
	}
	
	@Override
	public void render(RenderContext rcon) throws RenderException
	{
		ICamera cam = rcon.getCamera();
		
		if (this.isCulled(cam))
		{
			return;
		}
		
		if (this.renderCount == RenderConst.RECURSIVE_LIMIT)
		{
			return;
		}
		
		this.renderCount++;
		
		if (!this.initiated)
		{
			if (!this.initiate(rcon))
			{
				return;
			}
			
			this.initiated = true;
			
		}
		
		if (rcon.doUpdateCamera())
		{
			this.p.attachUniform(rcon, "view", cam.getView().asBuffer(), GLEnumUType.M_FOUR);
			this.p.attachUniform(rcon, "proj", cam.getProjection().asBuffer(), GLEnumUType.M_FOUR);
			
		}
		
		if (this.matSet != null && this.matSet.isDirty())
		{
			this.matSet.render(rcon);
			
			//TODO Load materials into program
			
		}
		
		boolean zBuffer = GL2.glIsEnabled(GLConst.GL_DEPTH_TEST);
		
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
		
		if (this.p.bind(rcon))
		{
			if (this.vao.bind(rcon))
			{
				this.doRender(rcon);
				
			}
			else
			{
				Logger.log().debug("VAO NOGO");
				
			}
			
			this.vao.unbind(rcon);
			
		}
		else
		{
			Logger.log().debug("PROGRAM NOGO");
			
		}
		
		this.p.unbind(rcon);
		
		this.renderCount--;
		
	}
	
	@Override
	public void preRender(RenderContext rcon, double delta)
	{
		if (this.matSet != null)
		{
			this.matSet.preRender(rcon, delta);
			
		}
		
		if (this.isDirty())
		{
			this.p.attachUniform(rcon, "flip", BufferHelper.makeIntBuffer(rcon.isScreenFlipped() ? 1 : 0), GLEnumUType.ONE);
			
			if (this.filters != null)
			{
				this.filters.filter(rcon, this.p);
				
			}
			
			this.setIsDirty(false);
			
		}
		
	}
	
	@Override
	public void postRender(RenderContext rcon)
	{
		if (this.matSet != null)
		{
			this.matSet.postRender(rcon);
			
		}
		
	}
	
	@Override
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
	
	public synchronized RenderableObj setFilters(Filters fs)
	{
		assert fs != null;
		
		this.filters = fs;
		
		return this;
	}
	
	public synchronized RenderableObj setMaterials(Materials ms)
	{
		assert ms != null;
		
		this.matSet = ms;
		
		return this;
	}
	
	public void setMaterial(int i, Material mat)
	{
		if (this.matSet == null)
		{
			this.setMaterials(new Materials());
			
		}
		
		this.matSet.set(i, mat);
		
	}
	
	public synchronized boolean addMaterials(Material... ms)
	{
		if (this.matSet == null)
		{
			this.setMaterials(new Materials());
			
		}
		
		return this.matSet.addMaterials(ms);
	}
	
	public synchronized RenderableObj setEnableZBuffer(boolean z)
	{
		this.zBuffer = z;
		
		return this;
	}
	
	public int getMaterialCount()
	{
		return this.matSet == null ? 0 : this.matSet.size();
	}
	
	public boolean isCulled(ICamera cam)
	{
		return false;
	}
	
	protected abstract boolean initiate(RenderContext rcon);
	
	protected abstract void doRender(RenderContext rcon) throws RenderException;
	
}
