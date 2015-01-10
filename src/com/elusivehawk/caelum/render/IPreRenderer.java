
package com.elusivehawk.caelum.render;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
@FunctionalInterface
public interface IPreRenderer
{
	void preRender(RenderContext rcon) throws RenderException;
	
}
