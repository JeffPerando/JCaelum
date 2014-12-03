
package com.elusivehawk.caelum;

import com.elusivehawk.caelum.input.EnumInputType;
import com.elusivehawk.caelum.input.Input;
import com.elusivehawk.caelum.input.InputManager;
import com.elusivehawk.util.EnumOS;
import com.elusivehawk.util.Internal;
import com.elusivehawk.util.json.JsonObject;
import com.google.common.collect.ImmutableList;

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
	
	void preInit();
	
	void initiate(JsonObject json, String... args);
	
	void destroy();
	
	String getName();
	
	ImmutableList<String> getNatives();
	
	IDisplayImpl createDisplay() throws Throwable;
	
	Input createInput(InputManager inmgr, EnumInputType type);
	
}
