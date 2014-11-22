
package com.elusivehawk.caelum.render;

import com.elusivehawk.caelum.DisplayManager;
import com.elusivehawk.util.Internal;
import com.elusivehawk.util.concurrent.ThreadTimed;

/**
 * 
 * The primary thread for rendering the game's window.
 * 
 * @author Elusivehawk
 */
@Internal
public final class ThreadGameRender extends ThreadTimed
{
	private final DisplayManager displays;
	
	private int fps = 30;
	
	@SuppressWarnings("unqualified-field-access")
	public ThreadGameRender(DisplayManager dmgr)
	{
		super("Renderer");
		
		assert dmgr != null;
		
		displays = dmgr;
		
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
	public int getTargetUpdateCount()
	{
		return this.fps;
	}
	
	@Override
	public void onThreadStopped(boolean failure)
	{
		this.displays.close();
		
	}
	
	public synchronized void setFPS(int i)
	{
		assert i > 0;
		
		this.fps = i;
		
	}
	
}
