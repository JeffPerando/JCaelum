
package com.elusivehawk.engine.physics;

import java.util.Iterator;
import com.elusivehawk.engine.util.SyncList;
import com.elusivehawk.engine.util.ThreadTimed;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public final class ThreadPhysics extends ThreadTimed
{
	private final IPhysicsScene scene;
	private final int updateCount;
	
	@SuppressWarnings("unqualified-field-access")
	public ThreadPhysics(IPhysicsScene phyScene, int ups)
	{
		scene = phyScene;
		updateCount = ups;
		
	}
	
	@Override
	public void update(double delta) throws Throwable
	{
		SyncList<ICollisionObject> list = this.scene.getCollidables();
		
		Iterator<ICollisionObject> itr = list.iterator();
		Iterator<ICollisionObject> itr0;
		
		ICollisionObject obj, obj0;
		
		ICollisionListener collision;
		
		while (itr.hasNext())
		{
			obj = itr.next();
			
			if (obj.isInactive())
			{
				continue;
			}
			
			obj.updatePositioning(delta);
			
			itr0 = list.iterator();
			
			while (itr0.hasNext())
			{
				obj0 = itr0.next();
				
				if (obj != obj0)
				{
					if (obj0.isNoclipping())
					{
						continue;
					}
					
					collision = obj.canCollide(obj0);
					
					if (collision != null)
					{
						collision.onCollision(obj0);
						
					}
					
				}
				
			}
			
		}
		
	}
	
	@Override
	public int getTargetUpdateCount()
	{
		return this.updateCount;
	}
	
	@Override
	public double getMaxDelta()
	{
		return 0.5;
	}
	
}
