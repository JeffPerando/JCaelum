
package com.elusivehawk.engine.network;

/**
 * 
 * Enumeration for client/server sensitivity.
 * 
 * @author Elusivehawk
 */
public enum Side
{
	CLIENT,
	SERVER,
	BOTH;
	
	public boolean isClient()
	{
		return this != SERVER;
	}
	
	public boolean isServer()
	{
		return this != CLIENT;
	}
	
	public boolean canReceive(Side s)
	{
		return this == BOTH || this != s;
	}
	
	public boolean canSend(Side s)
	{
		return this == BOTH || this == s;
	}
	
}
