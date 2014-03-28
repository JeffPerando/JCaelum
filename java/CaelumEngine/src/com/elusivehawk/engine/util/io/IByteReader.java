
package com.elusivehawk.engine.util.io;

/**
 * 
 * Convenience interface for reading bytes.
 * 
 * @author Elusivehawk
 */
public interface IByteReader
{
	public int remaining();
	
	public byte read();
	
	public byte[] readAll();
	
}
