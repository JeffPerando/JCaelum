
package com.elusivehawk.engine.android;

import java.util.List;
import com.elusivehawk.engine.core.EnumOS;
import com.elusivehawk.engine.core.IGameEnvironment;
import com.elusivehawk.engine.core.ILog;
import com.elusivehawk.engine.core.Input;
import com.elusivehawk.engine.render.IRenderEnvironment;
import com.elusivehawk.engine.util.json.JsonObject;

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
	public boolean isCompatible(EnumOS os)
	{
		return os == EnumOS.ANDROID;
	}
	
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
	public List<Input> loadInputs()
	{
		return null;
	}
	
}
