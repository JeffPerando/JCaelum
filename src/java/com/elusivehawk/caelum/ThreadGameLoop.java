
package com.elusivehawk.caelum;

import com.elusivehawk.caelum.render.WindowManager;
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
	private final WindowManager windows;
	
	@SuppressWarnings("unqualified-field-access")
	public ThreadGameLoop(Game g, WindowManager wmgr)
	{
		super("GameLoop");
		
		assert g != null;
		assert wmgr != null;
		
		game = g;
		windows = wmgr;
		
	}
	
	@Override
	public void update(double delta) throws Throwable
	{
		this.windows.sendInputEvents(delta);
		
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
