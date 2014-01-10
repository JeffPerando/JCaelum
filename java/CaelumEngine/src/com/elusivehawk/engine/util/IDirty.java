
package com.elusivehawk.engine.util;

/**
 * 
 * You know when you make several variants of the same method just so you don't have to make a nigh pointless interface?<br>
 * This is here to prevent that.
 * <p>
 * (If you don't do that, then I salute you)
 * 
 * @author Elusivehawk
 */
public interface IDirty
{
	public boolean isDirty();
	
	public void setIsDirty(boolean b);
	
}
