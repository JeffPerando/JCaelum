
package com.elusivehawk.caelum;

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
public interface IDisplayImpl extends Closeable
{
	void createDisplay() throws Throwable;
	
	void postInit();
	
	boolean isCreated();
	
	/**
	 * 
	 * @return If the display was actually X'ed out.
	 */
	boolean isCloseRequested();
	
	int getPosX();
	
	int getPosY();
	
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
