
package com.elusivehawk.engine.tag;

import com.elusivehawk.engine.core.Buffer;

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
