
package com.elusivehawk.caelum.render;

import java.util.UUID;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public interface IFilterable
{
	int addFilter(UUID type, IFilter f);
	
	void removeFilter(UUID type, IFilter f);
	
	void removeFilter(UUID type, int i);
	
}
