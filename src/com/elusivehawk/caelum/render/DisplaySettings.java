
package com.elusivehawk.caelum.render;

import java.nio.ByteBuffer;
import com.elusivehawk.caelum.Game;
import com.elusivehawk.caelum.render.tex.Color;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class DisplaySettings
{
	public String title = "Caelum Engine Game (Now with less thread locking!)";
	public int width = 800, height = 600;
	public ByteBuffer[] icons = null;
	public int targetFPS = 30;
	public boolean resize = false;
	public boolean fullscreen = false;
	public boolean vsync = false;
	public Color bg = Color.BLACK;
	
	public DisplaySettings(){}
	
	@SuppressWarnings("unqualified-field-access")
	public DisplaySettings(Game game)
	{
		if (game != null)
		{
			String name = game.getFormattedName();
			
			if (name != null && !"".equals(name))
			{
				title = game.getFormattedName();
				
			}
			
		}
		
	}
	
	@Override
	public Object clone()
	{
		DisplaySettings ret = new DisplaySettings();
		
		ret.title = this.title;
		ret.width = this.width;
		ret.height = this.height;
		ret.icons = this.icons;
		ret.targetFPS = this.targetFPS;
		ret.resize = this.resize;
		ret.fullscreen = this.fullscreen;
		ret.vsync = this.vsync;
		ret.bg = this.bg;
		
		return ret;
	}
	
}
