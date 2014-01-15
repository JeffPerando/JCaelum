
package com.elusivehawk.engine.render;

import java.io.IOException;
import java.util.Collection;
import com.elusivehawk.engine.core.CaelumEngine;
import com.elusivehawk.engine.core.EnumLogType;
import com.elusivehawk.engine.render.opengl.GLConst;
import com.elusivehawk.engine.util.SemiFinalStorage;
import com.elusivehawk.engine.util.ThreadTimed;

/**
 * 
 * The primary thread for rendering the game's window.
 * 
 * @author Elusivehawk
 */
public class ThreadGameRender extends ThreadTimed
{
	protected final IRenderHUB hub;
	protected final IRenderEnvironment env;
	protected final SemiFinalStorage<IDisplay> display = new SemiFinalStorage<IDisplay>(null);
	protected int fps;
	
	@SuppressWarnings("unqualified-field-access")
	public ThreadGameRender(IRenderHUB rhub)
	{
		hub = rhub;
		env = CaelumEngine.instance().getEnvironment().getRenderEnv();
		
	}
	
	@Override
	public boolean initiate()
	{
		DisplaySettings settings = this.hub.getSettings();
		
		if (settings == null)
		{
			settings = new DisplaySettings();
			
		}
		
		this.fps = settings.targetFPS;
		
		if (!this.display.locked())
		{
			this.display.set(this.env.createDisplay("default", settings));
			
		}
		
		return true;
	}
	
	@Override
	public void update(double delta) throws Throwable
	{
		if (this.isPaused())
		{
			return;
		}
		
		this.hub.update(delta);
		
		if (this.hub.updateDisplay())
		{
			DisplaySettings settings = this.hub.getSettings();
			
			this.display.get().resize(settings.height, settings.width);
			this.display.get().setFullscreen(settings.fullscreen);
			this.display.get().setVSync(settings.vsync);
			this.display.get().setFPS(settings.targetFPS);
			
			this.fps = settings.targetFPS;
			
		}
		
		this.hub.getCamera().updateCamera(this.hub);
		
		Collection<IRenderEngine> engines = this.hub.getRenderEngines();
		
		if (engines == null || engines.isEmpty())
		{
			return;
		}
		
		GL.glClear(GLConst.GL_COLOR_BUFFER_BIT | GLConst.GL_DEPTH_BUFFER_BIT);
		
		int priorityCount = Math.max(this.hub.getHighestPriority(), 1);
		int renderersUsed = 0;
		int priority = 0;
		boolean flag = true;
		
		for (int p = 0; p < priorityCount && flag; p++)
		{
			for (IRenderEngine engine : engines)
			{
				if (renderersUsed == engines.size())
				{
					flag = false;
					break;
				}
				
				priority = Math.min(engine.getPriority(this.hub), priorityCount - 1);
				
				if (priority != p)
				{
					continue;
				}
				
				engine.render(this.hub);
				renderersUsed++;
				
				int tex = 0, texUnits = GL.glGetInteger(GLConst.GL_MAX_TEXTURE_UNITS);
				
				for (int c = 0; c < texUnits; c++)
				{
					tex = GL.glGetInteger(GLConst.GL_TEXTURE0 + c);
					
					if (tex != 0)
					{
						GL.glBindTexture(GLConst.GL_TEXTURE0 + c, 0);
						
					}
					
				}
				
				try
				{
					RenderHelper.checkForGLError();
					
				}
				catch (Exception e)
				{
					CaelumEngine.instance().getLog().log(EnumLogType.ERROR, null, e);
					
				}
				
			}
			
		}
		
		this.hub.getCamera().postRender(this.hub);
		
		this.display.get().updateDisplay();
		
	}
	
	@Override
	public void onThreadStopped()
	{
		RenderHelper.cleanup();
		
		try
		{
			this.display.get().close();
		}
		catch (IOException e){}
		
	}
	
	@Override
	public int getTargetUpdateCount()
	{
		return this.fps;
	}
	
	@Override
	public double getMaxDelta()
	{
		return 0.5;
	}
	
	@Override
	public boolean isRunning()
	{
		return super.isRunning() && !this.display.get().isCloseRequested();
	}
	
}
