
package com.elusivehawk.engine.render;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class SubCanvas
{
	private final float x, y, w, h;
	
	private SubCanvas sub = null;
	
	@SuppressWarnings("unqualified-field-access")
	public SubCanvas(float xmin, float ymin, float xmax, float ymax)
	{
		x = xmin;
		y = ymin;
		w = xmax;
		h = ymax;
		
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
	
	public float interpolateX(float f)
	{
		float ret = f + this.x;
		
		if (this.sub != null)
		{
			ret = this.sub.interpolateX(ret);
			
		}
		
		return ret;
	}
	
	public float interpolateY(float f)
	{
		float ret = f + this.y;
		
		if (this.sub != null)
		{
			ret = this.sub.interpolateY(ret);
			
		}
		
		return ret;
	}
	
	public float interpolateW(float f)
	{
		float ret = f + this.w;
		
		if (this.sub != null)
		{
			ret = this.sub.interpolateW(ret);
			
		}
		
		return ret;
	}
	
	public float interpolateH(float f)
	{
		float ret = f + this.h;
		
		if (this.sub != null)
		{
			ret = this.sub.interpolateH(ret);
			
		}
		
		return ret;
	}
	
}
