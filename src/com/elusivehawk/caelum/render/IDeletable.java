
package com.elusivehawk.caelum.render;

import com.elusivehawk.caelum.IDisposable;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public interface IDeletable extends IDisposable
{
	void delete(RenderContext rcon);
	
}
