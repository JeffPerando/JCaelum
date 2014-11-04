
package com.elusivehawk.caelum.render;

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
	private final Material[] mats = new Material[RenderConst.MATERIAL_CAP];
	private int matCount = 0;
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
		return this.matCount;
	}
	
	@Override
	public Material get(int i)
	{
		return this.mats[i];
	}
	
	@Override
	public IArray<? extends Material> set(int i, Material mat)
	{
		if (this.matCount == RenderConst.MATERIAL_CAP)
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
		
		if (this.mats[i] != null)
		{
			return this;
		}
		
		synchronized (this)
		{
			this.mats[i] = mat;
			this.matCount++;
			
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
		int i = 0;
		
		do
		{
			if (this.matCount == this.mats.length)
			{
				break;
			}
			
			synchronized (this)
			{
				this.mats[this.matCount++] = ms[i++];
				
			}
			
			ret = true;
			
		}
		while (i < ms.length);
		
		if (ret)
		{
			if (this.isStatic())
			{
				for (Material m : this.mats)
				{
					synchronized (this)
					{
						this.isStatic = m.isStatic();
						
					}
					
					if (!this.isStatic)
					{
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
	
	public synchronized Materials finish()
	{
		this.finished = true;
		
		return this;
	}
	
	public int matCount()
	{
		return this.matCount;
	}
	
	public boolean isStatic()
	{
		return this.isStatic;
	}
	
}
