
package com.elusivehawk.engine.render;

import com.elusivehawk.engine.render.opengl.GLEnumTexture;
import com.elusivehawk.engine.render.opengl.IGLDeletable;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public interface ITexture extends IPreRenderer, IGLDeletable
{
	default GLEnumTexture getType()
	{
		return GLEnumTexture.GL_TEXTURE_2D;
	}
	
	int getTexId();
	
	boolean isAnimated();
	
}
