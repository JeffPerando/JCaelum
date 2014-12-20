
package com.elusivehawk.caelum.render.tex;

import java.util.Iterator;
import java.util.Set;
import com.elusivehawk.caelum.render.RenderConst;
import com.elusivehawk.util.IDirty;
import com.elusivehawk.util.IPopulator;
import com.google.common.collect.Sets;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class MaterialSet implements Iterable<Material>, IDirty
{
	private final Set<Material> mats = Sets.newConcurrentHashSet();
	
	private boolean isStatic = true, dirty = false;
	private int texCount = 0;
	
	public MaterialSet(){}
	
	public MaterialSet(IPopulator<MaterialSet> pop)
	{
		pop.populate(this);
		
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
	public Iterator<Material> iterator()
	{
		return this.mats.iterator();
	}
	
	public boolean add(Material mat)
	{
		if (this.size() < RenderConst.MATERIAL_CAP)
		{
			if (this.mats.add(mat))
			{
				synchronized (this)
				{
					this.isStatic = this.isStatic && mat.isStatic();
					this.texCount += mat.texCount();
					this.dirty = true;
					
				}
				
				return true;
			}
			
		}
		
		return false;
	}
	
	public boolean isStatic()
	{
		return this.isStatic;
	}
	
	public int size()
	{
		return this.mats.size();
	}
	
	public int texCount()
	{
		return this.texCount;
	}
	
}
