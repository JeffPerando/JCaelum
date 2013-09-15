
package elusivehawk.engine.render;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

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
	
	public GenericTexture(File file, boolean is3D) throws IOException
	{
		this(ImageIO.read(file), is3D);
		
	}
	
	public GenericTexture(BufferedImage img, boolean is3D)
	{
		this(RenderHelper.processImage(img, is3D, true), img.getWidth(), img.getHeight());
		
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
