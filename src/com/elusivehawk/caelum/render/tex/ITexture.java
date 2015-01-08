
package com.elusivehawk.caelum.render.tex;

import com.elusivehawk.caelum.render.IDeletable;
import com.elusivehawk.caelum.render.IPreRenderer;
import com.elusivehawk.caelum.render.gl.GLEnumTexture;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public interface ITexture extends IPreRenderer, IDeletable
{
	default GLEnumTexture getType()
	{
		return GLEnumTexture.GL_TEXTURE_2D;
	}
	
	int getTexId();
	
	boolean isStatic();
	
}
