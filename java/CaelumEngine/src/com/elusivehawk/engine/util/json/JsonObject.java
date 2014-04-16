
package com.elusivehawk.engine.util.json;

import java.util.List;
import java.util.Map;
import com.google.common.collect.Lists;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class JsonObject extends JsonKeypair
{
	protected final List<JsonKeypair> jsons;
	
	@SuppressWarnings("unqualified-field-access")
	public JsonObject(String name, Map<String, JsonKeypair> map)
	{
		super(EnumJsonType.OBJECT, name, "{");
		jsons = Lists.newArrayList(map.values());
		
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
	
	public JsonKeypair getValue(String name)
	{
		if (name != null)
		{
			for (JsonKeypair json : this.jsons)
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
