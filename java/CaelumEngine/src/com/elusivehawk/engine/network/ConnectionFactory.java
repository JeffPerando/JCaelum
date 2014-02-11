
package com.elusivehawk.engine.network;

import java.util.UUID;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public abstract class ConnectionFactory
{
	private static ConnectionFactory d = new ConnectionFactoryImpl(), factory = d;
	
	public static ConnectionFactory factory()
	{
		return factory;
	}
	
	public static void setFactory(ConnectionFactory fcty)
	{
		assert fcty != null;
		
		if (factory != d)
		{
			return;
		}
		
		factory = fcty;
		
	}
	
	public abstract IConnection create(IPacketHandler h, UUID id, int ups);
	
	public abstract IConnection createHS(IHost owner, UUID id, int ups);
	
	private static class ConnectionFactoryImpl extends ConnectionFactory
	{
		ConnectionFactoryImpl(){}
		
		@Override
		public IConnection create(IPacketHandler h, UUID id, int ups)
		{
			return new ConnectionImpl(h, id, ups);
		}
		
		@Override
		public IConnection createHS(IHost owner, UUID id, int ups)
		{
			return new HSConnectionImpl(owner, id, ups);
		}
		
	}
	
}
