
package com.elusivehawk.engine.tag;

import com.elusivehawk.engine.util.io.ByteWriter;

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
	
	public int save(ByteWriter w);
	
}
