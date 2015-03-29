
package com.elusivehawk.caelum.render;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
@FunctionalInterface
public interface IPostRenderer
{
	void postRender(RenderContext rcon) throws RenderException;
	
}
