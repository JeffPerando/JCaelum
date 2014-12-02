
package com.elusivehawk.caelum;

import java.util.List;
import com.elusivehawk.caelum.assets.AssetManager;
import com.elusivehawk.caelum.physics.IPhysicsSimulator;
import com.elusivehawk.caelum.prefab.GameState;
import com.elusivehawk.caelum.render.RenderContext;
import com.elusivehawk.util.IPausable;
import com.elusivehawk.util.Version;
import com.google.common.collect.Lists;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
@SuppressWarnings("unused")
public abstract class Game extends AbstractGameComponent implements IPausable
{
	private boolean initiated = false, paused = false;
	
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
	
	protected void preInit(GameArguments args){}
	
	public int getUpdateCount()
	{
		return 30;
	}
	
	//XXX Abstract methods
	
	public abstract Version getGameVersion();
	
	public abstract DisplaySettings getDisplaySettings();
	
}
