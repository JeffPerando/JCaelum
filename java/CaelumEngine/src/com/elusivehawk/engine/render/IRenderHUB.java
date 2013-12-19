
package com.elusivehawk.engine.render;

import java.util.Collection;
import com.elusivehawk.engine.core.IUpdatable;

/**
 * 
 * The rendering HUB for the current game.
 * 
 * @author Elusivehawk
 */
public interface IRenderHUB extends IUpdatable
{
	/**
	 * 
	 * Note: It's recommended to not return null.
	 * 
	 * @return The mode for this HUB.
	 */
	public EnumRenderMode getRenderMode();
	
	/**
	 * @return True to update the current display's settings.
	 */
	public boolean updateDisplay();
	
	/**
	 * @return The settings for the current display.
	 */
	public DisplaySettings getSettings();
	
	/**
	 * @return The camera to use for rendering.
	 */
	public ICamera getCamera();
	
	/**
	 * @return The current scene to render.
	 */
	public IScene getScene();
	
	/**
	 * @return The renderers to render the current scene with.
	 */
	public Collection<IRenderEngine> getRenderEngines();
	
	/**
	 * @return The highest renderer priority.
	 */
	public int getHighestPriority();
	
}
