
package com.elusivehawk.caelum.physics;

import com.elusivehawk.caelum.Experimental;
import com.elusivehawk.util.IUpdatable;
import com.elusivehawk.util.math.VectorF;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
@Experimental
public abstract class Shape implements VectorF.Listener, IUpdatable
{
	protected final VectorF pos = new VectorF();
	protected volatile boolean moveable = true;
	
	@SuppressWarnings("unqualified-field-access")
	public Shape(VectorF position)
	{
		pos.set(position);
		pos.addListener(this);
		
	}
	
	@Override
	public void update(double delta)
	{
		if (!this.canMove())
		{
			
		}
		
	}
	
	@Override
	public void onVecChanged(VectorF vec){}
	
	public VectorF getPosition()
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
	
	public abstract boolean collides(VectorF vec);
	
	public abstract VectorF createNearestPoint(VectorF otherPos);
	
}
