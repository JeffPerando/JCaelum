
package com.elusivehawk.engine.render;

import com.elusivehawk.engine.core.IDirty;
import com.elusivehawk.engine.render.opengl.GLProgram;

/**
 * 
 * The thing that lets you look out into the game world.
 * 
 * @author Elusivehawk
 */
public interface ICamera extends IDirty
{
	/**
	 * 
	 * Called once every frame; Do whatever you need to before rendering in this method.
	 * 
	 * @param hub The current rendering HUB.
	 */
	public void updateCamera(IRenderHUB hub);
	
	/**
	 * 
	 * Called once every frame, after rendering.
	 * 
	 * @param hub The current rendering HUB.
	 */
	public void postRender(IRenderHUB hub);
	
	/**
	 * 
	 * Called whenever an object wants to use this camera; Use this to update the program's uniforms.
	 * 
	 * @param p The GLProgram asking to be manipulated.
	 */
	public void updateUniform(GLProgram p);
	
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
