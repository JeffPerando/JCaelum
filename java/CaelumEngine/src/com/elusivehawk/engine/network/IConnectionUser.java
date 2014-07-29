
package com.elusivehawk.engine.network;

/**
 * 
 * Lambda-friendly interface for doing things with {@link IConnection}s.
 * 
 * @author Elusivehawk
 */
public interface IConnectionUser
{
	public boolean processConnection(IConnection con);
	
}
