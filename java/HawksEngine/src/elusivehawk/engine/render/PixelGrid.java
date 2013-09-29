
package elusivehawk.engine.render;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import javax.imageio.ImageIO;
import org.lwjgl.BufferUtils;
import elusivehawk.engine.core.EnumRenderMode;
import elusivehawk.engine.util.GameLog;

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
public class PixelGrid implements ITexture
{
	protected final int[][] pixels;
	protected final BufferedImage base;
	protected int converted = 0;
	
	public final int xSize, ySize;
	
	public PixelGrid(int w, int h)
	{
		pixels = new int[w][h];
		xSize = w;
		ySize = h;
		base = null;
		
	}
	
	public PixelGrid(BufferedImage img, int x, int y, int w, int h)
	{
		this(img.getSubimage(x, y, w, h));
	}
	
	public PixelGrid(BufferedImage img)
	{
		pixels = new int[img.getWidth()][img.getHeight()];
		xSize = img.getWidth();
		ySize = img.getHeight();
		base = img;
		
		for (int x = 0; x < img.getWidth(); x++)
		{
			for (int y = 0; y < img.getHeight(); y++)
			{
				pixels[x][y] = EnumColorFormat.RGBA.convert(new Color(EnumColorFormat.ARGB, img.getRGB(x, y))).getColor();
				
			}
			
		}
		
	}
	
	public int getColor(int x, int y)
	{
		return this.pixels[x][y];
	}
	
	public void setColor(int x, int y, int col)
	{
		this.pixels[x][y] = col;
		
	}
	
	public void setColor(int x, int y, Color col)
	{
		this.pixels[x][y] = col.getColor();
		
	}
	
	public int toTexture(boolean generate, EnumRenderMode mode)
	{
		if (this.converted == 0 || generate)
		{
			this.converted = RenderHelper.processImage(this.toByteBuffer(), this.xSize, this.ySize, mode);
			
		}
		
		return this.converted;
	}
	
	public BufferedImage toImg()
	{
		return this.toImg(0, 0, this.xSize, this.ySize);
	}
	
	public BufferedImage toImg(int x, int y, int w, int h)
	{
		int imgX = Math.min(x + w, this.xSize);
		int imgY = Math.min(y + h, this.ySize);
		
		BufferedImage ret = new BufferedImage(imgX, imgY, BufferedImage.TYPE_INT_ARGB);
		
		for (int xCoord = 0; xCoord < ret.getWidth(); xCoord++)
		{
			for (int yCoord = 0; yCoord < ret.getHeight(); yCoord++)
			{
				ret.setRGB(xCoord, yCoord, EnumColorFormat.ARGB.convert(new Color(EnumColorFormat.RGBA, this.getColor(xCoord + x, yCoord + y))).getColor());
				
			}
			
		}
		
		return ret;
	}
	
	public ByteBuffer toByteBuffer()
	{
		ByteBuffer ret = BufferUtils.createByteBuffer(this.xSize * this.ySize * 4);
		Color col = new Color();
		
		for (int x = 0; x < this.xSize; x++)
		{
			for (int y = 0; y < this.ySize; y++)
			{
				col.setColor(this.getColor(x, y));
				
				col.store(ret);
				
			}
			
		}
		
		ret.flip();
		
		return ret;
	}
	
	public FloatBuffer toFloatBuffer()
	{
		FloatBuffer ret = BufferUtils.createFloatBuffer(this.xSize * this.ySize * 4);
		Color col = new Color();
		
		for (int x = 0; x < this.xSize; x++)
		{
			for (int y = 0; y < this.ySize; y++)
			{
				col.setColor(this.getColor(x, y));
				
				col.store(ret);
				
			}
			
		}
		
		ret.flip();
		
		return ret;
	}
	
	public IntBuffer toIntBuffer()
	{
		IntBuffer ret = BufferUtils.createIntBuffer(this.xSize * this.ySize);
		Color col = new Color();
		
		for (int x = 0; x < this.xSize; x++)
		{
			for (int y = 0; y < this.ySize; y++)
			{
				col.setColor(this.getColor(x, y));
				
				col.store(ret);
				
			}
			
		}
		
		ret.flip();
		
		return ret;
	}
	
	public void pasteImage(BufferedImage img, int xPos, int yPos)
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
				
				this.setColor(x + xPos, y + yPos, EnumColorFormat.RGBA.convert(new Color(EnumColorFormat.ARGB, img.getRGB(x, y))).getColor());
				
			}
			
		}
		
	}
	
	public void pasteImage(PixelGrid grid, int xPos, int yPos)
	{
		for (int x = 0; x < grid.xSize; x++)
		{
			if (x + xPos > this.xSize)
			{
				break;
			}
			
			for (int y = 0; y < grid.ySize; y++)
			{
				if (y + yPos > this.ySize)
				{
					break;
				}
				
				this.setColor(x + xPos, y + yPos, grid.getColor(x, y));
				
			}
			
		}
		
	}
	
	public boolean writeToFile(File file)
	{
		return this.writeToFile(file, "png");
	}
	
	public boolean writeToFile(File file, String format)
	{
		try
		{
			return ImageIO.write(this.toImg(), format, file);
			
		}
		catch (IOException e)
		{
			GameLog.error(e);
			
		}
		
		return false;
	}
	
	public int replace(int in, int out)
	{
		int ret = 0;
		
		for (int x = 0; x < this.xSize; x++)
		{
			for (int y = 0; y < this.ySize; y++)
			{
				if (this.getColor(x, y) == in)
				{
					this.setColor(x, y, out);
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
				this.setColor(x, y, 0xFFFFFF - this.getColor(x, y));
				
			}
			
		}
		
	}
	
	public void reset()
	{
		for (int x = 0; x < this.xSize; x++)
		{
			for (int y = 0; y < this.ySize; y++)
			{
				this.setColor(x, y, this.base != null ? this.base.getRGB(x, y) : 0);
				
			}
			
		}
		
	}
	
	public static PixelGrid scale(PixelGrid grid, int scale)
	{
		return scale(grid, scale, scale);
	}
	
	public static PixelGrid scale(PixelGrid grid, int xScale, int yScale)
	{
		PixelGrid ret = new PixelGrid(grid.xSize * xScale, grid.ySize * yScale);
		
		for (int x = 0; x < grid.xSize; x++)
		{
			for (int y = 0; y < grid.ySize; y++)
			{
				int col = grid.getColor(x, y);
				
				for (int w = 0; w < xScale; w++)
				{
					for (int h = 0; h < yScale; h++)
					{
						ret.setColor(x + w * xScale, y + h * yScale, col);
						
					}
					
				}
				
			}
			
		}
		
		return ret;
	}
	
	@Override
	public int getTexture()
	{
		return this.converted;
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
