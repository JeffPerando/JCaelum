
package com.elusivehawk.engine.render;

import java.util.Collection;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public interface IRenderHUB
{
	public void update(double delta);
	
	public ICamera<?, ?> getCamera();
	
	public IScene getScene();
	
	public Collection<IRenderEngine> getRenderEngines();
	
	public EnumRenderMode getRenderMode();
	
	public boolean updateDisplay();
	
	public DisplaySettings getSettings();
	
}
