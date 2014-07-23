
package com.elusivehawk.engine.core;

import com.elusivehawk.engine.physics.IPhysicsSimulator;
import com.elusivehawk.engine.render.IRenderHUB;
import com.elusivehawk.util.IUpdatable;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public abstract class GameState implements IUpdatable
{
	public abstract void initiate();
	
	public abstract void finish();
	
	@SuppressWarnings("static-method")
	public IRenderHUB getRenderHUB()
	{
		return null;
	}
	
	@SuppressWarnings("static-method")
	public IPhysicsSimulator getPhysicsSimulator()
	{
		return null;
	}
	
}
