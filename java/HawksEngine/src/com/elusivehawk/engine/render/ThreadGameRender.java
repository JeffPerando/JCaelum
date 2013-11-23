
package com.elusivehawk.engine.render;

import java.util.Collection;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import com.elusivehawk.engine.core.CaelumEngine;
import com.elusivehawk.engine.core.GameLog;
import com.elusivehawk.engine.core.ThreadTimed;

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
	
	public ThreadGameRender(IRenderHUB renderHub)
	{
		hub = renderHub;
		
		System.setProperty("org.lwjgl.opengl.Display.noinput", "true");
		
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
		
		if (!Display.isCreated())
		{
			try
			{
				Display.setTitle(settings.title);
				Display.setResizable(settings.resize);
				Display.setVSyncEnabled(settings.vsync);
				Display.setFullscreen(settings.fullscreen);
				if (settings.icons != null) Display.setIcon(settings.icons);
				if (settings.mode != null) Display.setDisplayMode(settings.mode);
				
				Color bg = settings.bg;
				Display.setInitialBackground(bg.getColorFloat(EnumColorFilter.RED), bg.getColorFloat(EnumColorFilter.GREEN), bg.getColorFloat(EnumColorFilter.BLUE));
				
				Display.create();
				
				//TODO Display.setDisplayConfiguration(settings.gamma, settings.brightness, settings.constrast);
				
				GL.glViewport(0, 0, settings.mode.getWidth(), settings.mode.getHeight());
				GL.glClearColor(bg.getColorFloat(EnumColorFilter.RED), bg.getColorFloat(EnumColorFilter.GREEN), bg.getColorFloat(EnumColorFilter.BLUE), bg.getColorFloat(EnumColorFilter.ALPHA));
				
			}
			catch (LWJGLException e)
			{
				GameLog.error(e);
				
				return false;
			}
			
		}
		
		try
		{
			CaelumEngine.instance().startupHook.wait();
			
		}
		catch (Exception e){}
		
		return true;
	}
	
	@Override
	public void update(double delta, boolean paused)
	{
		this.hub.update(delta);
		
		if (this.hub.updateDisplay())
		{
			try
			{
				DisplaySettings settings = this.hub.getSettings();
				
				Display.setDisplayMode(settings.mode);
				Display.setFullscreen(settings.fullscreen);
				Display.setVSyncEnabled(settings.vsync);
				
				this.fps = settings.targetFPS;
				
				//TODO Display.setDisplayConfiguration(settings.gamma, settings.brightness, settings.constrast);
				
			}
			catch (LWJGLException e)
			{
				GameLog.error(e);
				
			}
			
		}
		
		this.hub.getCamera().updateCamera(this.hub);
		
		Collection<IRenderEngine> engines = this.hub.getRenderEngines();
		
		if (engines == null || engines.isEmpty())
		{
			return;
		}
		
		GL.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
		
		for (IRenderEngine engine : engines)
		{
			engine.render(this.hub);
			
			int tex = 0, texUnits = GL.glGetInteger(GL.GL_MAX_TEXTURE_UNITS);
			
			for (int c = 0; c < texUnits; c++)
			{
				tex = GL.glGetInteger(GL.GL_TEXTURE0 + c);
				
				if (tex != 0)
				{
					GL.glBindTexture(GL.GL_TEXTURE0 + c, 0);
					
				}
				
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
		
		Display.sync(this.fps);
		Display.update(false);
		
	}
	
	@Override
	public void onThreadStopped()
	{
		GL.cleanup();
		
		Display.destroy();
		
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
		return !Display.isCloseRequested();
	}
	
}
