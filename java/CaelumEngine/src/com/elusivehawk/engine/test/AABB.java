
package com.elusivehawk.engine.test;

import com.elusivehawk.engine.EnumEngineFeature;
import com.elusivehawk.util.math.MathHelper;
import com.elusivehawk.util.math.Vector;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
@IntendedFor(EnumEngineFeature.PHYSICS)
public class AABB extends Shape
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
		
		return MathHelper.bounds(vec, this.min, this.max);
	}
	
	@Override
	public Vector createNearestPoint(Vector otherPos)
	{
		return MathHelper.clamp(otherPos, this.min, this.max);
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
	
	@Override
	public AABB clone()
	{
		return new AABB(this);
	}
	
}
