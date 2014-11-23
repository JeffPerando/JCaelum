
package com.elusivehawk.caelum.prefab.gui;

import com.elusivehawk.caelum.Display;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
@FunctionalInterface
public interface IButtonListener
{
	void onButtonClicked(Display display, Button button);
	
}
