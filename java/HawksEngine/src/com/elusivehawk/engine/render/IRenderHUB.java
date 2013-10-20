
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
	public ICamera getCamera();
	
	public IScene getScene();
	
	public Collection<IRenderEngine> getRenderEngines();
	
	public EnumRenderMode getRenderMode();
	
}
