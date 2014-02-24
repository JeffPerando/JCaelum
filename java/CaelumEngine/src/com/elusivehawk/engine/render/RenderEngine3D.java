
package com.elusivehawk.engine.render;

import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;
import com.elusivehawk.engine.render.old.Model;
import com.elusivehawk.engine.render.opengl.GLConst;
import com.elusivehawk.engine.render.opengl.GLProgram;
import com.elusivehawk.engine.render.opengl.ITexture;
import com.elusivehawk.engine.util.Tuple;

/**
 * 
 * Renders entities in the game world.
 * 
 * @author Elusivehawk
 */
public class RenderEngine3D implements IRenderEngine
{
	@Override
	public void render(RenderContext context)
	{
		if (!context.getHub().getRenderMode().is3D())
		{
			return;
		}
		
		IScene scene = context.getHub().getScene();
		
		if (scene == null)
		{
			return;
		}
		
		Collection<IModelGroup> models = scene.getModels();
		
		if (models == null || models.isEmpty())
		{
			return;
		}
		
		context.getGL1().glEnable(GLConst.GL_DEPTH_TEST);
		context.getGL1().glDepthFunc(GLConst.GL_LESS);
		
		context.getGL1().glEnable(GLConst.GL_CULL_FACE);
		context.getGL1().glCullFace(GLConst.GL_BACK);
		
		int currTex = 0, tex = 0;
		boolean zBuffer = true;
		
		for (IModelGroup group : models)
		{
			List<RenderTicket> tickets = group.getTickets();
			
			if (tickets == null || tickets.isEmpty())
			{
				continue;
			}
			
			for (int c = 0; c < tickets.size(); c++)
			{
				if (!group.doRenderTicket(c))
				{
					continue;
				}
				
				RenderTicket tkt = tickets.get(c);
				Model m = tkt.getModel();
				GLProgram p = tkt.getProgram();
				
				if (!p.bind(context))
				{
					continue;
				}
				
				if (!tkt.updateBeforeUse(context))
				{
					p.unbind(context);
					continue;
				}
				
				tex = tkt.getTexture() == null ? context.getDefaultTexture() : tkt.getTexture().getTexture();
				
				if (currTex != tex)
				{
					if (!context.getGL1().glIsTexture(tex))
					{
						tex = context.getDefaultTexture();
						
					}
					
					context.getGL1().glBindTexture(GLConst.GL_TEXTURE0, tex);
					currTex = tex;
					
				}
				
				if (zBuffer != tkt.enableZBuffering())
				{
					zBuffer = tkt.enableZBuffering();
					
					if (zBuffer)
					{
						context.getGL1().glEnable(GLConst.GL_DEPTH_TEST);
						context.getGL1().glDepthFunc(GLConst.GL_LESS);
						
					}
					else
					{
						context.getGL1().glDisable(GLConst.GL_DEPTH_TEST);
						
					}
					
				}
				
				for (Entry<Integer, Tuple<Integer, Integer>> entry : m.getOffsets().entrySet())
				{
					context.getGL1().glDrawElements(entry.getKey(), entry.getValue().one, GLConst.GL_UNSIGNED_INT, entry.getValue().two);
					
				}
				
				RenderHelper.checkForGLError(context);
				
				p.unbind(context);
				
			}
			
		}
		
		context.getGL1().glDisable(GLConst.GL_CULL_FACE);
		
		if (zBuffer)
		{
			context.getGL1().glDisable(GLConst.GL_DEPTH_TEST);
			
		}
		
	}
	
	@Override
	public int getPriority(IRenderHUB hub)
	{
		return 0;
	}
	
}
