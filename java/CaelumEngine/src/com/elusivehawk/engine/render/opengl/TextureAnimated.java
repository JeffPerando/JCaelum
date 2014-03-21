
package com.elusivehawk.engine.render.opengl;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import com.elusivehawk.engine.core.CaelumEngine;
import com.elusivehawk.engine.core.EnumLogType;
import com.elusivehawk.engine.render.EnumColorFormat;
import com.elusivehawk.engine.render.EnumRenderMode;
import com.elusivehawk.engine.render.LegibleBufferedImage;
import com.elusivehawk.engine.render.RenderHelper;
import com.elusivehawk.engine.util.Buffer;
import com.elusivehawk.engine.util.FileHelper;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class TextureAnimated implements INonStaticTexture
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
				
				tex.add(RenderHelper.processImage(new LegibleBufferedImage(sub), mode, format));
				
			}
			
		}
		
	}
	
	@Override
	public void glDelete()
	{
		this.tex.rewind();
		
		IGL1 gl1 = RenderHelper.gl1();
		
		for (int i : this.tex)
		{
			gl1.glDeleteTextures(i);
			
		}
		
	}
	
	@Override
	public void updateTexture()
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
	public boolean bind(int... extras)
	{
		RenderHelper.gl1().glBindTexture(GLConst.GL_TEXTURE0 + (extras == null || extras.length == 0 ? 0 : extras[0]), this);
		
		try
		{
			RenderHelper.checkForGLError();
			
		}
		catch (Exception e)
		{
			this.unbind(extras);
			
			return false;
		}
		
		return true;
	}
	
	@Override
	public void unbind(int... extras)
	{
		RenderHelper.gl1().glBindTexture(GLConst.GL_TEXTURE0 + (extras == null || extras.length == 0 ? 0 : extras[0]), 0);
		
	}
	
}
