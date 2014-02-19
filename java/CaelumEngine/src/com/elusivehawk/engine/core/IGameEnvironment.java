
package com.elusivehawk.engine.core;

import com.eclipsesource.json.JsonObject;
import com.elusivehawk.engine.render2.IRenderEnvironment;

/**
 * 
 * Interface for library-specific implementations, such as:<br>
 * <ul>
 * <li>2D rendering implementation</li>
 * <li>Input implementation (Mouse, keyboard, console controller, etc.)</li>
 * <li>Logging</li>
 * <li>Display implementation</li>
 * </ul>
 * <p>
 * NOTICE: The game environment in question shouldn't be in the same library as the game itself.
 * 
 * @author Elusivehawk
 */
public interface IGameEnvironment
{
	public boolean isCompatible(EnumOS os);
	
	public void initiate(JsonObject json, String... args);
	
	public String getName();
	
	public ILog getLog();
	
	public IRenderEnvironment getRenderEnv();
	
}
