
package com.elusivehawk.engine.render;

import java.util.UUID;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public interface IFilterable
{
	default UUID addFilter(IFilter f)
	{
		UUID ret = UUID.randomUUID();
		
		this.addFilter(ret, f);
		
		return ret;
	}
	
	public void addFilter(UUID id, IFilter f);
	
	public boolean removeFilter(UUID id);
	
}
