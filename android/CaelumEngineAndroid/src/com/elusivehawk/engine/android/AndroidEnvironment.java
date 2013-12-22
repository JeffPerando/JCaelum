
package com.elusivehawk.engine.android;

import com.elusivehawk.engine.core.IGameEnvironment;
import com.elusivehawk.engine.core.ILog;
import com.elusivehawk.engine.render.IRenderEnvironment;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class AndroidEnvironment implements IGameEnvironment
{
	@Override
	public void initiate()
	{
		
	}
	
	@Override
	public String getName()
	{
		return "CaelumAndroid";
	}
	
	@Override
	public ILog getLog()
	{
		return new AndroidLog();
	}
	
	@Override
	public IRenderEnvironment getRenderEnv()
	{
		// TODO Auto-generated method stub
		return null;
	}
	
}
