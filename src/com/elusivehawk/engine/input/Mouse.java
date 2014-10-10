
package com.elusivehawk.engine.input;

import com.elusivehawk.util.math.Vector;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public abstract class Mouse extends Input
{
	public abstract Vector getMousePos();
	
	public abstract Vector getMousePosDelta();
	
	public abstract EnumMouseClick[] getClickStatus();
	
	public abstract EnumMouseClick[] getOldClickStatus();
	
	public abstract int getButtonCount();
	
	public abstract float getWheelScroll();
	
	public abstract void setGrabMouse(boolean grab);
	
}
