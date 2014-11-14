
package com.elusivehawk.caelum.render;

import java.util.UUID;
import com.elusivehawk.caelum.render.gl.GLConst;
import com.elusivehawk.caelum.render.gl.GLEnumUType;
import com.elusivehawk.caelum.render.gl.GLProgram;
import com.elusivehawk.caelum.render.gl.IGL1;
import com.elusivehawk.caelum.render.gl.VertexArray;
import com.elusivehawk.caelum.render.tex.Material;
import com.elusivehawk.caelum.render.tex.Materials;
import com.elusivehawk.util.IDirty;
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
	
	protected final VertexArray vao = new VertexArray();
	
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
		if (!this.initiated)
		{
			if (!this.initiate(rcon))
			{
				return;
			}
			
			this.initiated = true;
			rcon.registerRenderer(this);
			
		}
		
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
		
		if (this.p.bind(rcon))
		{
			if (this.vao.bind(rcon))
			{
				boolean zBuffer = rcon.getGL2().glIsEnabled(GLConst.GL_DEPTH_TEST);
				
				if (zBuffer != this.zBuffer)
				{
					IGL1 gl1 = rcon.getGL1();
					
					if (this.zBuffer)
					{
						gl1.glEnable(GLConst.GL_DEPTH_TEST);
						
					}
					else
					{
						gl1.glDisable(GLConst.GL_DEPTH_TEST);
						
					}
					
				}
				
				this.doRender(rcon);
				
				this.vao.unbind(rcon);
				
			}
			
			this.p.unbind(rcon);
			
		}
		
		this.renderCount--;
		
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
		this.matSet.postRender(rcon);
		
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
