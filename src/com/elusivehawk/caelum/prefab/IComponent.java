
package com.elusivehawk.caelum.prefab;

import com.elusivehawk.caelum.IDisposable;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public interface IComponent extends IDisposable
{
	void initiate(Entity parent);
	
}
