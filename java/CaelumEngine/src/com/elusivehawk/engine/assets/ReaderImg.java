
package com.elusivehawk.engine.assets;

import java.io.File;
import javax.imageio.ImageIO;
import com.elusivehawk.engine.render.LegibleBufferedImage;
import com.elusivehawk.engine.render.RenderHelper;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class ReaderImg implements AssetReader
{
	@Override
	public Asset readAsset(File file) throws Exception
	{
		if (file.getName().endsWith(".gif"))
		{
			return new TextureGif(RenderHelper.readGifFile(file));
		}
		
		return new Texture(new LegibleBufferedImage(ImageIO.read(file)));
	}
	
}
