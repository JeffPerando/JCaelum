
package com.elusivehawk.engine.render;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class RenderEngineParticles implements IRenderEngine
{
	@Override
	public boolean render(IRenderHUB hub)
	{
		if (hub.getScene() == null || !hub.getRenderMode().is3D())
		{
			return false;
		}
		
		ParticleScene particles = hub.getScene().getParticles();
		
		if (particles == null || !particles.updateBeforeRendering())
		{
			return false;
		}
		
		GLProgram p = particles.p;
		
		if (!p.bind())
		{
			return false;
		}
		
		GL.glEnable(GL.GL_DEPTH_TEST);
		GL.glDepthFunc(GL.GL_LESS);
		
		GL.glEnable(GL.GL_CULL_FACE);
		GL.glCullFace(GL.GL_BACK);
		
		GL.glDrawArrays(GL.GL_POINT, 0, particles.getParticleCount());
		
		p.unbind();
		
		GL.glDisable(GL.GL_CULL_FACE);
		GL.glDisable(GL.GL_DEPTH_TEST);
		
		return true;
	}
	
}
