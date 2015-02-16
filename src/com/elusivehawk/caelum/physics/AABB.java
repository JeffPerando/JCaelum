
package com.elusivehawk.caelum.physics;

import com.elusivehawk.caelum.Experimental;
import com.elusivehawk.util.math.MathHelper;
import com.elusivehawk.util.math.VectorF;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
@Experimental
public class AABB extends Shape
{
	private final VectorF max, min, size;
	private final VectorF maxOff = new VectorF(), minOff = new VectorF();
	
	@SuppressWarnings("unqualified-field-access")
	public AABB(VectorF position, VectorF cubeSize)
	{
		super(position);
		
		assert cubeSize != null;
		
		size = (VectorF)cubeSize.setImmutable();
		
		VectorF halfSize = (VectorF)cubeSize.clone().divAll(2);
		
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
	public boolean collides(VectorF vec)
	{
		if (vec == null)
		{
			return false;
		}
		
		return MathHelper.bounds(vec, this.min, this.max);
	}
	
	@Override
	public VectorF createNearestPoint(VectorF otherPos)
	{
		return MathHelper.clamp(otherPos, this.min, this.max);
	}
	
	@Override
	public void onVecChanged(VectorF vec)
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
	
	public VectorF getSize()
	{
		return this.size;
	}
	
}
