
package com.elusivehawk.engine.render;

import com.elusivehawk.engine.render.opengl.GLConst;
import com.elusivehawk.engine.render.opengl.GLProgram;
import com.elusivehawk.engine.render2.IRenderEngine;
import com.elusivehawk.engine.render2.IRenderHUB;

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
	public void render(IRenderHUB hub)
	{
		if (hub.getScene() == null || !hub.getRenderMode().is3D())
		{
			return;
		}
		
		ParticleScene particles = hub.getScene().getParticles();
		
		if (particles == null || !particles.updateBeforeUse(hub))
		{
			return;
		}
		
		GLProgram p = particles.getProgram();
		
		if (!p.bind())
		{
			return;
		}
		
		GL.glEnable(GLConst.GL_DEPTH_TEST);
		GL.glDepthFunc(GLConst.GL_LESS);
		
		GL.glEnable(GLConst.GL_CULL_FACE);
		GL.glCullFace(GLConst.GL_BACK);
		
		GL.glDrawArrays(GLConst.GL_POINTS, 0, particles.getParticleCount());
		
		GL.glDisable(GLConst.GL_CULL_FACE);
		GL.glDisable(GLConst.GL_DEPTH_TEST);
		
		p.unbind();
		
	}
	
	@Override
	public int getPriority(IRenderHUB hub)
	{
		return 0;
	}
	
}
