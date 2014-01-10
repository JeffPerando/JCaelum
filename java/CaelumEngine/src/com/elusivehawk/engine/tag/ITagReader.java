
package com.elusivehawk.engine.tag;

import com.elusivehawk.engine.util.Buffer;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public interface ITagReader<T>
{
	public ITag<T> readTag(String name, Buffer<Byte> buf);
	
}
