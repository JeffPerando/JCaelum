
package com.elusivehawk.caelum.lwjgl;

import com.elusivehawk.caelum.IDisplayImpl;
import com.elusivehawk.caelum.IGameEnvironment;
import com.elusivehawk.caelum.input.IInputImpl;
import com.elusivehawk.caelum.input.Input;
import com.elusivehawk.caelum.input.Keyboard;
import com.elusivehawk.caelum.input.Mouse;
import com.elusivehawk.util.CompInfo;
import com.elusivehawk.util.EnumOS;
import com.google.common.collect.ImmutableList;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
@Deprecated
public class LWJGLEnvironment implements IGameEnvironment
{
	public static final String[]
					WIN_32 = {"/windows/x86/lwjgl.dll", "/windows/x86/OpenAL32.dll"},
					WIN_64 = {"/windows/x64/lwjgl.dll", "/windows/x64/OpenAL32.dll"},
					MAC_32 = {"/macosx/x64/liblwjgl.dylib", "/macosx/x64/libopenal.dylib"},
					MAC_64 = MAC_32,
					LINUX_32 = {"/linux/x86/liblwjgl.so", "/libnux/x86/libopenal.so"},
					LINUX_64 = {"/linux/x64/liblwjgl.so", "/libnux/x64/libopenal.so"};
	
	@Override
	public boolean isCompatible(EnumOS os)
	{
		return os != EnumOS.ANDROID;
	}
	
	@Override
	public void preInit()
	{
		//GL1.setImpl(new OpenGL1());
		//GL2.setImpl(new OpenGL2());
		//GL3.setImpl(new OpenGL3());
		
		//System.setProperty("org.lwjgl.opengl.Display.noinput", "true");
		
	}
	
	@Override
	public void initiate(String... args)
	{
		//System.setProperty("org.lwjgl.librarypath", CaelumEngine.getNativeLocation().getAbsolutePath());
		
	}
	
	@Override
	public void destroy(){}
	
	@Override
	public String getName()
	{
		return "CaelumLWJGL";
	}
	
	@Override
	public ImmutableList<String> getNatives()
	{
		String[] n = null;
		
		switch (CompInfo.OS)
		{
			case WINDOWS: n = CompInfo.IS_64_BIT ? WIN_64 : WIN_32; break;
			case MAC: n = CompInfo.IS_64_BIT ? MAC_64 : MAC_32; break;
			case LINUX: n = CompInfo.IS_64_BIT ? LINUX_64 : LINUX_32; break;
			
		}
		
		return n == null ? null : ImmutableList.copyOf(n);
	}
	
	@Override
	public IDisplayImpl createDisplay()
	{
		return new LWJGLDisplayImpl();
	}
	
	@Override
	public IInputImpl createInputImpl(Class<? extends Input> type)
	{
		if (type == Keyboard.class)
		{
			return new LWJGLKeyboardImpl();
		}
		
		if (type == Mouse.class)
		{
			return new LWJGLMouseImpl();
		}
		
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
