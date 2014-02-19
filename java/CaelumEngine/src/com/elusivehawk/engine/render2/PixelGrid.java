
package com.elusivehawk.engine.render2;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import com.elusivehawk.engine.util.BufferHelper;

/**
 * 
 * Use this to create procedurally generated textures.
 * <p>
 * <b>NOTICE:</b> Unlike {@link RenderHelper}'s method of handling textures,
 * this is more direct, so do *NOT* be an idiot when working with this.
 * <p>
 * You have been warned.
 * 
 * @author Elusivehawk
 */
public class PixelGrid implements ILegibleImage
{
	protected final int[][] pixels;
	protected final ILegibleImage base;
	protected final int xSize, ySize;
	protected final EnumColorFormat f;
	
	@SuppressWarnings("unqualified-field-access")
	public PixelGrid(int w, int h, EnumColorFormat format)
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
				pixels[x][y] = new Color(f, img.getPixel(x, y)).getColor();
				
			}
			
		}
		
	}
	
	public void setPixel(int x, int y, int col)
	{
		this.pixels[x][y] = col;
		
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
				
				for (EnumColorFilter filter : this.f.filters)
				{
					ret.put(col.getColor(filter));
					
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
				
				for (EnumColorFilter filter : this.f.filters)
				{
					ret.put(col.getColorFloat(filter));
					
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
	
	public void invertColors()
	{
		for (int x = 0; x < this.xSize; x++)
		{
			for (int y = 0; y < this.ySize; y++)
			{
				this.setPixel(x, y, 0xFFFFFF - this.getPixel(x, y));
				
			}
			
		}
		
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
	
	public static PixelGrid scale(PixelGrid grid, int scale)
	{
		return scale(grid, scale, scale);
	}
	
	public static PixelGrid scale(PixelGrid grid, int xScale, int yScale)
	{
		PixelGrid ret = new PixelGrid(grid.xSize * xScale, grid.ySize * yScale, grid.getFormat());
		
		for (int x = 0; x < grid.xSize; x++)
		{
			for (int y = 0; y < grid.ySize; y++)
			{
				int col = grid.getPixel(x, y);
				
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
	
	@Override
	public int getPixel(int x, int y)
	{
		return this.pixels[x][y];
	}
	
	@Override
	public EnumColorFormat getFormat()
	{
		return this.f;
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
	
}
