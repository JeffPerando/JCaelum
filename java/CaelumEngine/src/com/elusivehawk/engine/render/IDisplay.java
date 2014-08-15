
package com.elusivehawk.engine.render;

import java.io.Closeable;

/**
 * 
 * The new display system for the Caelum Engine.
 * <p>
 * NOTICE: For the sake of simplicity, it is highly recommended that any implementors maximize
 * the window this object is tied to.
 * 
 * @author Elusivehawk
 */
public interface IDisplay extends Closeable
{
	public String getTitle();
	
	public String getName();
	
	public void createDisplay() throws Exception;
	
	/**
	 * 
	 * @return If the display is in fullscreen.
	 */
	public boolean getFullscreen();
	
	/**
	 * 
	 * @return If the display was actually X'ed out.
	 */
	public boolean isCloseRequested();
	
	/**
	 * 
	 * @return The current height of the current display.
	 */
	public int getHeight();
	
	/**
	 * 
	 * @return The current width of the current display.
	 */
	public int getWidth();
	
	/**
	 * 
	 * Called after rendering; Primarily here to emulate LWJGL's Display system.
	 * 
	 */
	public void updateDisplay();
	
	public void updateSettings(DisplaySettings settings);
	
}
