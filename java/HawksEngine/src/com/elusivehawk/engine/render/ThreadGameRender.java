
package com.elusivehawk.engine.render;

import java.util.List;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import com.elusivehawk.engine.util.GameLog;
import com.elusivehawk.engine.util.ThreadTimed;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class ThreadGameRender extends ThreadTimed
{
	protected final IRenderHUB hub;
	protected int fps;
	protected float delta;
	
	public ThreadGameRender(IRenderHUB renderHub, int framerate)
	{
		hub = renderHub;
		fps = framerate;
		
		delta = (100000000.0f / fps);
		
	}
	
	@Override
	public void update()
	{
		try
		{
			if (!Display.isCurrent())
			{
				Display.makeCurrent();
				
			}
			
		}
		catch (LWJGLException e)
		{
			GameLog.error(e);
			
		}
		
		this.hub.getCamera().updateCamera();
		
		//TODO Update program uniforms.
		
		List<IRenderEngine> engines = this.hub.getRenderEngines();
		
		if (engines == null || engines.size() == 0)
		{
			return;
		}
		
		GL.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
		
		for (int c = 0; c < engines.size(); c++)
		{
			GL.glActiveTexture(0);
			
			if (!engines.get(c).render(this.hub))
			{
				GameLog.warn("Rendering engine #" + (c + 1) + " failed to render.");
				
			}
			
			try
			{
				RenderHelper.checkForGLError();
				
			}
			catch (Exception e)
			{
				GameLog.error(e);
				
			}
			
		}
		
	}
	
	@Override
	public int getDelta() //TODO Convert to float
	{
		return 0;
	}
	
	@Override
	public int getTargetUpdateCount()
	{
		return this.fps;
	}
	
	public synchronized void setTargetFPS(int framerate)
	{
		this.fps = framerate;
		
	}
	
}
