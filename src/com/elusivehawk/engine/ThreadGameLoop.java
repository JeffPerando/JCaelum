
package com.elusivehawk.engine;

import java.util.Iterator;
import java.util.List;
import com.elusivehawk.engine.input.Input;
import com.elusivehawk.util.EnumLogType;
import com.elusivehawk.util.Internal;
import com.elusivehawk.util.Logger;
import com.elusivehawk.util.ShutdownHelper;
import com.elusivehawk.util.concurrent.ThreadTimed;
import com.google.common.collect.Lists;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
@Internal
public final class ThreadGameLoop extends ThreadTimed
{
	private final List<Input> input = Lists.newArrayList();
	private final Game game;
	
	@SuppressWarnings("unqualified-field-access")
	public ThreadGameLoop(List<Input> inputList, Game g)
	{
		super("GameLoop");
		
		game = g;
		
		if (inputList != null)
		{
			input.addAll(inputList);
			
		}
		
	}
	
	@Override
	public boolean initiate()
	{
		super.initiate();
		
		CaelumEngine.waitForDisplay();//Guarantees that the display has been created.
		
		Iterator<Input> itr = this.input.iterator();
		Input in;
		
		while (itr.hasNext())
		{
			in = itr.next();
			boolean loaded = false;
			
			try
			{
				loaded = in.initiateInput();
				
			}
			catch (Exception e)
			{
				this.handleException(e);
				
			}
			
			if (loaded)
			{
				Logger.log().log(EnumLogType.VERBOSE, "Input successfully loaded: %s", in.getClass().getSimpleName());
				
			}
			else
			{
				Logger.log().log(EnumLogType.WARN, "Input could not be loaded: %s", in.getClass().getSimpleName());
				this.input.remove(in);
				
			}
			
		}
		
		return true;
	}
	
	@Override
	public void update(double delta, Object... extra) throws Throwable
	{
		if (!this.input.isEmpty())
		{
			this.input.forEach(((input) ->
			{
				try
				{
					input.update(delta, extra);
					
				}
				catch (Throwable e)
				{
					this.handleException(e);
					ShutdownHelper.exit("INPUT-ERR");
					
				}
				
			}));
			
		}
		
		this.game.update(delta, extra);
		
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
	
	@Override
	public void onThreadStopped(boolean failure)
	{
		if (!this.input.isEmpty())
		{
			this.input.forEach(((input) ->
			{
				try
				{
					input.close();
					
				}
				catch (Exception e)
				{
					e.printStackTrace();
					
				}
				
			}));
			
		}
	}
	
}
