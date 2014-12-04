
package com.elusivehawk.caelum;

import java.io.Closeable;

/**
 * 
 * Implementation interface for {@link Display}s.
 * 
 * @author Elusivehawk
 */
public interface IDisplayImpl extends Closeable
{
	void createDisplay(DisplaySettings settings) throws Throwable;
	
	void postInit();
	
	boolean isCreated();
	
	/**
	 * 
	 * @return If the display was actually X'ed out.
	 */
	boolean isCloseRequested();
	
	/**
	 * 
	 * @return The current height of the display.
	 */
	int getHeight();
	
	/**
	 * 
	 * @return The current width of the display.
	 */
	int getWidth();
	
	void preRenderDisplay();
	
	/**
	 * 
	 * Called after rendering; Primarily here to emulate LWJGL's Display system.
	 * 
	 */
	void updateDisplay();
	
	void updateSettings(DisplaySettings settings);
	
}
