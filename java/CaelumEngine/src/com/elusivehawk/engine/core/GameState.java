
package com.elusivehawk.engine.core;

import com.elusivehawk.engine.physics.IPhysicsSimulator;
import com.elusivehawk.engine.render.RenderContext;
import com.elusivehawk.engine.render.old.IRenderHUB;
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
	
	@Deprecated
	public abstract IRenderHUB getRenderHUB();
	
	public abstract void render(RenderContext rcon, double delta);
	
	public abstract IPhysicsSimulator getPhysicsSimulator();
	
}
