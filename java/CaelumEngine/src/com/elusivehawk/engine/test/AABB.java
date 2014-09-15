
package com.elusivehawk.engine.test;

import com.elusivehawk.engine.EnumEngineFeature;
import com.elusivehawk.util.math.IVecListener;
import com.elusivehawk.util.math.MathHelper;
import com.elusivehawk.util.math.Vector;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
@IntendedFor(EnumEngineFeature.PHYSICS)
public class AABB extends Shape implements IVecListener
{
	private final Vector max, min, size;
	private final Vector maxOff = new Vector(), minOff = new Vector();
	
	@SuppressWarnings("unqualified-field-access")
	public AABB(Vector position, Vector cubeSize)
	{
		super(position);
		
		assert cubeSize != null;
		
		size = cubeSize;
		
		Vector halfSize = cubeSize.clone().divAll(2);
		
		max = position.add(maxOff.set(halfSize), false);
		min = position.sub(minOff.set(halfSize), false);
		
		maxOff.setImmutable();
		minOff.setImmutable();
		
		pos.addListener(this);
		
	}
	
	public AABB(AABB aabb)
	{
		this(aabb.getPosition().clone(), aabb.size.clone());
		
	}
	
	@Override
	public boolean collides(Vector vec)
	{
		if (vec == null)
		{
			return false;
		}
		
		for (int c = 0; c < 3; c++)
		{
			if (!MathHelper.bounds(vec.get(c), this.min.get(c), this.max.get(c)))
			{
				return false;
			}
			
		}
		
		return true;
	}
	
	@Override
	public Vector createNearestPoint(Vector otherPos)
	{
		Vector ret = new Vector();
		
		for (int c = 0; c < 3; c++)
		{
			ret.set(c, MathHelper.clamp(otherPos.get(c), this.min.get(c), this.max.get(c)));
			
		}
		
		return ret;
	}
	
	@Override
	public AABB clone()
	{
		return new AABB(this);
	}
	
	@Override
	public void onVecChanged(Vector vec)
	{
		if (this.pos.isDirty())
		{
			this.pos.add(this.maxOff, this.max);
			this.pos.sub(this.minOff, this.min);
			
		}
		
	}
	
}
