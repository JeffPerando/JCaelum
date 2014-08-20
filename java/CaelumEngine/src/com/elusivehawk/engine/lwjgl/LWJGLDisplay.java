
package com.elusivehawk.engine.lwjgl;

import java.io.IOException;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import com.elusivehawk.engine.CaelumEngine;
import com.elusivehawk.engine.render.Color;
import com.elusivehawk.engine.render.DisplaySettings;
import com.elusivehawk.engine.render.ColorFilter;
import com.elusivehawk.engine.render.IDisplay;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class LWJGLDisplay implements IDisplay
{
	private int framerate = 30;
	
	@Override
	public void close() throws IOException
	{
		Display.destroy();
		
	}
	
	@Override
	public String getTitle()
	{
		return Display.getTitle();
	}
	
	@Override
	public void createDisplay() throws Exception
	{
		Display.create();
		
	}
	
	@Override
	public boolean getFullscreen()
	{
		return Display.isFullscreen();
	}
	
	@Override
	public boolean isCloseRequested()
	{
		return Display.isCloseRequested();
	}
	
	@Override
	public int getHeight()
	{
		return Display.getHeight();
	}
	
	@Override
	public int getWidth()
	{
		return Display.getWidth();
	}
	
	@Override
	public void updateDisplay()
	{
		Display.sync(this.framerate);
		Display.update(false);
		
	}
	
	@Override
	public void processMessages()
	{
		Display.processMessages();
		
	}
	
	@Override
	public void updateSettings(DisplaySettings settings)
	{
		try
		{
			Display.setTitle(settings.title);
			Display.setDisplayMode(new DisplayMode(settings.width, settings.height));
			Display.setFullscreen(settings.vsync);
			Display.setIcon(settings.icons);
			
			Color bg = settings.bg;
			Display.setInitialBackground(bg.getColorf(ColorFilter.RED), bg.getColorf(ColorFilter.GREEN), bg.getColorf(ColorFilter.BLUE));
			
			Display.setTitle(settings.title);
			Display.setVSyncEnabled(settings.vsync);
			
			this.framerate = settings.targetFPS;
			
		}
		catch (LWJGLException e)
		{
			CaelumEngine.log().err(e);
			
		}
		
	}
	
	@Override
	public boolean makeCurrent()
	{
		try
		{
			Display.makeCurrent();
			
		}
		catch (LWJGLException e)
		{
			CaelumEngine.log().err(e);
			
			return false;
		}
		
		return true;
	}
	
	@Override
	public boolean releaseContext()
	{
		try
		{
			Display.releaseContext();
		}
		catch (LWJGLException e)
		{
			CaelumEngine.log().err(e);
			
			return false;
		}
		
		return true;
	}
	
}
