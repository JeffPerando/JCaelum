
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
	
	@SuppressWarnings("unqualified-field-access")
	public ThreadGameLoop(Game g)
	{
		super("GameLoop");
		
		game = g;
		
	}
	
	@Override
	public void update(double delta) throws Throwable
	{
		/*if (!this.input.isEmpty())
		{
			this.input.forEach(((input) ->
			{
				try
				{
					input.update(delta);
					
				}
				catch (Throwable e)
				{
					this.handleException(e);
					ShutdownHelper.exit("INPUT-ERR");
					
				}
				
			}));
			
		}*/
		
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
