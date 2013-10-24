
package com.elusivehawk.engine.render;

import java.util.Collection;
import java.util.Map.Entry;
import com.elusivehawk.engine.core.Tuple;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class RenderEngine3D implements IRenderEngine
{
	@Override
	public void render(IRenderHUB hub)
	{
		IScene scene = hub.getScene();
		
		if (scene == null || !hub.getRenderMode().is3D())
		{
			return;
		}
		
		Collection<IModelGroup> models = scene.getModels();
		
		if (models == null || models.isEmpty())
		{
			return;
		}
		
		GL.glEnable(GL.GL_DEPTH_TEST);
		GL.glDepthFunc(GL.GL_LESS);
		
		GL.glEnable(GL.GL_CULL_FACE);
		GL.glCullFace(GL.GL_BACK);
		
		int currTex = 0, tex = 0;
		
		for (IModelGroup group : models)
		{
			Collection<RenderTicket> tickets = group.getTickets();
			
			if (tickets == null || tickets.isEmpty())
			{
				continue;
			}
			
			for (RenderTicket tkt : tickets)
			{
				tkt.updateBeforeUse(hub);
				
				Model m = tkt.getModel();
				GLProgram p = tkt.getProgram();
				
				if (!p.bind())
				{
					continue;
				}
				
				tex = tkt.getTexture().getTexture();
				
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
				
				for (Entry<Integer, Tuple<Integer, Integer>> entry : m.getOffsets().entrySet())
				{
					GL.glDrawElements(entry.getKey(), entry.getValue().one, GL.GL_UNSIGNED_INT, entry.getValue().two);
					
				}
				
				RenderHelper.checkForGLError();
				
				p.unbind();
				
			}
			
		}
		
		GL.glDisable(GL.GL_CULL_FACE);
		GL.glDisable(GL.GL_DEPTH_TEST);
		
	}
	
}
