
package com.elusivehawk.caelum.prefab.gui;

import com.elusivehawk.caelum.render.Window;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
@FunctionalInterface
public interface IButtonListener
{
	void onButtonClicked(Window window, Button button);
	
}
