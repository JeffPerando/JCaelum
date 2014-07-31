
package com.elusivehawk.engine.render;

import com.elusivehawk.engine.IContext;
import com.elusivehawk.engine.IThreadContext;
import com.elusivehawk.engine.ThreadCaelum;
import com.elusivehawk.util.ShutdownHelper;

/**
 * 
 * The primary thread for rendering the game's window.
 * 
 * @author Elusivehawk
 */
public class ThreadGameRender extends ThreadCaelum implements IThreadContext
{
	protected final RenderContext con;
	
	protected int fps = 30;
	protected IDisplay display = null;
	
	@SuppressWarnings("unqualified-field-access")
	public ThreadGameRender(RenderContext rcon)
	{
		con = rcon;
		
	}
	
	@Override
	public boolean initiate()
	{
		if (!super.initiate())
		{
			return false;
		}
		
		if (!this.con.initContext())
		{
			return false;
		}
		
		this.fps = this.con.getFPS();
		this.display = this.con.getDisplay();
		
		return true;
	}
	
	@Override
	public void update(double delta) throws Throwable
	{
		if (this.display.isCloseRequested())
		{
			this.con.onDisplayClosed(this.display);
			
			ShutdownHelper.exit(0);
			
		}
		
		if (this.con.drawScreen(delta))
		{
			this.display.updateDisplay();
			
		}
		
	}
	
	@Override
	public synchronized void setPaused(boolean pause)
	{
		super.setPaused(pause);
		this.con.setPaused(pause);
		
	}
	
	@Override
	public int getTargetUpdateCount()
	{
		return this.fps;
	}
	
	@Override
	public IContext getContext()
	{
		return this.con;
	}
	
}
