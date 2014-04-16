
package com.elusivehawk.engine.util.json;

import com.elusivehawk.engine.util.StringHelper;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class JsonKeypair
{
	public final EnumJsonType type;
	public final String key, value;
	
	@SuppressWarnings("unqualified-field-access")
	public JsonKeypair(EnumJsonType jtype, String name, String info)
	{
		if (jtype == null)
		{
			throw new NullPointerException();
		}
		
		type = jtype;
		key = name;
		value = info;
		
	}
	
	@Override
	public String toString()
	{
		return this.toString(0, false);
	}
	
	public String toString(int tabs, boolean format)
	{
		StringBuilder b = new StringBuilder(tabs + 2);
		
		if (format)
		{
			for (int c = 0; c < tabs; c++)
			{
				b.append("\t");
				
			}
			
		}
		
		if (this.key != null && !"".equalsIgnoreCase(this.key))
		{
			b.append(String.format("\"%s\": ", this.key));
		}
		
		b.append(StringHelper.sanitizeEscapeSequence(this.type == EnumJsonType.STRING && this.value != null ? String.format("\"%s\"", this.value) : this.value));
		
		return b.toString();
	}
	
}
