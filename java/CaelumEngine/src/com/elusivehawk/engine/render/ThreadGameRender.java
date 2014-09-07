
package com.elusivehawk.engine.render;

import com.elusivehawk.engine.IContext;
import com.elusivehawk.engine.IThreadContext;
import com.elusivehawk.util.Internal;
import com.elusivehawk.util.ShutdownHelper;
import com.elusivehawk.util.concurrent.ThreadTimed;

/**
 * 
 * The primary thread for rendering the game's window.
 * 
 * @author Elusivehawk
 */
@Internal
public class ThreadGameRender extends ThreadTimed implements IThreadContext
{
	protected final RenderContext rcon;
	
	@SuppressWarnings("unqualified-field-access")
	public ThreadGameRender(RenderContext con)
	{
		super("Renderer");
		
		rcon = con;
		
	}
	
	@Override
	public boolean initiate()
	{
		if (!this.rcon.initContext())
		{
			return false;
		}
		
		return super.initiate();
	}
	
	@Override
	public void update(double delta, Object... extra) throws Throwable
	{
		if (this.isPaused())
		{
			return;
		}
		
		IDisplay display = this.rcon.getDisplay();
		
		if (display.isCloseRequested())
		{
			this.rcon.onDisplayClosed(display);
			
			ShutdownHelper.exit(0);
			
			return;
		}
		
		this.rcon.update(delta, extra);
		
		display.updateDisplay();
		
	}
	
	@Override
	public synchronized void setPaused(boolean pause)
	{
		super.setPaused(pause);
		this.rcon.setPaused(pause);
		
	}
	
	@Override
	public int getTargetUpdateCount()
	{
		return this.rcon.getFPS();
	}
	
	@Override
	public IContext getContext()
	{
		return this.rcon;
	}
	
}
