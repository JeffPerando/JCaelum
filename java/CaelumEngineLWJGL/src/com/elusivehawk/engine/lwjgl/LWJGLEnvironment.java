
package com.elusivehawk.engine.lwjgl;

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
public class LWJGLEnvironment implements IGameEnvironment
{
	@Override
	public void initiate()
	{
		if (EnumOS.OS == EnumOS.ANDROID)
		{
			throw new RuntimeException("LWJGL is *NOT* compatible with your smartphone!");
			
		}
		
		System.setProperty("org.lwjgl.opengl.Display.noinput", "true");
		
	}
	
	@Override
	public String getName()
	{
		return "CaelumLWJGL";
	}
	
	@Override
	public ILog getLog()
	{
		return null;
	}
	
	@Override
	public IRenderEnvironment getRenderEnv()
	{
		// TODO Auto-generated method stub
		return null;
	}
	
}
