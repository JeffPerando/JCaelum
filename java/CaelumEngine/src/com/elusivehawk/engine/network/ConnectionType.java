
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
	EITHER;
	
	public boolean isTcp()
	{
		return this != UDP;
	}
	
	public boolean isUdp()
	{
		return this != TCP;
	}
	
	public boolean isCompatible(ConnectionType type)
	{
		return type == EITHER || type == this;
	}
	
}
