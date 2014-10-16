
package com.elusivehawk.engine.render;

import com.elusivehawk.util.IDirty;
import com.elusivehawk.util.storage.IArray;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class MaterialSet implements IRenderable, IArray<Material>, IDirty
{
	private final Material[] mats = new Material[RenderConst.MATERIAL_CAP];
	private volatile int matCount = 0;
	private volatile boolean dirty = false, isStatic = true, finished = false;
	
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
		
		this.mats[i] = mat;
		this.matCount++;
		
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
	
	public MaterialSet setImmutable()
	{
		this.finished = true;
		
		return this;
	}
	
	public boolean addMaterials(Material... mats)
	{
		assert mats != null && mats.length > 0;
		
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
			
			this.mats[this.matCount++] = mats[i++];
			ret = true;
			
		}
		while (i < mats.length);
		
		if (ret)
		{
			for (Material m : mats)
			{
				if (!this.isStatic())
				{
					break;
				}
				
				this.isStatic = m.isStatic();
				
			}
			
			this.dirty = true;
			
		}
		
		return ret;
	}
	
	public MaterialSet finish()
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
