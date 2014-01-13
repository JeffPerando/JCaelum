
package com.elusivehawk.engine.network;

import java.net.Socket;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public interface IConnectable
{
	public void connect(IP ip);
	
	public void connect(Socket s);
	
	public void beginComm();
	
}
