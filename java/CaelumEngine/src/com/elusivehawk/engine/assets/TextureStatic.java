
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
	protected final ILegibleImage image;
	
	@SuppressWarnings("unqualified-field-access")
	public TextureStatic(String filename, ILegibleImage img)
	{
		super(filename);
		image = img;
		
	}
	
	@Override
	protected boolean finishAsset()
	{
		this.tex = RenderHelper.processImage(this.image);
		
		return this.tex != 0;
	}
	
	@Override
	public ILegibleImage getSourceImg(int frame)
	{
		return this.image;
	}
	
}
