
package com.elusivehawk.engine.core;

/**
 * 
 * Core thread used for the logic portion of a {@linkplain IGame game}.
 * 
 * @author Elusivehawk
 */
public class ThreadGameLoop extends ThreadTimedWrapper
{
	public ThreadGameLoop(IGame g)
	{
		super(g);
		
	}
	
	@Override
	public boolean initiate()
	{
		boolean ret = super.initiate();
		
		try
		{
			CaelumEngine.instance().startupHook.wait();
			
		}
		catch (Exception e){}
		
		return ret;
	}
	
}