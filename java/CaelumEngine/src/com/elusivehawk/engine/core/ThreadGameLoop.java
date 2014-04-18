
package com.elusivehawk.engine.core;

import java.util.Map;
import com.google.common.collect.Maps;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public final class ThreadGameLoop extends ThreadCaelum
{
	private final Map<EnumInputType, Input> input = Maps.newHashMap();
	private final Game game;
	private final GameArguments args;
	
	@SuppressWarnings("unqualified-field-access")
	public ThreadGameLoop(Map<EnumInputType, Input> inputMap, Game g, GameArguments gargs)
	{
		game = g;
		args = gargs;
		
		if (inputMap != null)
		{
			input.putAll(inputMap);
			
		}
		
	}
	
	@Override
	public boolean initiate()
	{
		super.initiate();
		
		return this.game.initiateGame(this.args);
	}
	
	@Override
	public void update(double delta) throws Throwable
	{
		if (!this.input.isEmpty())
		{
			for (Input in : this.input.values())
			{
				if (in == null)
				{
					continue;
				}
				
				in.updateInput();
				
			}
			
		}
		
		this.game.update(delta);
		
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
