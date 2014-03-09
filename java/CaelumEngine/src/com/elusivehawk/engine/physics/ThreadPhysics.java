
package com.elusivehawk.engine.physics;

import java.util.Iterator;
import com.elusivehawk.engine.core.GameState;
import com.elusivehawk.engine.core.IGameStateListener;
import com.elusivehawk.engine.util.SyncList;
import com.elusivehawk.engine.util.ThreadTimed;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public final class ThreadPhysics extends ThreadTimed implements IGameStateListener
{
	private IPhysicsScene scene;
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
		if (this.scene == null)
		{
			return;
		}
		
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
					
					collision = obj.getCollisionResult(obj0);
					
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
	
	@Override
	public synchronized void onGameStateSwitch(GameState gs)
	{
		this.scene = gs.getPhysicsScene();
		
	}
	
}
