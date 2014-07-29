
package com.elusivehawk.engine.network;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public enum ConnectionType
{
	TCP,
	UDP,
	EITHER,
	UNKNOWN;
	
	public boolean isTcp()
	{
		return this != UDP && this != UNKNOWN;
	}
	
	public boolean isUdp()
	{
		return this != TCP && this != UNKNOWN;
	}
	
	public boolean isCompatible(ConnectionType type)
	{
		return type == EITHER || type == this;
	}
	
}
