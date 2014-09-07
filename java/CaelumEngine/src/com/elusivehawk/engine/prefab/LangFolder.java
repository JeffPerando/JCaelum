
package com.elusivehawk.engine.prefab;

import java.util.Map;
import com.elusivehawk.util.IPopulator;
import com.google.common.collect.Maps;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class LangFolder
{
	private String name;
	private final Map<String, LangFolder> folders = Maps.newHashMap();
	
	@SuppressWarnings("unqualified-field-access")
	public LangFolder(String title)
	{
		name = title;
		
	}
	
	public LangFolder(String title, IPopulator<LangFolder> pop)
	{
		this(title);
		pop.populate(this);
		
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public void addSubFolder(LangFolder sub)
	{
		this.folders.put(sub.getName(), sub);
		
	}
	
	public LangFolder getFolder(String name)
	{
		return this.folders.get(name);
	}
	
}
