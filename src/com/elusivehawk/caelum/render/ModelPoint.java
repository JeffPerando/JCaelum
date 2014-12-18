
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
	public final int mat;
	
	@SuppressWarnings("unqualified-field-access")
	public ModelPoint(Vector v, Vector t, Vector n, int m)
	{
		vtx = v.clone().setImmutable();
		tex = t.clone().setImmutable();
		norm = n.clone().setImmutable();
		mat = m;
		
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
		
		if (this.mat != mp.mat)
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
		ret *= (31 + this.mat);
		
		return ret;
	}
	
}
