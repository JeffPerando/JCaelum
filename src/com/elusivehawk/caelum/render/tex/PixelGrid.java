
package com.elusivehawk.caelum.render.tex;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import com.elusivehawk.util.storage.BufferHelper;

/**
 * 
 * Helper class for creating procedurally generated textures.
 * 
 * @author Elusivehawk
 */
public class PixelGrid implements ILegibleImage
{
	protected final int[][] pixels;
	protected final ILegibleImage base;
	protected final int xSize, ySize;
	protected final ColorFormat f;
	
	public PixelGrid(int w, int h)
	{
		this(w, h, ColorFormat.RGBA);
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public PixelGrid(int w, int h, ColorFormat format)
	{
		pixels = new int[w][h];
		xSize = w;
		ySize = h;
		base = null;
		f = format;
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public PixelGrid(ILegibleImage img)
	{
		pixels = new int[img.getWidth()][img.getHeight()];
		xSize = img.getWidth();
		ySize = img.getHeight();
		base = img;
		f = img.getFormat();
		
		for (int x = 0; x < img.getWidth(); x++)
		{
			for (int y = 0; y < img.getHeight(); y++)
			{
				pixels[x][y] = img.getPixel(x, y);
				
			}
			
		}
		
	}
	
	@Override
	public int getPixel(int x, int y)
	{
		return this.pixels[x][y];
	}
	
	@Override
	public boolean setPixel(int x, int y, int col)
	{
		this.pixels[x][y] = col;
		
		return true;
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
		return this.f;
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
	
	public ByteBuffer toByteBuffer()
	{
		ByteBuffer ret = BufferHelper.createByteBuffer(this.xSize * this.ySize * 4);
		Color col = new Color(this.f);
		
		for (int x = 0; x < this.xSize; x++)
		{
			for (int y = 0; y < this.ySize; y++)
			{
				col.setColor(this.getPixel(x, y));
				
				for (ColorFilter filter : this.f.filters)
				{
					ret.put((byte)col.getColor(filter));
					
				}
				
			}
			
		}
		
		ret.flip();
		
		return ret;
	}
	
	public FloatBuffer toFloatBuffer()
	{
		FloatBuffer ret = BufferHelper.createFloatBuffer(this.xSize * this.ySize * 4);
		Color col = new Color(this.f);
		
		for (int x = 0; x < this.xSize; x++)
		{
			for (int y = 0; y < this.ySize; y++)
			{
				col.setColor(this.getPixel(x, y));
				
				for (ColorFilter filter : this.f.filters)
				{
					ret.put(col.getColorf(filter));
					
				}
				
			}
			
		}
		
		ret.flip();
		
		return ret;
	}
	
	public IntBuffer toIntBuffer()
	{
		IntBuffer ret = BufferHelper.createIntBuffer(this.xSize * this.ySize);
		
		for (int x = 0; x < this.xSize; x++)
		{
			for (int y = 0; y < this.ySize; y++)
			{
				ret.put(this.getPixel(x, y));
				
			}
			
		}
		
		ret.flip();
		
		return ret;
	}
	
	public void pasteImage(ILegibleImage img, int xPos, int yPos)
	{
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
				
				this.setPixel(x + xPos, y + yPos, this.f.convert(new Color(img.getFormat(), img.getPixel(x, y))).getColor());
				
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
		PixelGrid ret = new PixelGrid(this.xSize * xScale, this.ySize * yScale, this.getFormat());
		
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
	
}
