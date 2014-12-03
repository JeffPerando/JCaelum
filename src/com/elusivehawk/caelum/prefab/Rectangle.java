
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
	
	public boolean within(Vector point)
	{
		return MathHelper.bounds(point.get(MathConst.X), this.x, this.z) && MathHelper.bounds(point.get(MathConst.Y), this.y, this.w);
	}
	
	public float interpolateX(float f)
	{
		return MathHelper.interpolate(this.x, f);
	}
	
	public float interpolateY(float f)
	{
		return MathHelper.interpolate(this.y, f);
	}
	
	public float interpolateZ(float f)
	{
		return MathHelper.interpolate(this.z, f);
	}
	
	public float interpolateW(float f)
	{
		return MathHelper.interpolate(this.w, f);
	}
	
	public Rectangle interpolate(Rectangle r)
	{
		return new Rectangle(this.interpolateX(r.x), this.interpolateY(r.y), this.interpolateZ(r.z), this.interpolateW(r.w));
	}
	
}
