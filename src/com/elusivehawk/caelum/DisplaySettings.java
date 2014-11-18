
package com.elusivehawk.caelum;

import com.elusivehawk.caelum.render.tex.Color;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class DisplaySettings
{
	public String title = "Caelum Engine Game (Now with multiple window support!)";
	public int width = 800, height = 600;
	public boolean resize = false;
	public boolean fullscreen = false;
	public boolean vsync = false;
	public Color bg = Color.BLACK;
	
	public DisplaySettings(){}
	
	@SuppressWarnings("unqualified-field-access")
	public DisplaySettings(DisplaySettings settings)
	{
		title = settings.title;
		width = settings.width;
		height = settings.height;
		resize = settings.resize;
		fullscreen = settings.fullscreen;
		vsync = settings.vsync;
		bg = settings.bg;
		
	}
	
	@Override
	public Object clone()
	{
		return new DisplaySettings(this);
	}
	
}
