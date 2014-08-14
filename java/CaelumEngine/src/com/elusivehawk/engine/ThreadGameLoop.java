
package com.elusivehawk.engine;

import java.util.Map;
import com.elusivehawk.engine.render.RenderContext;
import com.elusivehawk.util.Internal;
import com.google.common.collect.Maps;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
@Internal
public final class ThreadGameLoop extends ThreadCaelum implements IThreadContext
{
	private final Map<EnumInputType, Input> input = Maps.newHashMap();
	private final Game game;
	
	private RenderContext rcon = null;//Only used for single-threaded rendering.
	
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
		if (!super.initiate())
		{
			return false;
		}
		
		if (this.rcon != null && !this.rcon.initContext())
		{
			return false;
		}
		
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
		
		if (this.rcon != null)
		{
			this.rcon.update(delta);
			this.rcon.getDisplay().updateDisplay();
			
		}
		
	}
	
	@Override
	public int getTargetUpdateCount()
	{
		return this.game.getUpdateCount();
	}
	
	@Override
	public IContext getContext()
	{
		return this.rcon;
	}
	
	@Override
	public synchronized void setPaused(boolean pause)
	{
		super.setPaused(pause);
		
		this.game.setPaused(pause);
		
	}
	
	@Override
	public void onThreadStopped(boolean failure)
	{
		if (this.rcon != null)
		{
			this.rcon.cleanup();
			
		}
		
	}
	
	public void enableSingleThreadedRendering(RenderContext context)
	{
		if (this.rcon == null)
		{
			this.rcon = context;
			
		}
		
	}
	
}
