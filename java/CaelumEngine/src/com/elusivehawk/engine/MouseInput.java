
package com.elusivehawk.engine;

import com.elusivehawk.util.math.Vector;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public abstract class MouseInput extends Input
{
	public abstract Vector getMousePos();
	
	public abstract Vector getMousePosDelta();
	
	public abstract EnumMouseClick getClickStatus(int button);
	
	public abstract EnumMouseClick getOldClickStatus(int button);
	
	public abstract int getButtonCount();
	
	public abstract float getWheelScroll();
	
	public abstract void setGrabMouse(boolean grab);
	
}
