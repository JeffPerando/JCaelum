
package com.elusivehawk.engine.render.old;

import com.elusivehawk.engine.render.RenderContext;

/**
 * 
 * Renders particles.
 * 
 * @author Elusivehawk
 */
@Deprecated
public class RenderEngineParticles implements IRenderEngine
{
	@Override
	public void render(RenderContext con, IRenderHUB hub)
	{
		/*if (hub.getScene() == null || !hub.getRenderMode().is3D())
		{
			return;
		}
		
		ParticleScene particles = hub.getScene().getParticles();
		
		if (particles == null || !particles.updateBeforeUse())
		{
			return;
		}
		
		GLProgram p = particles.getProgram();
		
		if (!p.bind())
		{
			return;
		}
		
		IGL1 gl1 = con.getGL1();
		
		gl1.glEnable(GLConst.GL_DEPTH_TEST);
		gl1.glDepthFunc(GLConst.GL_LESS);
		
		gl1.glEnable(GLConst.GL_CULL_FACE);
		gl1.glCullFace(GLConst.GL_BACK);
		
		gl1.glDrawArrays(GLConst.GL_POINTS, 0, particles.getParticleCount());
		
		gl1.glDisable(GLConst.GL_CULL_FACE);
		gl1.glDisable(GLConst.GL_DEPTH_TEST);
		
		p.unbind();
		*/
	}
	
	@Override
	public int getPriority(IRenderHUB hub)
	{
		return 0;
	}
	
}
