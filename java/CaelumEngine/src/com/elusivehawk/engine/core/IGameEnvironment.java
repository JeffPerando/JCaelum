
package com.elusivehawk.engine.core;

import java.util.List;
import com.elusivehawk.engine.render.IRenderEnvironment;
import com.elusivehawk.util.EnumOS;
import com.elusivehawk.util.Internal;
import com.elusivehawk.util.json.JsonObject;

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
 * NOTICE: The game environment in question shouldn't be compiled with the game itself.
 * 
 * @author Elusivehawk
 */
@Internal
public interface IGameEnvironment
{
	public boolean isCompatible(EnumOS os);
	
	public void initiate(JsonObject json, String... args);
	
	public String getName();
	
	public ILog getLog();
	
	public IRenderEnvironment getRenderEnv();
	
	public List<Input> loadInputs();
	
}
