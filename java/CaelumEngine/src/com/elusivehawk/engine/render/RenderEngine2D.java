
package com.elusivehawk.engine.render;

import java.util.Collection;
import com.elusivehawk.engine.render.opengl.GLConst;
import com.elusivehawk.engine.render.opengl.GLProgram;
import com.elusivehawk.engine.render2.IRenderEngine;
import com.elusivehawk.engine.render2.IRenderHUB;
import com.elusivehawk.engine.render2.IScene;
import com.elusivehawk.engine.render2.RenderHelper;

/**
 * 
 * Renders 2D images onto the screen.
 * 
 * @author Elusivehawk
 */
@Deprecated
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
			
			tex = imgScene.getTexture().getTexture(true);
			
			if (currTex != tex)
			{
				if (GL.glIsTexture(tex))
				{
					GL.glBindTexture(GLConst.GL_TEXTURE0, tex);
					currTex = tex;
					
				}
				else
				{
					GL.glBindTexture(GLConst.GL_TEXTURE0, 0);
					
				}
				
			}
			
			GL.glDrawElements(GLConst.GL_TRIANGLES, imgScene.getImgCount() * 6, GLConst.GL_UNSIGNED_INT, 0);
			
			GL.glBindTexture(GLConst.GL_TEXTURE0, 0);
			
			p.unbind();
			
			RenderHelper.checkForGLError();
			
		}
		
	}
	
	@Override
	public int getPriority(IRenderHUB hub)
	{
		return hub.getRenderMode().is3D() ? 1 : 0;
	}
	
}
