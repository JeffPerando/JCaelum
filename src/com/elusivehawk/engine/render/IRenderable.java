
package com.elusivehawk.engine.render;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public interface IRenderable extends IPreRenderer, IPostRenderer
{
	/**
	 * 
	 * @param rcon
	 * 
	 * @throws RenderException
	 * 
	 * @see RenderHelper
	 */
	void render(RenderContext rcon) throws RenderException;
	
	default void render(RenderContext rcon, ICamera cam)
	{
		ICamera cam_tmp = rcon.getCamera();
		
		rcon.setCamera(cam);
		
		this.render(rcon);
		
		rcon.setCamera(cam_tmp);
		
	}
	
}
