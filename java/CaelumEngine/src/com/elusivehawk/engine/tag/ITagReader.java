
package com.elusivehawk.engine.tag;

import java.io.DataInputStream;
import java.io.IOException;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public interface ITagReader<T>
{
	public ITag<T> readTag(String name, DataInputStream is) throws IOException;
	
}
