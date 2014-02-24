
package com.elusivehawk.engine.physics;

import com.elusivehawk.engine.math.MathHelper;
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
		
		if (MathHelper.bounds(vec.get(Vector.X), this.minX, this.maxX) && 
				MathHelper.bounds(vec.get(Vector.Y), this.minY, this.maxY) &&
				MathHelper.bounds(vec.get(Vector.Z), this.minZ, this.maxZ))
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
		
		return new VectorF(MathHelper.clamp(vec.get(Vector.X), this.minX, this.maxX),
				MathHelper.clamp(vec.get(Vector.Y), this.minY, this.maxY),
				MathHelper.clamp(vec.get(Vector.Z), this.minZ, this.maxZ));
	}
	
}
