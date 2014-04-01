
package com.elusivehawk.engine.core;

import com.elusivehawk.engine.physics.IPhysicsSimulator;
import com.elusivehawk.engine.render.IRenderHUB;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public abstract class GameState
{
	public abstract void initiate();
	
	public abstract void updateGameState(Game game, double delta) throws Throwable;
	
	public abstract void finish();
	
	@SuppressWarnings("static-method")
	public IRenderHUB getRenderHUB()
	{
		return null;
	}
	
	@Deprecated
	@SuppressWarnings("static-method")
	public IPhysicsSimulator getPhysicsScene()
	{
		return null;
	}
	
}
