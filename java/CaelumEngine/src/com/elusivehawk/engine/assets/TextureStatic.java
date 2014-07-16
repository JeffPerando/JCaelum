
package com.elusivehawk.engine.assets;

import com.elusivehawk.engine.render.ILegibleImage;
import com.elusivehawk.engine.render.RenderHelper;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class TextureStatic extends Texture
{
	@SuppressWarnings("unqualified-field-access")
	public TextureStatic(String filename, ILegibleImage img)
	{
		super(filename);
		frames[0] = img;
		
	}
	
	@Override
	protected boolean finishAsset()
	{
		this.tex = RenderHelper.processImage(this.frames[0]);
		
		return this.tex != 0;
	}
	
}
