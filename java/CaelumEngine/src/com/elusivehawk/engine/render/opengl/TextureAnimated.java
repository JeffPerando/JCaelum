
package com.elusivehawk.engine.render.opengl;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import com.elusivehawk.engine.assets.EnumAssetType;
import com.elusivehawk.engine.core.CaelumEngine;
import com.elusivehawk.engine.core.EnumLogType;
import com.elusivehawk.engine.render.EnumColorFormat;
import com.elusivehawk.engine.render.EnumRenderMode;
import com.elusivehawk.engine.render.INonStaticTexture;
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
	protected final Buffer<Integer> tex;
	protected boolean dirty = true;
	
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
	public EnumAssetType getType()
	{
		return EnumAssetType.TEXTURE;
	}
	
	@Override
	public int[] getIds()
	{
		return new int[]{this.tex.next(false)};
	}
	
	@Override
	public boolean isFinished()
	{
		return true;//FIXME
	}
	
	@Override
	public void finish(){}
	
	@Override
	public boolean isDirty()
	{
		return this.dirty;
	}
	
	@Override
	public void setIsDirty(boolean b)
	{
		this.dirty = b;
		
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
		
		this.dirty = true;
		
	}
	
}
