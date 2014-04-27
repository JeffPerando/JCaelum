/*
 * Java port of Bullet (c) 2008 Martin Dvorak <jezek2@advel.cz>
 *
 * Bullet Continuous Collision Detection and Physics Library
 * Copyright (c) 2003-2008 Erwin Coumans  http://www.bulletphysics.com/
 *
 * This software is provided 'as-is', without any express or implied warranty.
 * In no event will the authors be held liable for any damages arising from
 * the use of this software.
 * 
 * Permission is granted to anyone to use this software for any purpose, 
 * including commercial applications, and to alter it and redistribute it
 * freely, subject to the following restrictions:
 * 
 * 1. The origin of this software must not be misrepresented; you must not
 *    claim that you wrote the original software. If you use this software
 *    in a product, an acknowledgment in the product documentation would be
 *    appreciated but is not required.
 * 2. Altered source versions must be plainly marked as such, and must not be
 *    misrepresented as being the original software.
 * 3. This notice may not be removed or altered from any source distribution.
 */

package com.elusivehawk.engine.physics.jbullet.collision.broadphase;

import com.elusivehawk.engine.physics.jbullet.collision.dispatch.CollisionAlgorithmCreateFunc;
import com.elusivehawk.engine.physics.jbullet.collision.dispatch.CollisionObject;
import com.elusivehawk.engine.physics.jbullet.collision.dispatch.ManifoldResult;
import com.elusivehawk.engine.physics.jbullet.collision.narrowphase.PersistentManifold;
import com.elusivehawk.engine.physics.jbullet.util.ObjectArrayList;

/*
 * NOTICE: Edited by Elusivehawk
 */
/**
 * Collision algorithm for handling narrowphase or midphase collision detection
 * between two collision object types.
 * 
 * @author jezek2
 */
public abstract class CollisionAlgorithm
{
	//protected final BulletStack stack = BulletStack.get();
	
	// JAVA NOTE: added
	private CollisionAlgorithmCreateFunc createFunc;
	
	protected Dispatcher dispatcher;

	public void init(){}

	public void init(CollisionAlgorithmConstructionInfo ci)
	{
		this.dispatcher = ci.dispatcher1;
	}
	
	public abstract void destroy();

	public abstract void processCollision(CollisionObject body0, CollisionObject body1, DispatcherInfo dispatchInfo, ManifoldResult resultOut);

	public abstract float calculateTimeOfImpact(CollisionObject body0, CollisionObject body1, DispatcherInfo dispatchInfo, ManifoldResult resultOut);
	
	public abstract void getAllContactManifolds(ObjectArrayList<PersistentManifold> manifoldArray);
	
	public final void internalSetCreateFunc(CollisionAlgorithmCreateFunc func)
	{
		this.createFunc = func;
		
	}

	public final CollisionAlgorithmCreateFunc internalGetCreateFunc()
	{
		return this.createFunc;
	}
	
}
