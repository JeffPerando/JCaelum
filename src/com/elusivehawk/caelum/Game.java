
package com.elusivehawk.caelum;

import com.elusivehawk.util.IPausable;

/**
 * 
 * Primary game class.
 * 
 * @author Elusivehawk
 */
public abstract class Game extends AbstractGameComponent implements IPausable
{
	private boolean paused = false;
	
	protected Game(String title)
	{
		super(title);
		
	}
	
	@Override
	public boolean isPaused()
	{
		return this.paused;
	}
	
	@Override
	public void setPaused(boolean p)
	{
		this.paused = p;
		
	}
	
	//XXX Overridden methods
	
	@Override
	public String toString()
	{
		return this.name;
	}
	
	//XXX Optional/technical methods
	
	public void preInit(){}
	
	public int getUpdateCount()
	{
		return 30;
	}
	
	public boolean isGameHeadless()
	{
		return false;
	}
	
	//XXX Abstract methods
	
	public abstract DisplaySettings getDisplaySettings();
	
}
