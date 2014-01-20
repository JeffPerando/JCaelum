
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
	public ICollisionListener canCollide(ICollisionObject obj)
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public boolean canCollide(Vector<Float> vec)
	{
		return MathHelper.dist(this.getCentralPosition(), vec) <= this.radius;
	}
	
}
