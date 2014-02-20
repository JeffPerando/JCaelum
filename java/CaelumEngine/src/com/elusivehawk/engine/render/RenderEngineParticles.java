
package com.elusivehawk.engine.render;

import com.elusivehawk.engine.render.opengl.GLConst;
import com.elusivehawk.engine.render.opengl.GLProgram;
import com.elusivehawk.engine.render2.IRenderEngine;
import com.elusivehawk.engine.render2.IRenderHUB;
import com.elusivehawk.engine.render2.RenderContext;

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
	public void render(RenderContext context)
	{
		if (context.getHub().getScene() == null || !context.getHub().getRenderMode().is3D())
		{
			return;
		}
		
		ParticleScene particles = context.getHub().getScene().getParticles();
		
		if (particles == null || !particles.updateBeforeUse(context))
		{
			return;
		}
		
		GLProgram p = particles.getProgram();
		
		if (!p.bind(context))
		{
			return;
		}
		
		context.getGL1().glEnable(GLConst.GL_DEPTH_TEST);
		context.getGL1().glDepthFunc(GLConst.GL_LESS);
		
		context.getGL1().glEnable(GLConst.GL_CULL_FACE);
		context.getGL1().glCullFace(GLConst.GL_BACK);
		
		context.getGL1().glDrawArrays(GLConst.GL_POINTS, 0, particles.getParticleCount());
		
		context.getGL1().glDisable(GLConst.GL_CULL_FACE);
		context.getGL1().glDisable(GLConst.GL_DEPTH_TEST);
		
		p.unbind(context);
		
	}
	
	@Override
	public int getPriority(IRenderHUB hub)
	{
		return 0;
	}
	
}
