
package com.elusivehawk.engine.tag;

import java.io.DataOutputStream;
import java.io.IOException;

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
	
	public void save(DataOutputStream out) throws IOException;
	
}
