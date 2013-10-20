
package com.elusivehawk.engine.render;

import java.util.List;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public interface IRenderHUB
{
	public ICamera getCamera();
	
	public IScene getScene();
	
	public List<IRenderEngine> getRenderEngines();
	
	public EnumRenderMode getRenderMode();
	
}
