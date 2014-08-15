
package com.elusivehawk.engine;

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
 * 
 * @author Elusivehawk
 */
@Internal
public interface IGameEnvironment
{
	public boolean isCompatible(EnumOS os);
	
	public void initiate(JsonObject json, String... args);
	
	public String getName();
	
	public IRenderEnvironment getRenderEnv();
	
	public List<Input> loadInputs();
	
	default ILog getLog()
	{
		return null;
	}
	
	default boolean singleThreaded()
	{
		return false;
	}
	
}
