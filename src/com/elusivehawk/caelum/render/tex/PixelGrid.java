
package com.elusivehawk.caelum.render.tex;

import java.awt.image.BufferedImage;
import com.elusivehawk.util.IPopulator;

/**
 * 
 * Helper class for creating procedurally generated textures.
 * 
 * @author Elusivehawk
 */
public class PixelGrid implements ILegibleImage
{
	protected final ColorFormat format;
	protected final int[][] pixels;
	protected final ILegibleImage base;
	protected final int xSize, ySize;
	
	public PixelGrid(int w, int h)
	{
		this(ColorFormat.RGBA, w, h);
		
	}
	
	public PixelGrid(int w, int h, IPopulator<PixelGrid> pop)
	{
		this(w, h);
		
		pop.populate(this);
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public PixelGrid(ColorFormat f, int w, int h)
	{
		format = f;
		pixels = new int[w][h];
		xSize = w;
		ySize = h;
		base = null;
		
	}
	
	public PixelGrid(ColorFormat f, int w, int h, IPopulator<PixelGrid> pop)
	{
		this(f, w, h);
		
		pop.populate(this);
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public PixelGrid(ILegibleImage img)
	{
		pixels = new int[img.getWidth()][img.getHeight()];
		xSize = img.getWidth();
		ySize = img.getHeight();
		base = img;
		format = img.getFormat();
		
		for (int x = 0; x < img.getWidth(); x++)
		{
			for (int y = 0; y < img.getHeight(); y++)
			{
				pixels[x][y] = img.getPixel(x, y);
				
			}
			
		}
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public PixelGrid(BufferedImage img)
	{
		this(getFormat(img), img.getWidth(), img.getHeight());
		
		for (int x = 0; x < img.getWidth(); x++)
		{
			for (int y = 0; y < img.getHeight(); y++)
			{
				pixels[x][y] = img.getRGB(x, (img.getHeight() - 1) - y);
				
			}
			
		}
		
	}
	
	@Override
	public int getPixel(int x, int y)
	{
		return this.pixels[x][y];
	}
	
	@Override
	public void setPixel(int x, int y, int col)
	{
		this.pixels[x][y] = col;
		
	}
	
	@Override
	public int getHeight()
	{
		return this.ySize;
	}
	
	@Override
	public int getWidth()
	{
		return this.xSize;
	}
	
	@Override
	public ColorFormat getFormat()
	{
		return this.format;
	}
	
	@Override
	public PixelGrid clone()
	{
		return new PixelGrid(this);
	}
	
	public void setColor(int x, int y, Color col)
	{
		this.pixels[x][y] = col.getColor();
		
	}
	
	public void pasteImage(ILegibleImage img, int xPos, int yPos)
	{
		Color col = new Color(img.getFormat());
		
		for (int x = 0; x < img.getWidth(); x++)
		{
			if (x + xPos > this.xSize)
			{
				break;
			}
			
			for (int y = 0; y < img.getHeight(); y++)
			{
				if (y + yPos > this.ySize)
				{
					break;
				}
				
				this.setPixel(x + xPos, y + yPos, col.setColor(img.getPixel(x, y)).convertTo(this.format).getColor());
				
			}
			
		}
		
	}
	
	public int replace(int in, int out)
	{
		int ret = 0;
		
		for (int x = 0; x < this.xSize; x++)
		{
			for (int y = 0; y < this.ySize; y++)
			{
				if (this.getPixel(x, y) == in)
				{
					this.setPixel(x, y, out);
					ret++;
					
				}
				
			}
			
		}
		
		return ret;
	}
	
	public void reset()
	{
		for (int x = 0; x < this.xSize; x++)
		{
			for (int y = 0; y < this.ySize; y++)
			{
				this.setPixel(x, y, this.base != null ? this.base.getPixel(x, y) : 0);
				
			}
			
		}
		
	}
	
	public PixelGrid scale(int scale)
	{
		return this.scale(scale, scale);
	}
	
	public PixelGrid scale(int xScale, int yScale)
	{
		PixelGrid ret = new PixelGrid(this.getFormat(), this.xSize * xScale, this.ySize * yScale);
		
		for (int x = 0; x < this.xSize; x++)
		{
			for (int y = 0; y < this.ySize; y++)
			{
				int col = this.getPixel(x, y);
				
				for (int w = 0; w < xScale; w++)
				{
					for (int h = 0; h < yScale; h++)
					{
						ret.setPixel(x + w * xScale, y + h * yScale, col);
						
					}
					
				}
				
			}
			
		}
		
		return ret;
	}
	
	private static ColorFormat getFormat(BufferedImage img)
	{
		switch (img.getType())
		{
			case BufferedImage.TYPE_3BYTE_BGR:
			case BufferedImage.TYPE_INT_BGR: return ColorFormat.BGRA;
			case BufferedImage.TYPE_4BYTE_ABGR:
			case BufferedImage.TYPE_4BYTE_ABGR_PRE: return ColorFormat.ABGR;
			case BufferedImage.TYPE_INT_ARGB:
			case BufferedImage.TYPE_INT_ARGB_PRE: return ColorFormat.ARGB;
			case BufferedImage.TYPE_INT_RGB:
			default: return ColorFormat.RGBA;
		}
		
	}
	
}
