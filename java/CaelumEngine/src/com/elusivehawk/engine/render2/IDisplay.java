
package com.elusivehawk.engine.render2;

import java.io.Closeable;
import com.elusivehawk.engine.render.opengl.IGL1;

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
	
	/**
	 * 
	 * @return If the display is in fullscreen.
	 */
	public boolean getFullscreen();
	
	/**
	 * 
	 * @return If the display has VSync enabled.
	 */
	public boolean getVSync();
	
	/**
	 * 
	 * @return If the display can be closed via X-ing out.
	 */
	public boolean canClose();
	
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
	
	public void setTitle(String title);
	
	/**
	 * 
	 * Called when the display needs to be resized.
	 * 
	 * @param height The new height.
	 * @param width The new width.
	 */
	public void resize(int height, int width);
	
	/**
	 * 
	 * @param close If the display can be X'ed out.
	 */
	public void setCloseable(boolean close);
	
	public void setFullscreen(boolean full);
	
	public void setVSync(boolean vsync);
	
	/**
	 * 
	 * @param fps The expected framerate the display is suppose to run at.
	 */
	public void setFPS(int fps);
	
	/**
	 * 
	 * Called after rendering; Primarily here to emulate LWJGL's Display system.
	 * 
	 */
	public void updateDisplay();
	
	/**
	 * 
	 * Note: When this is called, it's recommended to also call {@link IGL1}.glClearColor().
	 * 
	 * @param col The new background (and clear) color.
	 */
	public void setBackgroundColor(Color col);
	
}
