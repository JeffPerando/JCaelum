
package com.elusivehawk.engine.lwjgl;

import java.util.List;
import com.elusivehawk.engine.IGameEnvironment;
import com.elusivehawk.engine.input.Input;
import com.elusivehawk.engine.render.DisplaySettings;
import com.elusivehawk.engine.render.IDisplay;
import com.elusivehawk.engine.render.RenderContext;
import com.elusivehawk.util.CompInfo;
import com.elusivehawk.util.EnumOS;
import com.elusivehawk.util.FileHelper;
import com.elusivehawk.util.concurrent.IThreadStoppable;
import com.elusivehawk.util.json.EnumJsonType;
import com.elusivehawk.util.json.JsonData;
import com.elusivehawk.util.json.JsonObject;
import com.google.common.collect.Lists;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class LWJGLEnvironment implements IGameEnvironment
{
	protected final OpenGL3 GL_3 = new OpenGL3();
	protected final Object GL_4 = null;
	
	@Override
	public boolean isCompatible(EnumOS os)
	{
		return os != EnumOS.ANDROID;
	}
	
	@Override
	public void initiate(JsonObject json, String... args)
	{
		System.setProperty("org.lwjgl.opengl.Display.noinput", "true");
		
		String lib = null;
		
		if (json != null)
		{
			JsonData val = json.getValue("debugNativeLocation");
			
			if (val != null)
			{
				if (val.type == EnumJsonType.STRING)
				{
					lib = val.value;
					
				}
				
			}
			
		}
		
		if (lib == null)
		{
			lib = determineLWJGLPath();
			
		}
		
		System.setProperty("org.lwjgl.librarypath", lib);
		
	}
	
	@Override
	public String getName()
	{
		return "CaelumLWJGL";
	}
	
	@Override
	public IDisplay createDisplay(DisplaySettings settings)
	{
		LWJGLDisplay ret = new LWJGLDisplay();
		
		ret.updateSettings(settings);
		
		return ret;
	}
	
	@Override
	public List<Input> loadInputs()
	{
		List<Input> ret = Lists.newArrayList();
		
		ret.add(new LWJGLMouse());
		ret.add(new LWJGLKeyboard());
		
		return ret;
	}
	
	public static String determineLWJGLPath()
	{
		//TODO: this only works on Debian... but we'll try it for now.
		
		return (CompInfo.OS == EnumOS.LINUX && FileHelper.createFile("/usr/lib/jni/liblwjgl.so").exists()) ? "/usr/lib/jni" : FileHelper.createFile(CompInfo.DEBUG && FileHelper.createFile("lib/lwjgl/native").exists() ? "lib/lwjgl/native" : ".", String.format("/%s", CompInfo.OS.toString())).getAbsolutePath();
	}
	
	@Override
	public Object getGL(int version)
	{
		switch (version)
		{
			case 1:
			case 2:
			case 3: return this.GL_3;
			case 4: return this.GL_4;
			default: return null;
		}
		
	}
	
	@Override
	public IThreadStoppable createRenderThread(RenderContext rcon)
	{
		return null;
	}
	
}
