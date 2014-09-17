
package com.elusivehawk.engine.physics;

import com.elusivehawk.engine.Experimental;
import com.elusivehawk.util.IUpdatable;
import com.elusivehawk.util.math.IVecListener;
import com.elusivehawk.util.math.Vector;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
@Experimental
public abstract class Shape implements IVecListener, IUpdatable
{
	protected final Vector pos = new Vector();
	protected volatile boolean moveable = true;
	
	@SuppressWarnings("unqualified-field-access")
	public Shape(Vector position)
	{
		pos.set(position);
		pos.addListener(this);
		
	}
	
	@Override
	public void update(double delta, Object... extra)
	{
		if (!this.canMove())
		{
			
		}
		
	}
	
	@Override
	public void onVecChanged(Vector vec){}
	
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
