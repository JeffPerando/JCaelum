
package com.elusivehawk.util;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public enum EnumLogType
{
	DEBUG(false),
	INFO(false),
	ERROR(true),
	VERBOSE(false),
	WARN(true),
	WTF(true);
	
	public final boolean err;
	
	@SuppressWarnings("unqualified-field-access")
	EnumLogType(boolean warn)
	{
		err = warn;
		
	}
	
}
