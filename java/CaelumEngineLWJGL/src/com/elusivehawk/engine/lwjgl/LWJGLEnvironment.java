
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
	private final IRenderEnvironment renderEnviro = new OpenGLEnvironment();
	
	@Override
	public void initiate(JsonObject json, String... args)
	{
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
		return this.renderEnviro;
	}
	
	@Override
	public boolean isCompatible(EnumOS os)
	{
		return os != EnumOS.ANDROID;
	}
	
	public static String determineLWJGLPath()
	{
		//TODO: this only works on Debian... but we'll try it for now.
		
		return (EnumOS.CURR_OS == EnumOS.LINUX && new File("/usr/lib/jni/liblwjgl.so").exists()) ? "/usr/lib/jni" : FileHelper.createFile("/lwjgl/native/" + EnumOS.CURR_OS.toString()).getAbsolutePath();
	}
	
}
