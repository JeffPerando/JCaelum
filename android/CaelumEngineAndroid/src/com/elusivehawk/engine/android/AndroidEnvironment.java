
package com.elusivehawk.engine.android;

import java.util.List;
import com.elusivehawk.engine.IGameEnvironment;
import com.elusivehawk.engine.input.Input;
import com.elusivehawk.engine.render.DisplaySettings;
import com.elusivehawk.engine.render.IDisplay;
import com.elusivehawk.engine.render.IRenderEnvironment;
import com.elusivehawk.util.EnumOS;
import com.elusivehawk.util.json.JsonObject;

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
	
	@SuppressWarnings("unused")
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
	public IRenderEnvironment getRenderEnv()
	{
		return this.renderEnviro;
	}
	
	@Override
	public IDisplay createDisplay(DisplaySettings settings)
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public List<Input> loadInputs()
	{
		return null;
	}
	
}
