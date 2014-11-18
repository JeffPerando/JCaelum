
package com.elusivehawk.caelum.input;

import java.util.List;
import com.elusivehawk.caelum.Display;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public abstract class Keyboard extends Input
{
	public Keyboard(Display window)
	{
		super(window);
		
	}
	
	@Override
	public final EnumInputType getType()
	{
		return EnumInputType.KEYBOARD;
	}
	
	public boolean useCapitals()
	{
		return this.isKeyDown(Key.SHIFT) || this.isKeyDown(Key.CAPS_LOCK);
	}
	
	public abstract boolean isKeyDown(Key key);
	
	public abstract List<Key> getPushedKeys();
	
	public abstract List<Key> getOldPushedKeys();
	
}
