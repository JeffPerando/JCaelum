
package com.elusivehawk.engine.render;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public interface IFramebufferTexture extends ITexture, IPostRenderer
{
	void renderTexture(RenderContext rcon);
	
}
