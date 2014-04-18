
package com.elusivehawk.engine.render;

import com.elusivehawk.engine.core.IContext;
import com.elusivehawk.engine.core.IThreadContext;
import com.elusivehawk.engine.core.ThreadCaelum;
import com.elusivehawk.engine.util.ShutdownHelper;

/**
 * 
 * The primary thread for rendering the game's window.
 * 
 * @author Elusivehawk
 */
public class ThreadGameRender extends ThreadCaelum implements IThreadContext
{
	protected final RenderSystem sys;
	
	protected int fps = 30;
	protected IDisplay display = null;
	
	@SuppressWarnings("unqualified-field-access")
	public ThreadGameRender(RenderSystem rsys)
	{
		sys = rsys;
		
	}
	
	@Override
	public boolean initiate()
	{
		super.initiate();
		
		if (this.sys.initiate())
		{
			this.fps = this.sys.getFPS();
			this.display = this.sys.getDisplay();
			
			return true;
		}
		
		return false;
	}
	
	@Override
	public void update(double delta) throws Throwable
	{
		if (this.display.isCloseRequested())
		{
			this.sys.onDisplayClosed(this.display);
			
			ShutdownHelper.exit(0);
			
		}
		
		if (this.sys.drawScreen(delta))
		{
			this.display.updateDisplay();
			
		}
		
	}
	
	@Override
	public synchronized void setPaused(boolean pause)
	{
		super.setPaused(pause);
		this.sys.setPaused(pause);
		
	}
	
	@Override
	public int getTargetUpdateCount()
	{
		return this.fps;
	}
	
	@Override
	public IContext getContext()
	{
		return this.sys.getContext();
	}
	
	public synchronized void flipScreen(boolean flip)
	{
		this.sys.onScreenFlipped(flip);
		
	}
	
}
