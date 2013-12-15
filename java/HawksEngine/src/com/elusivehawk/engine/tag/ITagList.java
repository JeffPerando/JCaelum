
package com.elusivehawk.engine.tag;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public interface ITagList
{
	public void addTag(ITag<?> tag);
	
	public ITag<?> getTag(String name);
	
}
