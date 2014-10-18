
package com.elusivehawk.engine.render.tex;

import com.elusivehawk.engine.render.IPreRenderer;
import com.elusivehawk.engine.render.gl.GLEnumTexture;
import com.elusivehawk.engine.render.gl.IGLDeletable;

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
