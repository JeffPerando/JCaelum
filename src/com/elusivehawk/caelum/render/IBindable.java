
package com.elusivehawk.caelum.render;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public interface IBindable
{
	boolean bind(RenderContext rcon);
	
	void unbind(RenderContext rcon);
	
	boolean isBound(RenderContext rcon);
	
}
