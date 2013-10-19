
package com.elusivehawk.engine.core;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public interface IStoreable
{
	public boolean store(ByteBuffer buf);
	
	public boolean store(FloatBuffer buf);
	
	public boolean store(IntBuffer buf);
	
}
