
package com.elusivehawk.engine.tag;

import com.elusivehawk.engine.util.io.ByteReader;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public interface ITagReader<T>
{
	public ITag<T> readTag(String name, ByteReader wrap);
	
}
