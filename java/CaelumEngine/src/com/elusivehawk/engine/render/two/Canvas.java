
package com.elusivehawk.engine.render.two;

import com.elusivehawk.engine.render.Filterable;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class Canvas extends Filterable
{
	private SubCanvas sub = null;
	
	@SuppressWarnings("unqualified-field-access")
	public Canvas(int imgs)
	{
		
	}
	
	public void createSubCanvas(float xmin, float ymin, float xmax, float ymax)
	{
		if (this.sub == null)
		{
			this.sub = new SubCanvas(xmin, ymin, xmax, ymax);
			
		}
		else
		{
			this.sub.createSubCanvas(xmin, ymin, xmax, ymax);
			
		}
		
	}
	
	public boolean destroySubCanvas()
	{
		if (this.sub == null)
		{
			return false;
		}
		
		if (!this.sub.destroySubCanvas())
		{
			this.sub = null;
			
		}
		
		return true;
	}
	
	public void drawImage(float x, float y, float z, float w, Icon icon)
	{
		
		
	}
	
}
