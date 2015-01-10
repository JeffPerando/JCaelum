
package com.elusivehawk.caelum.render;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class IconGrid
{
	private final int width, height;
	private final Icon[][] icons;
	
	@SuppressWarnings("unqualified-field-access")
	public IconGrid(int w, int h)
	{
		width = w;
		height = h;
		icons = new Icon[w][h];
		
	}
	
	public int getWidth()
	{
		return this.width;
	}
	
	public int getHeight()
	{
		return this.height;
	}
	
	public Icon getIcon(int x, int y)
	{
		Icon ret = this.icons[x][y];
		
		if (ret == null)
		{
			float xinc = 1f / this.width;
			float yinc = 1f / this.height;
			
			ret = this.icons[x][y] = new Icon(x * xinc, y * yinc, (x + 1) * xinc, (y + 1) * yinc);
			
		}
		
		return ret;
	}
	
	public Icon[] getRow(int y)
	{
		Icon[] ret = new Icon[this.width];
		
		for (int x = 0; x < this.width; x++)
		{
			ret[x] = this.getIcon(x, y);
			
		}
		
		return ret;
	}
	
	public Icon[] getColumn(int x)
	{
		Icon[] ret = new Icon[this.height];
		
		for (int y = 0; y < this.height; y++)
		{
			ret[y] = this.getIcon(x, y);
			
		}
		
		return ret;
	}
	
}
