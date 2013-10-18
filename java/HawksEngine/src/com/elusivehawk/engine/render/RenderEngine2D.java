
package com.elusivehawk.engine.render;

import java.util.List;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class RenderEngine2D implements IRenderEngine
{
	@Override
	public void render(IRenderHUB hub)
	{
		IScene scene = hub.getScene();
		
		if (scene == null || !hub.getRenderMode().is2D())
		{
			return;
		}
		
		List<ImageScreen> imgs = scene.getImages();
		
		if (imgs == null || imgs.isEmpty())
		{
			return;
		}
		
		for (ImageScreen imgScene : imgs)
		{
			imgScene.updateImages();
			
			GLProgram p = imgScene.getProgram();
			
			if (!p.bind())
			{
				continue;
			}
			
			GL.glActiveTexture(imgScene.getTexture());
			GL.glDrawElements(GL.GL_TRIANGLES, imgScene.getImgCount() * 6, GL.GL_UNSIGNED_INT, 0);
			GL.glActiveTexture(null);
			
			p.unbind();
			
			RenderHelper.checkForGLError();
			
		}
		
	}
	
}
