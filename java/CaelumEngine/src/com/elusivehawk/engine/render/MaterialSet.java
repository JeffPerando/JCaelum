
package com.elusivehawk.engine.render;

import com.elusivehawk.util.IDirty;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class MaterialSet implements IDirty
{
	private final Material[] mats = new Material[RenderConst.MATERIAL_CAP];
	private volatile int matCount = 0;
	private volatile boolean dirty = false, finished = false;
	
	@Override
	public boolean isDirty()
	{
		return this.dirty;
	}
	
	@Override
	public void setIsDirty(boolean b)
	{
		this.dirty = b;
		
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
			this.dirty = true;
			
		}
		
		return ret;
	}
	
	public void finish()
	{
		this.finished = true;
		
	}
	
	public int matCount()
	{
		return this.matCount;
	}
	
}
