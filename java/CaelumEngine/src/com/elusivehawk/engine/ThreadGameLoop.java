
package com.elusivehawk.engine;

import java.util.Map;
import com.elusivehawk.util.Internal;
import com.google.common.collect.Maps;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
@Internal
public final class ThreadGameLoop extends ThreadCaelum
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
