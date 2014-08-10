
package com.elusivehawk.util.json;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import com.elusivehawk.util.IPopulator;
import com.google.common.collect.Lists;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class JsonObject extends JsonData implements Iterable<JsonData>
{
	protected final List<JsonData> jsons;
	
	@SuppressWarnings("unqualified-field-access")
	public JsonObject(String name)
	{
		super(EnumJsonType.OBJECT, name, "{");
		jsons = Lists.newArrayList();
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public JsonObject(String name, Collection<JsonData> data)
	{
		this(name);
		jsons.addAll(data);
		
	}
	
	public JsonObject(String name, IPopulator<JsonObject> pop)
	{
		this(name);
		pop.populate(this);
		
	}
	
	@Override
	public Iterator<JsonData> iterator()
	{
		return this.jsons.iterator();
	}
	
	@Override
	public String toString(int tabs, boolean format)
	{
		StringBuilder b = new StringBuilder();
		
		b.append(super.toString(tabs, format));
		
		for (int c = 0; c < this.jsons.size(); c++)
		{
			if (format) b.append("\n");
			b.append(this.jsons.get(c).toString(tabs + 1, format));
			
			if (c < (this.jsons.size() - 1))
			{
				b.append(",");
				
			}
			
		}
		
		b.append(format ? "\n}" : "}");
		
		return b.toString();
	}
	
	public boolean add(JsonData data)
	{
		for (JsonData info : this.jsons)
		{
			if (info.key.equals(data.key))
			{
				return false;
			}
			
		}
		
		this.jsons.add(data);
		
		return true;
	}
	
	public JsonData getValue(String name)
	{
		if ((name != null && !"".equalsIgnoreCase(name)) && !this.jsons.isEmpty())
		{
			for (JsonData json : this.jsons)
			{
				if (name.equalsIgnoreCase(json.key))
				{
					return json;
				}
				
			}
			
		}
		
		return null;
	}
	
}
