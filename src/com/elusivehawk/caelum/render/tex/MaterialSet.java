
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
	
	public int add(Material mat)
	{
		if (this.size() == RenderConst.MATERIAL_CAP)
		{
			return -1;
		}
		
		if (!this.mats.add(mat))
		{
			return -1;
		}
		
		synchronized (this)
		{
			this.isStatic = this.isStatic && mat.isStatic();
			this.texCount += mat.texCount();
			this.dirty = true;
			
		}
		
		return this.size() - 1;
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
