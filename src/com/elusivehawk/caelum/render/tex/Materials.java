
package com.elusivehawk.caelum.render.tex;

import java.util.ArrayList;
import java.util.List;
import com.elusivehawk.caelum.render.IRenderable;
import com.elusivehawk.caelum.render.RenderConst;
import com.elusivehawk.caelum.render.RenderContext;
import com.elusivehawk.caelum.render.RenderException;
import com.elusivehawk.util.IDirty;
import com.elusivehawk.util.storage.IArray;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class Materials implements IRenderable, IArray<Material>, IDirty
{
	private final List<Material> mats = new ArrayList<Material>(RenderConst.MATERIAL_CAP);
	private boolean dirty = false, isStatic = true, finished = false;
	
	@Override
	public boolean isDirty()
	{
		return this.dirty || !this.isStatic;
	}
	
	@Override
	public void setIsDirty(boolean b)
	{
		this.dirty = b;
		
	}
	
	@Override
	public int size()
	{
		return this.mats.size();
	}
	
	@Override
	public Material get(int i)
	{
		return this.mats.get(i);
	}
	
	@Override
	public IArray<? extends Material> set(int i, Material mat)
	{
		if (this.size() == RenderConst.MATERIAL_CAP)
		{
			return this;
		}
		
		if (this.isImmutable())
		{
			return this;
		}
		
		if (mat == null)
		{
			return this;
		}
		
		synchronized (this)
		{
			this.mats.add(i, mat);
			
		}
		
		return this;
	}
	
	@Override
	public void render(RenderContext rcon) throws RenderException
	{
		this.forEach(((mat) -> {mat.render(rcon);}));
		
	}
	
	@Override
	public void preRender(RenderContext rcon, double delta)
	{
		this.forEach(((mat) -> {mat.preRender(rcon, delta);}));
		
	}
	
	@Override
	public void postRender(RenderContext rcon)
	{
		this.forEach(((mat) -> {mat.postRender(rcon);}));
		
	}
	
	@Override
	public boolean isImmutable()
	{
		return this.finished;
	}
	
	@Override
	public synchronized Materials setImmutable()
	{
		this.finished = true;
		
		return this;
	}
	
	public boolean addMaterials(Material... ms)
	{
		assert ms != null && ms.length > 0;
		
		if (this.finished)
		{
			return false;
		}
		
		boolean ret = false;
		
		for (Material mat : ms)
		{
			if (this.size() == RenderConst.MATERIAL_CAP)
			{
				break;
			}
			
			this.mats.add(mat);
			
		}
		
		if (ret)
		{
			if (this.isStatic())
			{
				for (Material m : this.mats)
				{
					if (m.isStatic())
					{
						continue;
					}
					
					synchronized (this)
					{
						this.isStatic = false;
						break;
					}
					
				}
				
			}
			
			synchronized (this)
			{
				this.dirty = true;
				
			}
			
		}
		
		return ret;
	}
	
	public boolean isStatic()
	{
		return this.isStatic;
	}
	
}
