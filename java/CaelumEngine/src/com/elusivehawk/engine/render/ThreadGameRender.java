
package com.elusivehawk.engine.render;

import com.elusivehawk.engine.CaelumEngine;
import com.elusivehawk.engine.IContext;
import com.elusivehawk.engine.IThreadContext;
import com.elusivehawk.engine.ThreadCaelum;
import com.elusivehawk.util.Internal;
import com.elusivehawk.util.ShutdownHelper;

/**
 * 
 * The primary thread for rendering the game's window.
 * 
 * @author Elusivehawk
 */
@Internal
public class ThreadGameRender extends ThreadCaelum implements IThreadContext
{
	protected final RenderContext rcon;
	
	@SuppressWarnings("unqualified-field-access")
	public ThreadGameRender(RenderContext con)
	{
		super("Thread-Render");
		
		rcon = con;
		
	}
	
	@Override
	public boolean initiate()
	{
		if (!CaelumEngine.display().makeCurrent())
		{
			return false;
		}
		
		if (!this.rcon.initContext())
		{
			return false;
		}
		
		return super.initiate();
	}
	
	@Override
	public void update(double delta) throws Throwable
	{
		IDisplay display = CaelumEngine.display();
		
		if (display.isCloseRequested())
		{
			this.rcon.onDisplayClosed(display);
			
			ShutdownHelper.exit(0);
			
			return;
		}
		
		this.rcon.update(delta);
		
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
