
package com.elusivehawk.engine.render;

import com.elusivehawk.engine.core.CaelumEngine;
import com.elusivehawk.engine.core.EnumLogType;
import com.elusivehawk.engine.core.IContext;
import com.elusivehawk.engine.core.IThreadContext;
import com.elusivehawk.engine.util.ShutdownHelper;
import com.elusivehawk.engine.util.ThreadTimed;

/**
 * 
 * The primary thread for rendering the game's window.
 * 
 * @author Elusivehawk
 */
public class ThreadGameRender extends ThreadTimed implements IThreadContext
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
	public void handleException(Throwable e)
	{
		CaelumEngine.log().log(EnumLogType.ERROR, "Error caught during rendering: ", e);
		
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
	public double getMaxDelta()
	{
		return 0.5;
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
