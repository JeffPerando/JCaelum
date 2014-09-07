
package com.elusivehawk.engine.render.old;

import java.util.Collection;
import com.elusivehawk.engine.render.DisplaySettings;
import com.elusivehawk.engine.render.IDisplay;
import com.elusivehawk.util.IUpdatable;

/**
 * 
 * The rendering HUB for the current game.
 * 
 * @author Elusivehawk
 */
@Deprecated
public interface IRenderHUB extends IUpdatable
{
	/**
	 * 
	 * Called once during the startup phase.
	 * 
	 * @param d The current display.
	 */
	public void initiate(IDisplay d);
	
	/**
	 * 
	 * Called when the display is resized.
	 * 
	 * @param d The current display.
	 */
	public void onResize(IDisplay d);
	
	/**
	 * @return True to update the current display's settings.
	 */
	public boolean updateDisplay();
	
	/**
	 * @return The settings for the current display.
	 */
	public DisplaySettings getSettings();
	
	/**
	 * @return The current scene to render.
	 */
	public IScene getScene();
	
	/**
	 * @return The renderers to render the current scene with.
	 */
	public Collection<IRenderEngine> getRenderEngines();
	
}
