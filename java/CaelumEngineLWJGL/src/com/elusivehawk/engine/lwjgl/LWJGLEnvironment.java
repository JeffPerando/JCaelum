
package com.elusivehawk.engine.lwjgl;

import java.io.File;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import com.elusivehawk.engine.core.CaelumEngine;
import com.elusivehawk.engine.core.EnumOS;
import com.elusivehawk.engine.core.IGameEnvironment;
import com.elusivehawk.engine.core.ILog;
import com.elusivehawk.engine.render.IRenderEnvironment;
import com.elusivehawk.engine.util.FileHelper;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class LWJGLEnvironment implements IGameEnvironment
{
	@Override
	public void initiate(JsonObject json, String... args)
	{
		if (EnumOS.CURR_OS == EnumOS.ANDROID)
		{
			throw new RuntimeException("LWJGL is *NOT* compatible with your smartphone!");
			
		}
		
		System.setProperty("org.lwjgl.opengl.Display.noinput", "true");
		
		String lib = determineLWJGLPath();
		
		if (CaelumEngine.DEBUG)
		{
			JsonValue val = json.get("debugNativeLocation");
			
			if (val.isString())
			{
				lib = val.asString();
				
			}
			
		}
		
		System.setProperty("org.lwjgl.librarypath", lib);
		
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
	
	public static String determineLWJGLPath()
	{
		//TODO: this only works on Debian... but we'll try it for now.
		
		return (EnumOS.CURR_OS == EnumOS.LINUX && new File("/usr/lib/jni/liblwjgl.so").exists()) ? "/usr/lib/jni" : FileHelper.createFile("/lwjgl/native/" + EnumOS.CURR_OS.toString()).getAbsolutePath();
	}
	
}
