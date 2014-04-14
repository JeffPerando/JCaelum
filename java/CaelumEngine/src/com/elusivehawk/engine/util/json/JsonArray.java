
package com.elusivehawk.engine.util.json;

import java.util.List;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class JsonArray extends JsonValue
{
	protected final JsonValue[] array;
	
	@SuppressWarnings("unqualified-field-access")
	public JsonArray(String name, List<JsonValue> arr)
	{
		super(EnumJsonType.ARRAY, name, "[");
		array = arr.toArray(new JsonValue[arr.size()]);
		
	}
	
	@Override
	public String toString(int tabs, boolean format)
	{
		StringBuilder b = new StringBuilder();
		
		b.append(super.toString(tabs, format));
		
		JsonValue str;
		
		for (int c = 0; c < this.array.length; c++)
		{
			str = this.array[c];
			
			if (format) b.append("\n");
			
			b.append(str.toString(tabs + 1, format));
			
			if (c < (this.array.length - 1))
			{
				b.append(", ");
				
			}
			
		}
		
		b.append(format ? "\n]" : "]");
		
		return b.toString();
	}
	
	public int length()
	{
		return this.array.length;
	}
	
	public JsonValue getValue(int i)
	{
		return this.array[i];
	}
	
}
