
package com.elusivehawk.engine.render;

import java.util.List;
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
		
		delta = (1000000000.0f / fps);
		
	}
	
	@Override
	public void update()
	{
		RenderHelper.makeContextCurrent();
		
		this.hub.getCamera().updateCamera();
		
		//TODO Update program uniforms.
		
		List<IRenderEngine> engines = this.hub.getRenderEngines();
		
		if (engines == null || engines.size() == 0)
		{
			return;
		}
		
		GL.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
		
		for (IRenderEngine engine : engines)
		{
			GL.glActiveTexture(0);
			
			engine.render(this.hub);
			
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
