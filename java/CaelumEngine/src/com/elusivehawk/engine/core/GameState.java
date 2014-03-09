
package com.elusivehawk.engine.core;

import com.elusivehawk.engine.physics.IPhysicsScene;
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
	
	public abstract void updateGameState(Game game, double delta);
	
	public abstract void finish();
	
	public abstract IRenderHUB getRenderHUB();
	
	public abstract IPhysicsScene getPhysicsScene();
	
}
