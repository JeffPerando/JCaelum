
package com.elusivehawk.engine.render;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import com.elusivehawk.engine.render.opengl.GLProgram;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public abstract class Filterable implements IFilter
{
	private final Map<UUID, List<IFilter>> filters = Maps.newHashMap();
	
	@Override
	public void filter(RenderContext rcon, GLProgram p) throws RenderException
	{
		for (List<IFilter> fs : this.filters.values())
		{
			for (IFilter f : fs)
			{
				f.filter(rcon, p);
				
			}
			
		}
		
	}
	
	public int addFilter(UUID type, IFilter f)
	{
		List<IFilter> list = this.filters.get(type);
		
		if (list == null)
		{
			list = Lists.newArrayList();
			this.filters.put(type, list);
			
		}
		
		list.add(f);
		
		return list.size() - 1;
	}
	
	public void removeFilter(UUID type, int i)
	{
		List<IFilter> list = this.filters.get(type);
		
		if (list == null)
		{
			list = Lists.newArrayList();
			this.filters.put(type, list);
			
		}
		
		list.remove(i);
		
	}
	
}
