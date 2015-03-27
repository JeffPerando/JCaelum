
package com.elusivehawk.caelum.render;

import com.elusivehawk.caelum.IDisposable;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 * 
 * @see Deletables
 */
@FunctionalInterface
public interface IDeletable extends IDisposable
{
	@Override
	default void dispose()
	{
		Deletables.instance().scheduleDeletion(this);
		
	}
	
	void delete();
	
}
