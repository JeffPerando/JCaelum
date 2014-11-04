
package com.elusivehawk.caelum.lwjgl;

import java.util.List;
import com.elusivehawk.caelum.CaelumEngine;
import com.elusivehawk.caelum.IGameEnvironment;
import com.elusivehawk.caelum.input.Input;
import com.elusivehawk.caelum.render.DisplaySettings;
import com.elusivehawk.caelum.render.IDisplay;
import com.elusivehawk.caelum.render.RenderContext;
import com.elusivehawk.util.EnumOS;
import com.elusivehawk.util.concurrent.IThreadStoppable;
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
		
		System.setProperty("org.lwjgl.librarypath", CaelumEngine.getNativeLocation().getAbsolutePath());
		
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
	
	/*public static File determineLWJGLPath()
	{
		String path = null;
		
		if ((CompInfo.OS == EnumOS.LINUX && new File("/usr/lib/jni/liblwjgl.so").exists()))
		{
			path = "usr/lib/jni";//TODO: this only works on Debian... but we'll try it for now.
			
		}
		else if (FileHelper.createFile("lib/lwjgl/native").exists())
		{
			path = String.format("lib/lwjgl/native/%s", CompInfo.OS.toString());
			
		}
		
		if (path == null)
		{
			return FileHelper.getRootResDir();
		}
		
		return FileHelper.createFile(".", path);
	}*/
	
}
