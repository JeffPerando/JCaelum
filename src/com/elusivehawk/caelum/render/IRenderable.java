
package com.elusivehawk.caelum.render;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public interface IRenderable extends IPreRenderer, IPostRenderer
{
	boolean render(RenderContext rcon) throws RenderException;
	
	default boolean render(RenderContext rcon, ICamera cam) throws RenderException
	{
		ICamera cam_tmp = rcon.getCamera();
		
		rcon.setCamera(cam);
		
		boolean ret = false;
		
		try
		{
			ret = this.render(rcon);
			
		}
		catch (RenderException e)
		{
			throw e;
		}
		finally
		{
			rcon.setCamera(cam_tmp);
			
		}
		
		return ret;
	}
	
}
