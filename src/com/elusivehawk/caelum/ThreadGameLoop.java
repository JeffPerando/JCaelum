
package com.elusivehawk.caelum;

import com.elusivehawk.util.Internal;
import com.elusivehawk.util.concurrent.ThreadTimed;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
@Internal
public final class ThreadGameLoop extends ThreadTimed
{
	private final Game game;
	private final DisplayManager displays;
	
	@SuppressWarnings("unqualified-field-access")
	public ThreadGameLoop(Game g, DisplayManager dmgr)
	{
		super("GameLoop");
		
		assert g != null;
		assert dmgr != null;
		
		game = g;
		displays = dmgr;
		
	}
	
	@Override
	public void update(double delta) throws Throwable
	{
		this.displays.sendInputEvents(delta);
		
		try
		{
			this.game.update(delta);
			
		}
		catch (Throwable e)
		{
			throw new GameTickException(e);
		}
		
	}
	
	@Override
	public int getTargetUpdateCount()
	{
		return this.game.getUpdateCount();
	}
	
	@Override
	public synchronized void setPaused(boolean pause)
	{
		super.setPaused(pause);
		
		this.game.setPaused(pause);
		
	}
	
}
