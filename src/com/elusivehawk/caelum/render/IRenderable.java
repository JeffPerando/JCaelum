
package com.elusivehawk.caelum.render;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public interface IRenderable extends IPreRenderer, IPostRenderer
{
	void render(RenderContext rcon) throws RenderException;
	
	default void render(RenderContext rcon, ICamera cam) throws RenderException
	{
		ICamera cam_tmp = rcon.getCamera();
		
		rcon.setCamera(cam);
		
		try
		{
			this.render(rcon);
			
		}
		catch (RenderException e)
		{
			throw e;
		}
		finally
		{
			rcon.setCamera(cam_tmp);
			
		}
		
	}
	
}
