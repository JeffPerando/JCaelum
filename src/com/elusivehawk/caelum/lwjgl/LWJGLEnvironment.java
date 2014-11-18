
package com.elusivehawk.caelum.lwjgl;

import org.lwjgl.system.glfw.GLFW;
import com.elusivehawk.caelum.CaelumEngine;
import com.elusivehawk.caelum.CaelumException;
import com.elusivehawk.caelum.Display;
import com.elusivehawk.caelum.DisplaySettings;
import com.elusivehawk.caelum.IDisplayImpl;
import com.elusivehawk.caelum.IGameEnvironment;
import com.elusivehawk.caelum.input.EnumInputType;
import com.elusivehawk.caelum.input.Input;
import com.elusivehawk.caelum.render.gl.GL1;
import com.elusivehawk.caelum.render.gl.GL2;
import com.elusivehawk.caelum.render.gl.GL3;
import com.elusivehawk.util.EnumOS;
import com.elusivehawk.util.json.JsonObject;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class LWJGLEnvironment implements IGameEnvironment
{
	@Override
	public boolean isCompatible(EnumOS os)
	{
		return os != EnumOS.ANDROID;
	}
	
	@Override
	public void initiate(JsonObject json, String... args)
	{
		GL1.setImpl(new OpenGL1());
		GL2.setImpl(new OpenGL2());
		GL3.setImpl(new OpenGL3());
		
		System.setProperty("org.lwjgl.opengl.Display.noinput", "true");
		
		System.setProperty("org.lwjgl.librarypath", CaelumEngine.getNativeLocation().getAbsolutePath());
		
		if (GLFW.glfwInit() != 1)
		{
			throw new CaelumException("Unable to initiate GLFW");
		}
		
	}
	
	@Override
	public void destroy()
	{
		GLFW.glfwTerminate();
		
	}
	
	@Override
	public String getName()
	{
		return "CaelumLWJGL";
	}
	
	@Override
	public IDisplayImpl createDisplay(DisplaySettings settings)
	{
		return new LWJGLDisplay(settings);
	}
	
	@Override
	public Input loadInput(Display display, EnumInputType type)
	{
		switch (type)
		{
			case KEYBOARD: return new LWJGLKeyboard(display);
			case MOUSE: return new LWJGLMouse(display);
			default: throw new CaelumException("Unsupported input type: %s", type);
		}
		
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
