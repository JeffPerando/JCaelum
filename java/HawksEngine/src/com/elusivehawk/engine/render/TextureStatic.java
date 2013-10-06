
package com.elusivehawk.engine.render;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import com.elusivehawk.engine.core.EnumRenderMode;

/**
 * 
 * NOTICE: This is simply a wrapper for {@link ITexture}.
 * 
 * @author Elusivehawk
 */
public class TextureStatic implements ITexture
{
	protected final int tex, w, h;
	
	public TextureStatic(int texture, int width, int height)
	{
		tex = texture;
		w = width;
		h = height;
		
	}
	
	public TextureStatic(File file, EnumRenderMode mode) throws IOException
	{
		this(ImageIO.read(file), mode);
		
	}
	
	public TextureStatic(BufferedImage img, EnumRenderMode mode)
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