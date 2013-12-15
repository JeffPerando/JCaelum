
package com.elusivehawk.engine.core;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

/**
 * 
 * Interface for objects storable in NIO buffers.
 * 
 * @author Elusivehawk
 */
public interface IStoreable
{
	/**
	 * @return True if things were stored in the provided buffer.
	 */
	public boolean store(ByteBuffer buf);
	
	/**
	 * @return True if things were stored in the provided buffer.
	 */
	public boolean store(FloatBuffer buf);
	
	/**
	 * @return True if things were stored in the provided buffer.
	 */
	public boolean store(IntBuffer buf);
	
}
