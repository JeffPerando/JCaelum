
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
	public int addFilter(UUID type, IFilter f);
	
	public void removeFilter(UUID type, IFilter f);
	
	public void removeFilter(UUID type, int i);
	
}
