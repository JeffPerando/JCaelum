
package com.elusivehawk.engine.assets;

import java.io.File;
import com.elusivehawk.engine.render.ILegibleImage;
import com.elusivehawk.engine.render.RenderHelper;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class TextureGif extends Texture
{
	protected final File gif;
	protected int[] textures = null;
	
	@SuppressWarnings("unqualified-field-access")
	public TextureGif(File file)
	{
		super(file.getName());
		gif = file;
		
	}
	
	@Override
	protected boolean finishAsset()
	{
		ILegibleImage[] imgs = RenderHelper.readGifFile(this.gif);
		
		if (imgs != null)
		{
			for (int c = 0; c < imgs.length; c++)
			{
				this.textures[c] = RenderHelper.processImage(imgs[c]);
				
			}
			
			return true;
		}
		
		return false;
	}
	
	@Override
	public int getTexId(int frame)
	{
		return this.textures[frame];
	}
	
}
