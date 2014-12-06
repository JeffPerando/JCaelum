
package com.elusivehawk.caelum;

import com.elusivehawk.util.IPausable;
import com.elusivehawk.util.Version;

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
	public String getFormattedName()
	{
		return this.getGameVersion() == null ? this.name : String.format("%s %s", this.name, this.getGameVersion());
	}
	
	//XXX Optional/technical methods
	
	protected void preInit(){}
	
	public int getUpdateCount()
	{
		return 30;
	}
	
	//XXX Abstract methods
	
	public abstract Version getGameVersion();
	
	public abstract DisplaySettings getDisplaySettings();
	
}
