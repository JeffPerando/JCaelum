
package com.elusivehawk.engine.render2;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import com.elusivehawk.engine.core.CaelumEngine;
import com.elusivehawk.engine.core.EnumLogType;
import com.elusivehawk.engine.render.opengl.GL;
import com.elusivehawk.engine.render.opengl.ITexture;
import com.elusivehawk.engine.util.Buffer;
import com.elusivehawk.engine.util.FileHelper;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class TextureAnimated implements ITexture
{
	private final Buffer<Integer> tex;
	
	public TextureAnimated(String gif)
	{
		this(FileHelper.createFile(gif));
		
	}

	public TextureAnimated(File gif)
	{
		this(gif, EnumRenderMode.MODE_2D);
		
	}
	
	public TextureAnimated(File gif, EnumRenderMode mode)
	{
		this(gif, mode, EnumColorFormat.RGBA);
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public TextureAnimated(File gif, EnumRenderMode mode, EnumColorFormat format)
	{
		tex = RenderHelper.processGifFile(gif, mode, format);
		
		RenderHelper.register(this);
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public TextureAnimated(File file, EnumRenderMode mode, EnumColorFormat format, int y)
	{
		if (file.getName().endsWith(".gif"))
		{
			throw new RuntimeException("Wrong constructor, Einstein.");
		}
		
		BufferedImage img = null;
		
		try
		{
			img = ImageIO.read(file);
			
		}
		catch (Exception e)
		{
			CaelumEngine.instance().getLog().log(EnumLogType.ERROR, null, e);
			
		}
		
		if (img == null)
		{
			tex = new Buffer<Integer>();
			
		}
		else
		{
			if (img.getHeight() % y != 0)
			{
				throw new RuntimeException("Image is not fit for animating!");
			}
			
			tex = new Buffer<Integer>();
			
			for (int c = 0; c < img.getHeight(); c += y)
			{
				BufferedImage sub = img.getSubimage(0, c, img.getWidth(), y);
				
				tex.add(RenderHelper.processImage(new LegibleBufferedImage(sub), mode, format));
				
			}
			
		}
		
		RenderHelper.register(this);
		
	}
	
	@Override
	public void glDelete()
	{
		this.tex.rewind();
		
		for (int i : this.tex)
		{
			GL.glDeleteTextures(i);
			
		}
		
	}
	
	@Override
	public int getTexture(boolean next)
	{
		if (this.tex.remaining() == 0)
		{
			this.tex.rewind();
			
		}
		
		return this.tex.next(next);
	}
	
	@Override
	public boolean isStatic()
	{
		return false;
	}
	
}
