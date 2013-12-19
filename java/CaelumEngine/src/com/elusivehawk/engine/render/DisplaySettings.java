
package com.elusivehawk.engine.render;

import java.nio.ByteBuffer;
import org.lwjgl.opengl.DisplayMode;

/**
 * 
 * Pretty much every setter in {@link Display} in one class.
 * 
 * @author Elusivehawk
 */
public class DisplaySettings
{
	public String title = "Caelum Engine Game (Now with less gimmicks!)";
	public DisplayMode mode = new DisplayMode(800, 600);
	public ByteBuffer[] icons = null;
	public int targetFPS = 30;
	public boolean resize = false;
	public boolean fullscreen = false;
	public boolean vsync = false;
	public Color bg = new Color(EnumColorFormat.RGBA);
	@Deprecated
	public float gamma = 0, brightness = 0, constrast = 0;
	
}
