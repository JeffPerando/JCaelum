
package com.elusivehawk.engine.tag;

import com.elusivehawk.engine.util.io.ByteWrapper;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public interface ITagReader<T>
{
	public ITag<T> readTag(String name, ByteWrapper wrap);
	
}
