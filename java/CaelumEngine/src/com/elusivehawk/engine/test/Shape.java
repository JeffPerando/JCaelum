
package com.elusivehawk.engine.test;

import com.elusivehawk.engine.EnumEngineFeature;
import com.elusivehawk.util.math.Vector;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
@IntendedFor(EnumEngineFeature.PHYSICS)
public abstract class Shape
{
	protected final Vector pos = new Vector();
	protected volatile boolean moveable = true;
	
	public Shape(Vector position)
	{
		this.pos.set(position);
		
	}
	
	public Vector getPosition()
	{
		return this.pos;
	}
	
	public boolean canMove()
	{
		return this.moveable;
	}
	
	public Shape setMoveable(boolean b)
	{
		this.moveable = b;
		
		return this;
	}
	
	public boolean collides(Shape shape)
	{
		return this.collides(shape.createNearestPoint(this.getPosition()));
	}
	
	public abstract boolean collides(Vector vec);
	
	public abstract Vector createNearestPoint(Vector otherPos);
	
}
