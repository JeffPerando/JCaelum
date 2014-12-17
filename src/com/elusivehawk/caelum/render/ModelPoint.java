
package com.elusivehawk.caelum.render;

import com.elusivehawk.util.MakeStruct;
import com.elusivehawk.util.math.Vector;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
@MakeStruct
public class ModelPoint
{
	public final Vector vtx, tex, norm;
	public final int material;
	
	@SuppressWarnings("unqualified-field-access")
	public ModelPoint(float x, float y, float z, float u, float v, float nx, float ny, float nz, int mat)
	{
		vtx = new Vector(x, y, z);
		tex = new Vector(u, v);
		norm = new Vector(nx, ny, nz);
		material = mat;
		
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if (!(obj instanceof ModelPoint))
		{
			return false;
		}
		
		ModelPoint mp = (ModelPoint)obj;
		
		if (!this.vtx.equals(mp.vtx))
		{
			return false;
		}
		
		if (!this.tex.equals(mp.tex))
		{
			return false;
		}
		
		if (!this.norm.equals(mp.norm))
		{
			return false;
		}
		
		if (this.material != mp.material)
		{
			return false;
		}
		
		return true;
	}
	
	@Override
	public int hashCode()
	{
		int ret = 31;
		
		ret *= (31 + this.vtx.hashCode());
		ret *= (31 + this.tex.hashCode());
		ret *= (31 + this.norm.hashCode());
		ret *= (31 + this.material);
		
		return ret;
	}
	
}
