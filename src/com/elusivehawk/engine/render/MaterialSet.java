
package com.elusivehawk.engine.render;

import java.util.function.Consumer;
import com.elusivehawk.util.IDirty;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class MaterialSet implements IRenderable, IDirty
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
	public void render(RenderContext rcon) throws RenderException
	{
		this.forEveryMaterial(((mat) -> {mat.render(rcon);}));
		
	}
	
	@Override
	public void preRender(RenderContext rcon, double delta)
	{
		this.forEveryMaterial(((mat) -> {mat.preRender(rcon, delta);}));
		
	}
	
	@Override
	public void postRender(RenderContext rcon)
	{
		this.forEveryMaterial(((mat) -> {mat.postRender(rcon);}));
		
	}
	
	public Material getMat(int i)
	{
		return this.mats[i];
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
	
	public void forEveryMaterial(Consumer<Material> consumer)
	{
		for (int c = 0; c < this.matCount; c++)
		{
			consumer.accept(this.getMat(c));
			
		}
		
	}
	
	public void finish()
	{
		this.finished = true;
		
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
