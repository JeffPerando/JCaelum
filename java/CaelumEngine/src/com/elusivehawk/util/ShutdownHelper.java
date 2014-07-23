
package com.elusivehawk.util;


/**
 * 
 * Assists in helping to shut down your PC or Android application.
 * 
 * @author Elusivehawk
 */
public final class ShutdownHelper
{
	private static final ShutdownHelper INSTANCE = new ShutdownHelper();
	
	private ShutdownMechanism shutdown = new ShutdownMechanism();
	
	public static ShutdownHelper instance()
	{
		return INSTANCE;
	}
	
	public void setShutdownMech(ShutdownMechanism sm)
	{
		assert sm != null;
		
		this.shutdown = sm;
		
	}
	
	public static void exit(String err)
	{
		int hash = err.hashCode();
		
		System.err.println(String.format("Exiting with err %s (%s)", hash, err));
		
		exit0(hash);
		
	}
	
	public static void exit(int err)
	{
		System.err.println(String.format("Exiting with err %s", err));
		
		exit0(err);
		
	}
	
	private static void exit0(int err)
	{
		ShutdownMechanism sm = instance().shutdown;
		instance().shutdown = null;
		
		sm.exit(err);
		
	}
	
	public static class ShutdownMechanism
	{
		@SuppressWarnings("static-method")
		public void exit(int err)
		{
			System.exit(err);
			
		}
		
	}
	
}
