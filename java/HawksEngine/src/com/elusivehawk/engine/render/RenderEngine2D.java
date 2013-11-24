
package com.elusivehawk.engine.render;

import java.util.Collection;

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
		
		Collection<ImageScreen> imgs = scene.getImages();
		
		if (imgs == null || imgs.isEmpty())
		{
			return;
		}
		
		int currTex = 0, tex = 0;
		
		for (ImageScreen imgScene : imgs)
		{
			imgScene.updateBeforeUse(hub);
			
			GLProgram p = imgScene.getProgram();
			
			if (!p.bind())
			{
				continue;
			}
			
			tex = imgScene.getTexture().getTexture();
			
			if (currTex != tex)
			{
				if (GL.glIsTexture(tex))
				{
					GL.glBindTexture(GL.GL_TEXTURE0, tex);
					currTex = tex;
					
				}
				else
				{
					GL.glBindTexture(GL.GL_TEXTURE0, 0);
					
				}
				
			}
			
			GL.glDrawElements(GL.GL_TRIANGLES, imgScene.getImgCount() * 6, GL.GL_UNSIGNED_INT, 0);
			
			GL.glBindTexture(GL.GL_TEXTURE0, 0);
			
			p.unbind();
			
			RenderHelper.checkForGLError();
			
		}
		
	}
	
	@Override
	public int getPriority()
	{
		return 1;
	}
	
}
