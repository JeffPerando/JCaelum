
package com.elusivehawk.engine.physics;

import com.elusivehawk.engine.math.Vector;
import com.elusivehawk.engine.math.VectorF;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class CollisionBox extends CollisionObject
{
	protected float maxX, maxY, maxZ;
	protected float minX, minY, minZ;
	
	public CollisionBox(Vector<Float> origin, float f)
	{
		this(origin, null, f, f, f);
		
	}
	
	public CollisionBox(Vector<Float> origin, ICollisionListener lis, float f)
	{
		this(origin, lis, f, f, f);
		
	}
	
	public CollisionBox(Vector<Float> origin, float x, float y, float z)
	{
		this(origin, null, x, y, z);
		
	}
	
	public CollisionBox(Vector<Float> origin, ICollisionListener lis, float x, float y, float z)
	{
		this(origin, lis, -x, -y, -z, x, y, z);
		
	}
	
	public CollisionBox(Vector<Float> origin, float minX, float minY, float minZ, float maxX, float maxY, float maxZ)
	{
		this(origin, null, minX, minY, minZ, maxX, maxY, maxZ);
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public CollisionBox(Vector<Float> origin, ICollisionListener lis, float Xmin, float Ymin, float Zmin, float Xmax, float Ymax, float Zmax)
	{
		super(origin, lis);
		
		minX = Xmin;
		minY = Ymin;
		minZ = Zmin;
		maxX = Xmax;
		maxY = Ymax;
		maxZ = Zmax;
		
	}
	
	@Override
	public void expand(float f)
	{
		this.expand(f, f, f);
		
	}
	
	@Override
	public void expand(float x, float y, float z)
	{
		this.maxX *= x;
		this.maxY *= y;
		this.maxZ *= z;
		this.minX *= -x;
		this.minY *= -y;
		this.minZ *= -z;
		
	}
	
	@Override
	public ICollisionListener getCollisionResult(ICollisionObject obj)
	{
		Vector<Float> vec = obj.createPointForCollision(this);
		
		float x = vec.get(Vector.X),
		y = vec.get(Vector.Y),
		z = vec.get(Vector.Z);
		
		if ((x > this.maxX && x < this.minX) && (y > this.maxY && y < this.minY) && (z > this.maxZ && z < this.minZ))
		{
			ICollisionListener ret = super.getCollisionResult(obj);
			
			return ret == null ? this.listener : ret;
		}
		
		return null;
	}
	
	@Override
	public Vector<Float> createPointForCollision(ICollisionObject obj)
	{
		Vector<Float> vec = obj.getCentralPosition();
		
		return new VectorF(3, Math.min(Math.max(this.minX, vec.get(Vector.X)), this.maxX),
				Math.min(Math.max(this.minY, vec.get(Vector.Y)), this.maxY),
				Math.min(Math.max(this.minZ, vec.get(Vector.Z)), this.maxZ));
	}
	
}
