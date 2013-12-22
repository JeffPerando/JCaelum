
package com.elusivehawk.engine.render;

/**
 * 
 * The new 2D rendering system for library-specific image rendering.
 * 
 * @author Elusivehawk
 */
public interface IImageContext extends ILogicalRender
{
	public Object createImage(String path, int x, int y, int w, int h);
	
}
