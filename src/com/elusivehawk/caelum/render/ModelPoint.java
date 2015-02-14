
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
	
	@SuppressWarnings("unqualified-field-access")
	public ModelPoint(Vector v, Vector t, Vector n)
	{
		vtx = v.isImmutable() ? v : (Vector)v.clone().setImmutable();
		tex = t == null ? null : t.isImmutable() ? t : (Vector)t.clone().setImmutable();
		norm = n == null ? null : n.isImmutable() ? n : (Vector)n.clone().setImmutable();
		
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
		
		if (this.tex != mp.tex && !this.tex.equals(mp.tex))
		{
			return false;
		}
		
		if (this.norm != mp.norm && !this.norm.equals(mp.norm))
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
		
		return ret;
	}
	
}
