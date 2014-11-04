
package com.elusivehawk.caelum;

import java.util.List;
import com.elusivehawk.caelum.input.Input;
import com.elusivehawk.caelum.render.DisplaySettings;
import com.elusivehawk.caelum.render.IDisplay;
import com.elusivehawk.caelum.render.RenderContext;
import com.elusivehawk.util.EnumOS;
import com.elusivehawk.util.Internal;
import com.elusivehawk.util.concurrent.IThreadStoppable;
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
	
	String getName();
	
	/**
	 * 
	 * Called during startup.
	 * 
	 * @param settings The settings to create the display under.
	 * @return The display created.
	 */
	IDisplay createDisplay(DisplaySettings settings);
	
	List<Input> loadInputs();
	
	/**
	 * 
	 * Returns an OpenGL context object.
	 * 
	 * @param version The version of the context being requested.
	 * @return The OpenGL context object requested, or null if it couldn't be found.
	 */
	Object getGL(int version);
	
	IThreadStoppable createRenderThread(RenderContext rcon);
	
}
