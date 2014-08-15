
package com.elusivehawk.engine.render;

import java.nio.ByteBuffer;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class DisplaySettings
{
	public String title = "Caelum Engine Game (Now with even MORE deprecation!)";
	public int width = 800, height = 600;
	public ByteBuffer[] icons = null;
	public int targetFPS = 30;
	public boolean resize = false;
	public boolean fullscreen = false;
	public boolean vsync = false;
	public Color bg = Color.BLACK;
	
}
