
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
	void onWindowClosing(Window window);
	
	void onWindowClosed(Window window);
	
	void onWindowResize(Window window, int nwidth, int nheight);
	
}
