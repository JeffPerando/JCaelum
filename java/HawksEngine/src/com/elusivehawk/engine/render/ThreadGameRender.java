
package com.elusivehawk.engine.render;

import java.util.Collection;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
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
	protected double delta;
	
	public ThreadGameRender(IRenderHUB renderHub)
	{
		hub = renderHub;
		
	}
	
	@Override
	public boolean initiate()
	{
		DisplaySettings settings = this.hub.getSettings();
		
		this.setTargetFPS(settings.targetFPS);
		
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
		
		return true;
	}
	
	@Override
	public void timedUpdate(double delta)
	{
		if (this.hub.updateDisplay())
		{
			try
			{
				DisplaySettings settings = this.hub.getSettings();
				
				Display.setDisplayMode(settings.mode);
				Display.setFullscreen(settings.fullscreen);
				Display.setVSyncEnabled(settings.vsync);
				
				this.setTargetFPS(settings.targetFPS);
				
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
			//TODO Unbind all textures.
			
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
	public void onThreadStopped()
	{
		GL.cleanup();
		
		Display.destroy();
		
	}
	
	@Override
	public double getDelta()
	{
		return this.delta;
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
	
	protected void setTargetFPS(int framerate)
	{
		this.fps = framerate;
		this.delta = (1000000000.0 / this.fps);
		
	}
	
}
