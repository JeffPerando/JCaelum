
package com.elusivehawk.engine.lwjgl;

import java.util.List;
import com.elusivehawk.engine.core.CaelumEngine;
import com.elusivehawk.engine.core.EnumOS;
import com.elusivehawk.engine.core.IGameEnvironment;
import com.elusivehawk.engine.core.ILog;
import com.elusivehawk.engine.core.Input;
import com.elusivehawk.engine.render.IRenderEnvironment;
import com.elusivehawk.engine.util.FileHelper;
import com.elusivehawk.engine.util.json.EnumJsonType;
import com.elusivehawk.engine.util.json.JsonObject;
import com.elusivehawk.engine.util.json.JsonValue;

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
	public boolean isCompatible(EnumOS os)
	{
		return os != EnumOS.ANDROID;
	}
	
	@Override
	public void initiate(JsonObject json, String... args)
	{
		System.setProperty("org.lwjgl.opengl.Display.noinput", "true");
		
		String lib = determineLWJGLPath();
		
		if (CaelumEngine.DEBUG && json != null)
		{
			JsonValue val = json.getValue("debugNativeLocation");
			
			if (val.type == EnumJsonType.STRING)
			{
				lib = val.value;
				
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
	public List<Input> loadInputs()
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	public static String determineLWJGLPath()
	{
		//TODO: this only works on Debian... but we'll try it for now.
		
		return (EnumOS.getCurrentOS() == EnumOS.LINUX && FileHelper.createFile("/usr/lib/jni/liblwjgl.so").exists()) ? "/usr/lib/jni" : FileHelper.createFile("/lwjgl/native/" + EnumOS.getCurrentOS().toString()).getAbsolutePath();
	}
	
}
