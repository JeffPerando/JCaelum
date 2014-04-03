
package com.elusivehawk.engine.core;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public enum EnumScriptType
{
	JAVASCRIPT(/*FIXME "nashorn"*/"javascript"),
	LUA("luaj"),
	PYTHON(null),
	RUBY(null),
	OTHER(null);
	
	public final String engineName;
	
	@SuppressWarnings("unqualified-field-access")
	EnumScriptType(String eng)
	{
		engineName = eng;
		
	}
	
	public static EnumScriptType getType(String lang)
	{
		if (lang != null)
		{
			for (EnumScriptType type : values())
			{
				if (type.engineName == null)
				{
					continue;
				}
				
				if (lang.equalsIgnoreCase(type.engineName))
				{
					return type;
				}
				
			}
			
		}
		
		return OTHER;
	}
	
}
