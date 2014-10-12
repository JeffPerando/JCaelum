
package com.elusivehawk.engine.render;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
@FunctionalInterface
public interface IPreRenderer
{
	void preRender(RenderContext rcon, double delta);
	
}
