
package com.elusivehawk.engine.render.opengl;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import com.elusivehawk.engine.core.CaelumEngine;
import com.elusivehawk.engine.core.EnumLogType;
import com.elusivehawk.engine.render.EnumColorFormat;
import com.elusivehawk.engine.render.EnumRenderMode;
import com.elusivehawk.engine.render.LegibleBufferedImage;
import com.elusivehawk.engine.render.RenderContext;
import com.elusivehawk.engine.render.RenderHelper;
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
	
	public TextureAnimated(String gif, RenderContext context)
	{
		this(FileHelper.createFile(gif), context);
		
	}

	public TextureAnimated(File gif, RenderContext context)
	{
		this(gif, context, EnumRenderMode.MODE_2D);
		
	}
	
	public TextureAnimated(File gif, RenderContext context, EnumRenderMode mode)
	{
		this(gif, context, mode, EnumColorFormat.RGBA);
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public TextureAnimated(File gif, RenderContext context, EnumRenderMode mode, EnumColorFormat format)
	{
		tex = RenderHelper.processGifFile(gif, context, mode, format);
		
		RenderHelper.register(this);
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public TextureAnimated(File file, RenderContext context, EnumRenderMode mode, EnumColorFormat format, int y)
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
			CaelumEngine.log().log(EnumLogType.ERROR, null, e);
			
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
				
				tex.add(RenderHelper.processImage(new LegibleBufferedImage(sub), mode, format, context));
				
			}
			
		}
		
		RenderHelper.register(this);
		
	}
	
	@Override
	public void glDelete(RenderContext context)
	{
		this.tex.rewind();
		
		for (int i : this.tex)
		{
			context.getGL1().glDeleteTextures(i);
			
		}
		
	}
	
	@Override
	public void updateTexture(RenderContext context)
	{
		if (this.tex.remaining() == 0)
		{
			this.tex.rewind();
			
		}
		else
		{
			this.tex.next();
			
		}
		
	}
	
	@Override
	public int getTexture()
	{
		return this.tex.get();
	}
	
	@Override
	public boolean isStatic()
	{
		return false;
	}
	
}
