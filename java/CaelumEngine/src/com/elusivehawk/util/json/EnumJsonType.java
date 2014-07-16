
package com.elusivehawk.util.json;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public enum EnumJsonType
{
	ARRAY, OBJECT, STRING, BOOL, FLOAT, INT;
	
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
