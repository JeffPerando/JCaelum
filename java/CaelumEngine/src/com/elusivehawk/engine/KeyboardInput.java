
package com.elusivehawk.engine;

import java.util.List;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public abstract class KeyboardInput extends Input
{
	public abstract boolean isKeyDown(Key key);
	
	public abstract List<Key> getPushedKeys();
	
}
