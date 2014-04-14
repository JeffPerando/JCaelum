
package com.elusivehawk.engine.util.json;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public enum EnumJsonType
{
	ARRAY, OBJECT, STRING, BYTE, FLOAT, DOUBLE, SHORT, INT, LONG;
	
	public static EnumJsonType valueOfSafe(String name)
	{
		for (EnumJsonType e : values())
		{
			if (e.name().equalsIgnoreCase(name))
			{
				return e;
			}
			
		}
		
		return null;
	}
	
}
