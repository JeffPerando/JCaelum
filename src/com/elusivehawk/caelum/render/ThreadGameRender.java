
package com.elusivehawk.caelum.render;

import com.elusivehawk.caelum.DisplayManager;
import com.elusivehawk.util.Internal;
import com.elusivehawk.util.Logger;
import com.elusivehawk.util.concurrent.ThreadTimed;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
@Internal
public final class ThreadGameRender extends ThreadTimed
{
	private final DisplayManager displays;
	private final int fps;
	
	@SuppressWarnings("unqualified-field-access")
	public ThreadGameRender(DisplayManager dmgr, int i)
	{
		super("Renderer");
		
		assert dmgr != null;
		assert i > 0;
		
		displays = dmgr;
		fps = i;
		
	}
	
	@Override
	public void update(double delta) throws Throwable
	{
		if (this.isPaused())
		{
			return;
		}
		
		this.displays.update(delta);
		
	}
	
	@Override
	public boolean doPostUpdate()
	{
		return true;
	}
	
	@Override
	public void postUpdate(double delta) throws Throwable
	{
		this.displays.updateInput(delta);
		
	}
	
	@Override
	public int getTargetUpdateCount()
	{
		return this.fps;
	}
	
	@Override
	public void onThreadStopped(boolean failure)
	{
		this.displays.close();
		
	}
	
	@Override
	public void handleException(Throwable e)
	{
		Logger.err("Error caught during render phase", e);
		
	}
	
}
