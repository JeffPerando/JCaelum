
package com.elusivehawk.caelum.render.tex;

import com.elusivehawk.caelum.render.IDeletable;
import com.elusivehawk.caelum.render.IPreRenderer;
import com.elusivehawk.caelum.render.gl.GLEnumTexture;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 * 
 * @see TextureBinder
 */
public interface ITexture extends IPreRenderer, IDeletable
{
	GLEnumTexture getType();
	
	int getId();
	
	boolean isStatic();
	
	default int bindTex()
	{
		return TextureBinder.instance().bindTexture(this);
	}
	
}
