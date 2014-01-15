
package com.elusivehawk.engine.android;

import com.eclipsesource.json.JsonObject;
import com.elusivehawk.engine.core.EnumOS;
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
	private final AndroidREnvironment renderEnviro = new AndroidREnvironment();
	
	@Override
	public void initiate(JsonObject json, String... args)
	{
		//TODO Things
		
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
		return this.renderEnviro;
	}
	
	@Override
	public boolean isCompatible(EnumOS os)
	{
		return os == EnumOS.ANDROID;
	}
	
}
