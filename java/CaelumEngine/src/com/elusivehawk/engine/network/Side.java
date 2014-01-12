
package com.elusivehawk.engine.network;

/**
 * 
 * 
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
	
	public boolean belongsOnSide(Side side)
	{
		return (side.isClient() && this.isClient()) || (side.isServer() && this.isServer());
	}
	
}
