
package com.elusivehawk.engine.physics;

import com.elusivehawk.engine.math.MathHelper;
import com.elusivehawk.engine.math.Vector;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class CollisionSphere extends CollisionObject
{
	protected float radius;
	
	@SuppressWarnings("unqualified-field-access")
	public CollisionSphere(Vector<Float> origin, ICollisionListener lis, float r)
	{
		super(origin, lis);
		
		radius = r;
		
	}
	
	@Override
	public void expand(float f)
	{
		this.radius *= f;
		
	}
	
	@Override
	public void expand(float x, float y, float z)
	{
		this.expand(Math.max(Math.max(x, y), z));
		
	}
	
	@Override
	public ICollisionListener getCollisionResult(ICollisionObject obj)
	{
		if (MathHelper.dist(this.getCentralPosition(), obj.createPointForCollision(this)) <= this.radius)
		{
			ICollisionListener ret = super.getCollisionResult(obj);
			
			return ret == null ? this.listener : ret;
		}
		
		return null;
	}
	
	@Override
	public Vector<Float> createPointForCollision(ICollisionObject obj)
	{
		// TODO Auto-generated method stub
		return null;
	}
	
}
