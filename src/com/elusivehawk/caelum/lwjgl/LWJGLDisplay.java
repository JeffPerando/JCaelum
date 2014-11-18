
package com.elusivehawk.caelum.lwjgl;

import java.io.IOException;
import java.nio.IntBuffer;
import org.lwjgl.system.glfw.GLFW;
import com.elusivehawk.caelum.DisplaySettings;
import com.elusivehawk.caelum.IDisplayImpl;
import com.elusivehawk.util.storage.BufferHelper;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class LWJGLDisplay implements IDisplayImpl
{
	private final DisplaySettings settings;
	
	private int width = 0, height = 0;
	private long id = 0;
	
	@SuppressWarnings("unqualified-field-access")
	public LWJGLDisplay(DisplaySettings ds)
	{
		settings = ds;
		
	}
	
	@Override
	public void close() throws IOException
	{
		GLFW.glfwDestroyWindow(this.id);
		
	}
	
	@Override
	public boolean isCreated()
	{
		return this.id != 0;
	}
	
	@Override
	public void createDisplay() throws Exception
	{
		this.id = GLFW.glfwCreateWindow(this.settings.width, this.settings.height, this.settings.title, GLFW.glfwGetPrimaryMonitor()/*TODO Implement >1 monitor support*/, 0);
		
		if (this.id == 0)
		{
			throw new RuntimeException("Could not create display!");
		}
		
	}
	
	@Override
	public boolean isCloseRequested()
	{
		return GLFW.glfwWindowShouldClose(this.id) != 0;
	}
	
	@Override
	public int getHeight()
	{
		return this.height;
	}
	
	@Override
	public int getWidth()
	{
		return this.width;
	}
	
	@Override
	public void preRenderDisplay()
	{
		
	}
	
	@Override
	public void updateDisplay()
	{
		GLFW.glfwSwapBuffers(this.id);
		
	}
	
	@Override
	public void updateSettings(DisplaySettings settings)
	{
		GLFW.glfwSetWindowTitle(this.id, settings.title);
		GLFW.glfwSetWindowSize(this.id, settings.width, settings.height);
		GLFW.glfwSwapInterval(settings.vsync ? 1 : 0);
		
		/*
		 * TODO:
		 * 
		 * Fullscreen
		 * Icon(s)
		 * 
		 */
		
		IntBuffer size = BufferHelper.createIntBuffer(2);
		
		GLFW.glfwGetFramebufferSize(this.id, size, size);
		
		this.width = size.get();
		this.height = size.get();
		
	}
	
}
