
package com.elusivehawk.engine.physics;

import com.elusivehawk.engine.util.SyncList;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public interface IPhysicsScene
{
	public SyncList<ICollisionObject> getCollidables();
	
}
