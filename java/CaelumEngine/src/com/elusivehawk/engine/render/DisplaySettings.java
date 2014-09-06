
package com.elusivehawk.engine.render;

import java.nio.ByteBuffer;
import com.elusivehawk.engine.Game;

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
	@Deprecated
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
	
}
