
package com.elusivehawk.engine.lwjgl;

import java.io.IOException;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import com.elusivehawk.engine.render.Color;
import com.elusivehawk.engine.render.ColorFilter;
import com.elusivehawk.engine.render.DisplaySettings;
import com.elusivehawk.engine.render.IDisplay;
import com.elusivehawk.util.Logger;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class LWJGLDisplay implements IDisplay
{
	private String title = "";
	private int width = 800, height = 600;
	
	@Override
	public void close() throws IOException
	{
		Display.destroy();
		
	}
	
	@Override
	public String getTitle()
	{
		return this.title;
	}
	
	@Override
	public boolean isCreated()
	{
		return Display.isCreated();
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
		return this.height;
	}
	
	@Override
	public int getWidth()
	{
		return this.width;
	}
	
	@Override
	public void updateDisplay()
	{
		Display.update();
		
	}
	
	@Override
	public synchronized void updateSettings(DisplaySettings settings)
	{
		try
		{
			Display.setTitle(settings.title);
			Display.setDisplayMode(new DisplayMode(settings.width, settings.height));
			Display.setFullscreen(settings.vsync);
			
			if (settings.icons != null)
			{
				Display.setIcon(settings.icons);
				
			}
			
			Color bg = settings.bg;
			Display.setInitialBackground(bg.getColorf(ColorFilter.RED), bg.getColorf(ColorFilter.GREEN), bg.getColorf(ColorFilter.BLUE));
			
			Display.setTitle(settings.title);
			Display.setVSyncEnabled(settings.vsync);
			
		}
		catch (LWJGLException e)
		{
			Logger.log().err(e);
			
		}
		
		this.title = Display.getTitle();
		this.width = Display.getWidth();
		this.height = Display.getHeight();
		
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
			Logger.log().err(e);
			
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
			Logger.log().err(e);
			
			return false;
		}
		
		return true;
	}
	
}
