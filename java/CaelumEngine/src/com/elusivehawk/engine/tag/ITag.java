
package com.elusivehawk.engine.tag;

import com.elusivehawk.engine.util.Buffer;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public interface ITag<T>
{
	public byte getType();
	
	public T getData();
	
	public String getName();
	
	public void save(Buffer<Byte> buf);
	
}
