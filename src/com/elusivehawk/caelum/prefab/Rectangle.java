
package com.elusivehawk.caelum.prefab;

import com.elusivehawk.util.math.MathConst;
import com.elusivehawk.util.math.MathHelper;
import com.elusivehawk.util.math.Vector;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class Rectangle
{
	public final float x, y, z, w;
	
	public Rectangle(Rectangle r)
	{
		this(r.x, r.y, r.z, r.w);
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public Rectangle(float xStart, float yStart, float xEnd, float yEnd)
	{
		assert xStart < xEnd;
		assert yStart < yEnd;
		
		x = xStart;
		y = yStart;
		z = xEnd;
		w = yEnd;
		
	}
	
	@Override
	public Rectangle clone()
	{
		return new Rectangle(this);
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if (!(obj instanceof Rectangle))
		{
			return false;
		}
		
		Rectangle r = (Rectangle)obj;
		
		if (this.x != r.x)
		{
			return false;
		}
		
		if (this.y != r.y)
		{
			return false;
		}
		
		if (this.z != r.z)
		{
			return false;
		}
		
		if (this.w != r.w)
		{
			return false;
		}
		
		return true;
	}
	
	public boolean within(Vector point)
	{
		return MathHelper.bounds(point.get(MathConst.X), this.x, this.z) && MathHelper.bounds(point.get(MathConst.Y), this.y, this.w);
	}
	
	public float interpolateX(float f)
	{
		return MathHelper.interpolate(this.x, this.z, f);
	}
	
	public float interpolateY(float f)
	{
		return MathHelper.interpolate(this.y, this.w, f);
	}
	
	public Rectangle interpolate(Rectangle r)
	{
		return this.interpolate(r.x, r.y, r.z, r.w);
	}
	
	public Rectangle interpolate(float x, float y, float z, float w)
	{
		return new Rectangle(this.interpolateX(x), this.interpolateY(y), this.interpolateX(z), this.interpolateY(w));
	}
	
}
