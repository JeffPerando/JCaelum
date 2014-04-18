
package com.elusivehawk.engine.core;

import java.util.Map;
import com.elusivehawk.engine.util.ThreadTimed;
import com.google.common.collect.Maps;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public final class ThreadGameLoop extends ThreadTimed
{
	private final Map<EnumInputType, Input> input = Maps.newHashMap();
	private final Game game;
	
	@SuppressWarnings("unqualified-field-access")
	public ThreadGameLoop(Map<EnumInputType, Input> inputMap, Game g)
	{
		game = g;
		
		if (inputMap != null)
		{
			input.putAll(inputMap);
			
		}
		
	}
	
	@Override
	public boolean initiate()
	{
		return true;
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
	public double getMaxDelta()
	{
		return 0.5;
	}
	
	@Override
	public synchronized void setPaused(boolean pause)
	{
		super.setPaused(pause);
		this.game.setPaused(pause);
		
	}
	
}
