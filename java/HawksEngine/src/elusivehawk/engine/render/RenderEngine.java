
package elusivehawk.engine.render;

import java.util.List;
import java.util.Map.Entry;
import elusivehawk.engine.core.EnumRenderMode;
import elusivehawk.engine.util.GameLog;
import elusivehawk.engine.util.Tuple;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
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
			int currTex;
			
			for (IModelGroup group : models)
			{
				for (int c = 0; c < group.getModels().size(); c++)
				{
					if (!group.updateBeforeRendering(c))
					{
						continue;
					}
					
					Model m = group.getModels().get(c);
					GLProgram p = group.getProgram(c);
					
					if (!p.bind())
					{
						continue;
					}
					
					currTex = GL.glGetInteger(GL.GL_ACTIVE_TEXTURE);
					
					int tex = group.getTexture(c).getTexture();
					
					if (currTex != tex)
					{
						boolean isTex = GL.glIsTexture(tex);
						
						if (isTex)
						{
							GL.glActiveTexture(tex);
							
						}
						else
						{
							GameLog.warn("Model group " + group.getName() + " model #" + c + " has invalid texture ID: " + tex + ", please rectify this.");
							
							GL.glActiveTexture(0);
							
						}
						
					}
					
					for (Entry<Integer, Tuple<Integer, Integer>> entry : m.getArrays().entrySet())
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
		ImageScreen imgs = scene.getImages();
		
		if (imgs == null)
		{
			return;
		}
		
		imgs.updateImages();
		
		if (!imgs.p.bind())
		{
			return;
		}
		
		
		
		imgs.p.unbind();
		
	}
	
}
