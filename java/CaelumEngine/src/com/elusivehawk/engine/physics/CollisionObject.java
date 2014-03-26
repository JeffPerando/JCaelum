
package com.elusivehawk.engine.physics;

import java.util.ArrayList;
import java.util.List;
import com.elusivehawk.engine.math.Vector;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public abstract class CollisionObject implements ICollisionObject
{
	protected boolean inactive = false;
	protected Vector pos;
	protected ICollisionListener listener;
	protected List<ICollisionObject> children = new ArrayList<ICollisionObject>();
	
	@SuppressWarnings("unqualified-field-access")
	protected CollisionObject(Vector origin, ICollisionListener lis)
	{
		pos = origin;
		listener = lis;
		
	}
	
	@Override
	public void updatePositioning(double delta){}
	
	@Override
	public boolean isInactive()
	{
		return this.inactive;
	}

	@Override
	public void setInactive(boolean b)
	{
		this.inactive = b;
		
	}
	
	@Override
	public Vector getCentralPosition()
	{
		return this.pos;
	}
	
	@Override
	public boolean isNoclipping()
	{
		return false;
	}
	
	@Override
	public void addChild(ICollisionObject obj)
	{
		this.children.add(obj);
		
	}
	
	@Override
	public ICollisionListener getCollisionResult(ICollisionObject obj)
	{
		ICollisionListener ret = null;
		
		if (!this.children.isEmpty())
		{
			for (ICollisionObject col : this.children)
			{
				ret = col.getCollisionResult(obj);
				
				if (ret != null)
				{
					break;
				}
				
			}
			
		}
		
		return ret;
	}
	
}
