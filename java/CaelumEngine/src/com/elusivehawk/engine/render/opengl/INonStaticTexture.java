
package com.elusivehawk.engine.render.opengl;

import com.elusivehawk.engine.core.Asset;
import com.elusivehawk.engine.util.IDirty;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public interface INonStaticTexture extends Asset, IDirty
{
	/**
	 * Called once a frame.
	 * 
	 * Note: Should not be called by user code.
	 */
	public void updateTexture();
	
}
