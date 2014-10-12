
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
	String getTitle();
	
	void createDisplay() throws Throwable;
	
	boolean isCreated();
	
	/**
	 * 
	 * @return If the display is in fullscreen.
	 */
	boolean getFullscreen();
	
	/**
	 * 
	 * @return If the display was actually X'ed out.
	 */
	boolean isCloseRequested();
	
	/**
	 * 
	 * @return The current height of the current display.
	 */
	int getHeight();
	
	/**
	 * 
	 * @return The current width of the current display.
	 */
	int getWidth();
	
	/**
	 * 
	 * Called after rendering; Primarily here to emulate LWJGL's Display system.
	 * 
	 */
	void updateDisplay();
	
	void updateSettings(DisplaySettings settings);
	
	boolean makeCurrent();
	
	boolean releaseContext();
	
}
