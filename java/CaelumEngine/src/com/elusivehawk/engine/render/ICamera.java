
package com.elusivehawk.engine.render;

import com.elusivehawk.engine.render.opengl.IGLManipulator;
import com.elusivehawk.engine.util.IDirty;

/**
 * 
 * The thing that lets you look out into the game world.
 * 
 * @author Elusivehawk
 */
public interface ICamera extends IDirty, IPostRenderer, IGLManipulator
{
	/**
	 * Called once every frame; Do whatever you need to before rendering in this method.
	 */
	public void updateCamera();
	
	/**
	 * 
	 * Called if something needs to know something about this camera.
	 * 
	 * @param pollType The statistic being requested.
	 * @return The float being requested.
	 */
	public float getFloat(EnumCameraPollType pollType);
	
	/**
	 * 
	 * Setter for {{@link #getFloat(EnumCameraPollType)}.
	 * 
	 * @param pollType The statistic being set.
	 * @param f The value of the statistic.
	 * @return True if it was set.
	 */
	public boolean setFloat(EnumCameraPollType pollType, float f);
	
}
