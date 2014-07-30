
package com.elusivehawk.engine.render.three;

import com.elusivehawk.engine.math.Vector;
import com.elusivehawk.util.Internal;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
@Internal
public class ModelPoint
{
	public final Vector v, t, n;
	
	@SuppressWarnings("unqualified-field-access")
	public ModelPoint(Vector vtx, Vector tx, Vector norm)
	{
		v = vtx;
		t = tx;
		n = norm;
		
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if (!(obj instanceof ModelPoint))
		{
			return false;
		}
		
		ModelPoint p = (ModelPoint)obj;
		
		if (!this.v.equals(p.v))
		{
			return false;
		}
		
		if (!this.t.equals(p.t))
		{
			return false;
		}
		
		if (!this.n.equals(p.n))
		{
			return false;
		}
		
		return true;
	}
	
}
