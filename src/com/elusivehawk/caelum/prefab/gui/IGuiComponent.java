
package com.elusivehawk.caelum.prefab.gui;

import com.elusivehawk.caelum.Display;
import com.elusivehawk.caelum.prefab.Rectangle;
import com.elusivehawk.caelum.render.Canvas;
import com.elusivehawk.util.math.VectorF;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public interface IGuiComponent
{
	void drawComponent(Canvas canvas, int state);
	
	void onClicked(Display display, int button);
	
	void onDragged(VectorF delta);
	
	Rectangle getBounds();
	
	boolean isActive();
	
}
