
package elusivehawk.engine.render;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import elusivehawk.engine.core.EnumRenderMode;

/**
 * 
 * NOTICE: This is simply a wrapper for {@link ITexture}.
 * 
 * @author Elusivehawk
 */
public class GenericTexture implements ITexture
{
	protected final int tex, w, h;
	
	public GenericTexture(int texture, int width, int height)
	{
		tex = texture;
		w = width;
		h = height;
		
	}
	
	public GenericTexture(File file, EnumRenderMode mode) throws IOException
	{
		this(ImageIO.read(file), mode);
		
	}
	
	public GenericTexture(BufferedImage img, EnumRenderMode mode)
	{
		this(RenderHelper.processImage(img, mode), img.getWidth(), img.getHeight());
		
	}
	
	@Override
	public int getTexture()
	{
		return this.tex;
	}
	
	@Override
	public int getHeight()
	{
		return this.h;
	}
	
	@Override
	public int getWidth()
	{
		return this.w;
	}
	
}
