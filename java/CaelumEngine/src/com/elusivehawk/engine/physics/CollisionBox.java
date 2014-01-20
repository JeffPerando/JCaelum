
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
	public ICollisionListener canCollide(ICollisionObject obj)
	{
		ICollisionListener ret = null;
		
		if (obj.canCollide(this.createPoint(obj.getCentralPosition())))
		{
			if (this.children.isEmpty())
			{
				ret = this.listener;
				
			}
			else
			{
				for (ICollisionObject ch : this.children)
				{
					ret = ch.canCollide(obj);
					
					if (ret != null)
					{
						break;
					}
					
				}
				
			}
			
		}
		
		return ret;
	}
	
	@Override
	public boolean canCollide(Vector<Float> vec)
	{
		float x = vec.get(Vector.X),
		y = vec.get(Vector.Y),
		z = vec.get(Vector.Z);
		
		return (x > this.maxX && x < this.minX)
				&& (y > this.maxY && y < this.minY)
				&& (z > this.maxZ && z < this.minZ);
	}
	
	protected Vector<Float> createPoint(Vector<Float> o)
	{
		return new VectorF(3, Math.min(Math.max(this.minX, o.get(Vector.X)), this.maxX),
				Math.min(Math.max(this.minY, o.get(Vector.Y)), this.maxY),
				Math.min(Math.max(this.minZ, o.get(Vector.Z)), this.maxZ));
	}
	
}
