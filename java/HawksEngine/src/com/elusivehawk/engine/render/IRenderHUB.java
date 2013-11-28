
package com.elusivehawk.engine.render;

import java.util.Collection;
import com.elusivehawk.engine.core.IUpdatable;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public interface IRenderHUB extends IUpdatable
{
	public EnumRenderMode getRenderMode();
	
	public boolean updateDisplay();
	
	public DisplaySettings getSettings();
	
	public ICamera getCamera();
	
	public IScene getScene();
	
	public Collection<IRenderEngine> getRenderEngines();
	
	public int getHighestPriority();
	
}
