
package com.elusivehawk.engine.render;

import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.IntBuffer;
import javax.imageio.ImageIO;
import org.lwjgl.BufferUtils;
import com.elusivehawk.engine.core.EnumRenderMode;
import com.elusivehawk.engine.core.GameLog;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class TextureAnimated implements ITexture
{
	private final IntBuffer tex;
	private final int w, h;
	
	public TextureAnimated(File gif, int width, int height, EnumRenderMode mode)
	{
		tex = RenderHelper.processGifFile(gif, mode);
		w = width;
		h = height;
		
		GL.register(this);
		
	}
	
	public TextureAnimated(File file, EnumRenderMode mode, int y)
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
			GameLog.error(e);
			
		}
		
		if (img == null)
		{
			tex = BufferUtils.createIntBuffer(1);
			w = 0;
			h = 0;
			
		}
		else
		{
			if (img.getHeight() % y != 0)
			{
				throw new RuntimeException("Image is not fit for animating!");
			}
			
			tex = BufferUtils.createIntBuffer(img.getHeight() / y);
			w = img.getWidth();
			h = img.getHeight();
			
			for (int c = 0; c < img.getHeight(); c += y)
			{
				BufferedImage sub = img.getSubimage(0, c, img.getWidth(), y);
				
				tex.put(RenderHelper.processImage(sub, mode));
				
			}
			
		}
		
		GL.register(this);
		
	}
	
	@Override
	public void glDelete()
	{
		this.tex.rewind();
		
		GL.glDeleteTextures(this.tex);
		
	}
	
	@Override
	public int getTexture()
	{
		if (this.tex.remaining() == 0)
		{
			this.tex.rewind();
			
		}
		
		return this.tex.get();
	}
	
	@Override
	public boolean isStatic()
	{
		return false;
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
