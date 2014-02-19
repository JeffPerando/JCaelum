
package com.elusivehawk.engine.render2;

import com.elusivehawk.engine.render.opengl.GLProgram;
import com.elusivehawk.engine.util.IDirty;

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
	 * @param context The current rendering context.
	 */
	public void updateCamera(RenderContext context);
	
	/**
	 * 
	 * Called once every frame, after rendering.
	 * 
	 * @param context The current rendering context.
	 */
	public void postRender(RenderContext context);
	
	/**
	 * 
	 * Called whenever an object wants to use this camera; Use this to update the program's uniforms.
	 * 
	 * @param p The GLProgram asking to be manipulated.
	 * @param context The current rendering context.
	 * @param mode The render mode to consider when manipulating the program's uniforms.
	 */
	public void updateUniform(GLProgram p, RenderContext context, EnumRenderMode mode);
	
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
