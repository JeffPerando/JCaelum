
package com.elusivehawk.caelum.render;

import com.elusivehawk.caelum.prefab.Rectangle;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class SubCanvas extends Rectangle
{
	private SubCanvas child = null;
	
	public SubCanvas(Rectangle r)
	{
		super(r);
		
	}
	
	public SubCanvas(float xStart, float yStart, float xEnd, float yEnd)
	{
		super(xStart, yStart, xEnd, yEnd);
		
	}
	
	public void createSubCanvas(float x, float y, float z, float w)
	{
		if (this.child == null)
		{
			this.child = new SubCanvas(this.interpolate(x, y, z, w));
			
		}
		else
		{
			this.child.createSubCanvas(x, y, z, w);
			
		}
		
	}
	
	public boolean destroySubCanvas()
	{
		if (this.child == null)
		{
			return false;
		}
		
		if (!this.child.destroySubCanvas())
		{
			this.child = null;
			
		}
		
		return true;
	}
	
}
