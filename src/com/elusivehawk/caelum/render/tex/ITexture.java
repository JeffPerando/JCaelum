
package com.elusivehawk.caelum.render.tex;

import com.elusivehawk.caelum.render.IPreRenderer;
import com.elusivehawk.caelum.render.gl.GLEnumTexture;
import com.elusivehawk.caelum.render.gl.IGLDeletable;

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
	
	boolean isStatic();
	
}
