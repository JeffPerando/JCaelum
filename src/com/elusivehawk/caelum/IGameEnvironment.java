
package com.elusivehawk.caelum;

import com.elusivehawk.caelum.input.EnumInputType;
import com.elusivehawk.caelum.input.Input;
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
	boolean isCompatible(EnumOS os);
	
	void initiate(JsonObject json, String... args);
	
	void destroy();
	
	String getName();
	
	/**
	 * 
	 * @param settings
	 * @return
	 * @throws Throwable
	 */
	IDisplayImpl createDisplay(DisplaySettings settings) throws Throwable;
	
	Input loadInput(Display display, EnumInputType type);
	
}
