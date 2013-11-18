
package com.elusivehawk.engine.core;

import com.elusivehawk.engine.render.IRenderHUB;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public interface IGame
{
	public boolean initiate();
	
	public void update(double delta);
	
	public void onGameClosed();
	
	public void handleException(Throwable e);
	
	public int getTargetUpdateCount();
	
	public String getLWJGLPath();
	
	public IRenderHUB getRenderHUB();
	
}
