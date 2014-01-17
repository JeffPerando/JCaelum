
package com.elusivehawk.engine.core;

import com.elusivehawk.engine.util.Buffer;
import com.elusivehawk.engine.util.ThreadTimed;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public final class ThreadGameLoop extends ThreadTimed
{
	private final IGame game;
	private final Buffer<String> arguments;
	
	@SuppressWarnings("unqualified-field-access")
	public ThreadGameLoop(IGame g, Buffer<String> args)
	{
		game = g;
		arguments = args;
		
	}
	
	@Override
	public boolean initiate()
	{
		return this.game.initiate(this.arguments);
	}
	
	@Override
	public void update(double delta) throws Throwable
	{
		this.game.update(delta);
		
	}
	
	@Override
	public int getTargetUpdateCount()
	{
		return this.game.getUpdateCount();
	}
	
	@Override
	public double getMaxDelta()
	{
		return 0.5;
	}
	
}
