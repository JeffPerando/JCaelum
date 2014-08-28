
package com.elusivehawk.engine.render.old;

/**
 * 
 * Renders entities in the game world.
 * 
 * @author Elusivehawk
 */
@Deprecated
public class RenderEngine3D// implements IRenderEngine
{
	/*@Override
	public void render(RenderContext rcon, IRenderHUB hub, double delta)
	{
		IScene scene = hub.getScene();
		
		if (scene == null)
		{
			return;
		}
		
		Collection<IModelGroup> models = scene.getModels();
		
		if (models == null || models.isEmpty())
		{
			return;
		}
		
		IGL1 gl1 = rcon.getGL1();
		
		gl1.glEnable(GLConst.GL_DEPTH_TEST);
		gl1.glDepthFunc(GLConst.GL_LESS);
		
		gl1.glEnable(GLConst.GL_CULL_FACE);
		gl1.glCullFace(GLConst.GL_BACK);
		
		//int currTex = 0, tex = 0;
		boolean zBuffer = true;
		
		RenderTicket tkt;
		Model m;
		GLProgram p;
		
		for (IModelGroup group : models)
		{
			List<RenderTicket> tickets = group.getTickets();
			
			if (tickets == null || tickets.isEmpty())
			{
				continue;
			}
			
			for (int c = 0; c < tickets.size(); c++)
			{
				tkt = tickets.get(c);
				m = tkt.getModel();
				p = tkt.getProgram();
				
				if (m == null)
				{
					continue;
				}
				
				if (!tkt.updateBeforeRender(rcon, delta))
				{
					continue;
				}
				
				if (!p.bind(rcon))
				{
					continue;
				}
				
				try
				{
					RenderHelper.checkForGLError(gl1);
					
				}
				catch (Exception e)
				{
					p.unbind(rcon);
					
					throw e;
				}
				
				tex = tkt.getTexture() == null ? con.getDefaultTexture() : tkt.getTexture().getTexId(tkt.getCurrentTexFrame());
				
				if (currTex != tex)
				{
					if (!gl1.glIsTexture(tex))
					{
						tex = con.getDefaultTexture();
						
					}
					
					gl1.glBindTexture(GLConst.GL_TEXTURE0, tex);
					currTex = tex;
					
				}
				
				if (zBuffer != tkt.enableZBuffering())
				{
					zBuffer = tkt.enableZBuffering();
					
					if (zBuffer)
					{
						gl1.glEnable(GLConst.GL_DEPTH_TEST);
						gl1.glDepthFunc(GLConst.GL_LESS);
						
					}
					else
					{
						gl1.glDisable(GLConst.GL_DEPTH_TEST);
						
					}
					
				}
				
				gl1.glDrawElements(m.getPolygonType(), 0, GLConst.GL_UNSIGNED_INT, m.getPolyCount());
				
				p.unbind(rcon);
				
			}
			
		}
		
		gl1.glDisable(GLConst.GL_CULL_FACE);
		
		if (zBuffer)
		{
			gl1.glDisable(GLConst.GL_DEPTH_TEST);
			
		}
		
	}*/
	
}
