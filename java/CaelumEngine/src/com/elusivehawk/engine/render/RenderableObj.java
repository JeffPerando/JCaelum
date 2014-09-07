
package com.elusivehawk.engine.render;

import java.util.List;
import java.util.UUID;
import com.elusivehawk.engine.assets.Asset;
import com.elusivehawk.engine.assets.IAssetReceiver;
import com.elusivehawk.engine.render.opengl.GLProgram;
import com.elusivehawk.util.IDirty;
import com.google.common.collect.Lists;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public abstract class RenderableObj implements IDirty, IFilterable, IRenderable, IAssetReceiver
{
	protected final GLProgram p;
	
	protected final List<IDirty> dirts = Lists.newArrayList();
	
	protected boolean dirty = true, initiated = false;
	protected Filters filters = null;
	protected MaterialSet matSet = null;
	
	protected RenderableObj()
	{
		this(new GLProgram());
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	protected RenderableObj(GLProgram program)
	{
		assert program != null;
		
		p = program;
		
		dirts.add(program);
		
	}
	
	@Override
	public void onAssetLoaded(Asset a)
	{
		if (a instanceof Shader)
		{
			this.p.attachShader((Shader)a);
			
		}
		
		if (a instanceof Texture)
		{
			this.addMaterials(new Material(((Texture)a)));
			
		}
		
	}
	
	@Override
	public void render(RenderContext rcon, double delta) throws RenderException
	{
		if (!this.initiated)
		{
			if (!this.initiate(rcon))
			{
				return;
			}
			
			this.initiated = true;
			
		}
		
		if (!this.updateBeforeRender(rcon, delta))
		{
			return;
		}
		
		if (this.isDirty())
		{
			//TODO Load materials into program
			
			if (this.filters != null)
			{
				this.filters.filter(rcon, this.p);
				
			}
			
			this.setIsDirty(false);
			
		}
		
		if (this.p.bind(rcon))
		{
			this.doRender(rcon, delta);
			
			this.p.unbind(rcon);
			
		}
		
		this.postRender(rcon);
		
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
		if (!this.dirty)
		{
			for (IDirty d : this.dirts)
			{
				if (d.isDirty())
				{
					this.dirty = true;
					break;
				}
				
			}
			
		}
		
		return this.dirty;
	}
	
	@Override
	public synchronized void setIsDirty(boolean b)
	{
		if (!(this.dirty = b))
		{
			this.dirts.forEach(((d) -> {d.setIsDirty(false);}));
			
		}
		
	}
	
	public synchronized void setFilters(Filters fs)
	{
		if (this.filters != null)
		{
			this.dirts.remove(this.filters);
			
		}
		
		this.filters = fs;
		
		if (fs != null)
		{
			this.dirts.add(fs);
			
		}
		
	}
	
	public synchronized RenderableObj setMaterials(MaterialSet ms)
	{
		if (this.matSet != null)
		{
			this.dirts.remove(this.matSet);
			
		}
		
		this.matSet = ms;
		
		if (ms != null)
		{
			this.dirts.add(ms);
			
		}
		
		return this;
	}
	
	public synchronized boolean addMaterials(Material... ms)
	{
		if (this.matSet == null)
		{
			this.setMaterials(new MaterialSet());
			
		}
		
		return this.matSet.addMaterials(ms);
	}
	
	public int getMaterialCount()
	{
		return this.matSet == null ? 0 : this.matSet.matCount();
	}
	
	protected abstract boolean initiate(RenderContext rcon);
	
	protected abstract void doRender(RenderContext rcon, double delta) throws RenderException;
	
}
