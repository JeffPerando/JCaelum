
package com.elusivehawk.caelum.render;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import com.elusivehawk.caelum.render.gl.GLException;
import com.elusivehawk.caelum.render.gl.GLProgram;
import com.elusivehawk.util.IDirty;
import com.elusivehawk.util.storage.SyncList;
import com.google.common.collect.Maps;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class Filters implements IFilterable, IFilter, IDirty
{
	private final Map<UUID, List<IFilter>> filters = Maps.newHashMap();
	private boolean dirty = true;
	
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
	public void filter(RenderContext rcon, GLProgram p) throws GLException
	{
		for (List<IFilter> fs : this.filters.values())
		{
			for (IFilter f : fs)
			{
				f.filter(rcon, p);
				
			}
			
		}
		
		this.setIsDirty(false);
		
	}
	
	@Override
	public int addFilter(UUID type, IFilter f)
	{
		List<IFilter> list = this.filters.get(type);
		
		if (list == null)
		{
			list = SyncList.newList();
			this.filters.put(type, list);
			
		}
		
		list.add(f);
		this.setIsDirty(true);
		
		return list.size() - 1;
	}
	
	@Override
	public void removeFilter(UUID type, IFilter f)
	{
		List<IFilter> list = this.filters.get(type);
		
		if (list == null)
		{
			list = SyncList.newList();
			this.filters.put(type, list);
			
		}
		else
		{
			list.remove(f);
			this.setIsDirty(true);
			
		}
		
	}
	
	@Override
	public void removeFilter(UUID type, int i)
	{
		List<IFilter> list = this.filters.get(type);
		
		if (list == null)
		{
			list = SyncList.newList();
			this.filters.put(type, list);
			
		}
		else
		{
			list.remove(i);
			this.setIsDirty(true);
			
		}
		
	}
	
}
