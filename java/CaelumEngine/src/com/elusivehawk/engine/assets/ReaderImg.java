
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
public class ReaderImg implements IAssetReader
{
	@Override
	public Asset readAsset(AssetManager mgr, File file) throws Exception
	{
		if (file.getName().endsWith(".gif"))
		{
			return new TextureGif(file.getName(), RenderHelper.readGifFile(file));
		}
		
		return new TextureStatic(file.getName(), new LegibleBufferedImage(ImageIO.read(file)));
	}
	
}
