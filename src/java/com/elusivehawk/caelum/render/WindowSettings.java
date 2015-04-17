
package com.elusivehawk.caelum.render;

import com.elusivehawk.caelum.render.tex.Color;
import com.elusivehawk.util.IPopulator;
import com.elusivehawk.util.MakeStruct;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
@MakeStruct
public class WindowSettings
{
	public String
				title = "Caelum Engine Game (Now with more code!)",
				monitor = "default";
	public int width = 800, height = 600;
	public boolean resize = false;
	public boolean fullscreen = false;
	public boolean vsync = true;
	public Color bg = Color.WHITE;
	
	public WindowSettings(){}
	
	public WindowSettings(IPopulator<WindowSettings> pop)
	{
		pop.populate(this);
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public WindowSettings(WindowSettings settings)
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
		return new WindowSettings(this);
	}
	
}
