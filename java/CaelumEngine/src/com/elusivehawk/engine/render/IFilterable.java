
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
	public void addFilter(UUID id, IFilter f);
	
	public boolean removeFilter(UUID id);
	
}
