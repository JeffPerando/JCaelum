
package com.elusivehawk.caelum.window;

import com.elusivehawk.caelum.input.IInputListener;
import com.elusivehawk.caelum.render.IRenderer;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public interface IWindowListener extends IRenderer, IInputListener
{
	default void onWindowCreated(Window window){}
	
	default void onWindowClosing(Window window){};
	
	default void onWindowClosed(Window window){};
	
	default void onWindowResize(Window window, int nwidth, int nheight){};
	
}
