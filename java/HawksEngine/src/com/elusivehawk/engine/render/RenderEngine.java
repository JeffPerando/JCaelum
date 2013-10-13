
package com.elusivehawk.engine.render;

import java.util.List;
import java.util.Map.Entry;
import com.elusivehawk.engine.core.EnumRenderMode;
import com.elusivehawk.engine.util.GameLog;
import com.elusivehawk.engine.util.Tuple;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
@Deprecated
public final class RenderEngine
{
	private RenderEngine(){}//No constructor for you! Come back one year!
	
	public static boolean render(IScene scene, EnumRenderMode mode)
	{
		if (scene == null || mode == null)
		{
			return false;
		}
		
		GL.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
		
		if (mode.is3D())
		{
			do3DRenderPass(scene);
			RenderHelper.checkForGLError();
			
		}
		
		if (mode.is2D())
		{
			do2DRenderPass(scene);
			RenderHelper.checkForGLError();
			
		}
		
		return true;
	}
	
	public static void do3DRenderPass(IScene scene)
	{
		GL.glEnable(GL.GL_DEPTH_TEST);
		GL.glDepthFunc(GL.GL_LESS);
		
		GL.glEnable(GL.GL_CULL_FACE);
		GL.glCullFace(GL.GL_BACK);
		
		//Model rendering
		
		List<IModelGroup> models = scene.getModels();
		
		if (models != null && models.size() > 0)
		{
			int currTex, tex;
			
			for (IModelGroup group : models)
			{
				List<RenderTicket> tickets = group.getTickets();
				
				for (int c = 0; c < tickets.size(); c++)
				{
					RenderTicket ticket = tickets.get(c);
					
					ticket.updateBeforeUse();
					
					Model m = ticket.getModel();
					GLProgram p = ticket.getProgram();
					
					if (!p.bind())
					{
						continue;
					}
					
					currTex = GL.glGetInteger(GL.GL_ACTIVE_TEXTURE);
					tex = group.getTexture(c).getTexture();
					
					if (currTex != tex)
					{
						if (GL.glIsTexture(tex))
						{
							GL.glActiveTexture(tex);
							
						}
						else
						{
							GameLog.warn("Model group " + group.getName() + " model #" + c + " has invalid texture ID: " + tex + ", please rectify this.");
							
							GL.glActiveTexture(0);
							
						}
						
					}
					
					for (Entry<Integer, Tuple<Integer, Integer>> entry : m.getOffsets().entrySet())
					{
						GL.glDrawElements(entry.getKey(), entry.getValue().one, GL.GL_UNSIGNED_INT, entry.getValue().two);
						
					}
					
					RenderHelper.checkForGLError();
					
					p.unbind();
					
				}
				
			}
			
		}
		
		//Particle rendering
		
		ParticleScene particles = scene.getParticles();
		
		if (particles != null && particles.updateBeforeRendering())
		{
			GLProgram p = particles.p;
			
			if (p.bind())
			{
				GL.glDrawArrays(GL.GL_POINT, 0, particles.getParticleCount());
				
				RenderHelper.checkForGLError();
				
				p.unbind();
				
			}
			
		}
		
		GL.glDisable(GL.GL_CULL_FACE);
		GL.glDisable(GL.GL_DEPTH_TEST);
		
	}
	
	public static void do2DRenderPass(IScene scene)
	{
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
			
		}
		
	}
	
}
