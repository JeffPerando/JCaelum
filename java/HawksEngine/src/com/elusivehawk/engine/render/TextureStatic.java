
package com.elusivehawk.engine.render;

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * 
 * NOTICE: This is simply a wrapper for {@link ITexture}.
 * 
 * @author Elusivehawk
 */
public class TextureStatic implements ITexture
{
	protected final int tex;
	
	public TextureStatic(int texture)
	{
		tex = texture;
		
		GL.register(this);
		
	}
	
	public TextureStatic(String path) throws IOException
	{
		this(path, EnumRenderMode.MODE_2D, EnumColorFormat.RGBA);
		
	}
	
	public TextureStatic(String path, EnumRenderMode mode, EnumColorFormat format) throws IOException
	{
		this(new File(ClassLoader.getSystemResource(path).getFile()), mode, format);
		
	}
	
	public TextureStatic(File file, EnumRenderMode mode, EnumColorFormat format) throws IOException
	{
		this(new LegibleBufferedImage(ImageIO.read(file)), mode, format);
		
	}
	
	public TextureStatic(ILegibleImage img, EnumRenderMode mode, EnumColorFormat format)
	{
		this(RenderHelper.processImage(img, mode, format));
		
	}
	
	@Override
	public void glDelete()
	{
		GL.glDeleteTextures(this.getTexture());
		
	}
	
	@Override
	public int getTexture()
	{
		return this.tex;
	}
	
	@Override
	public boolean isStatic()
	{
		return true;
	}
	
}
