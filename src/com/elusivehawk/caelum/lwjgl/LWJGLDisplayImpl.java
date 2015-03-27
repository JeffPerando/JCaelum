
package com.elusivehawk.caelum.lwjgl;

import java.io.IOException;
import java.nio.IntBuffer;
import org.lwjgl.PointerBuffer;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GLContext;
import com.elusivehawk.caelum.CaelumException;
import com.elusivehawk.caelum.DisplaySettings;
import com.elusivehawk.caelum.IDisplayImpl;
import com.elusivehawk.util.Logger;
import com.elusivehawk.util.storage.BufferHelper;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class LWJGLDisplayImpl implements IDisplayImpl
{
	private final IntBuffer
				w = BufferHelper.createIntBuffer(1),
				h = BufferHelper.createIntBuffer(1);
	
	private int width = 0, height = 0;
	private long id = 0;
	private GLContext context = null;
	
	@Override
	public void close() throws IOException
	{
		GLFW.glfwDestroyWindow(this.id);
		
	}
	
	@Override
	public void createDisplay(DisplaySettings settings) throws Exception
	{
		long monitor = 0;
		
		if (settings.monitor == null)
		{
			monitor = GLFW.glfwGetPrimaryMonitor();
			
		}
		else
		{
			PointerBuffer monitors = GLFW.glfwGetMonitors();
			
			for (int c = 0; c < monitors.limit(); c++)
			{
				long m = monitors.get(c);
				String name = GLFW.glfwGetMonitorName(m);
				
				Logger.debug("Found display %s", name);
				
				if (name.equalsIgnoreCase(settings.monitor))
				{
					monitor = m;
					break;
					
				}
				
			}
			
		}
		
		this.id = GLFW.glfwCreateWindow(settings.width, settings.height, settings.title, settings.fullscreen ? monitor : 0/*TODO Implement >1 monitor support*/, 1/*Enables for resource sharing*/);
		
		if (this.id == 0)
		{
			throw new CaelumException("Cannot make LWJGL display: ID returned 0");
		}
		
		GLFW.glfwMakeContextCurrent(this.id);
		
		GLFW.glfwSwapInterval(settings.vsync ? 1 : 0);
		GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, 0);
		
		GLFW.glfwShowWindow(this.id);
		
		this.context = GLContext.createFromCurrent();
		
		this.updateInfo();
		
	}
	
	@Override
	public void postInit()
	{
		GLFW.glfwMakeContextCurrent(0);
		
	}
	
	@Override
	public boolean isCreated()
	{
		return this.id != 0 && this.context != null;
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
		GLFW.glfwMakeContextCurrent(this.id);
		
	}
	
	@Override
	public void updateDisplay()
	{
		GLFW.glfwSwapBuffers(this.id);
		
		GLFW.glfwPollEvents();//TODO Check usage
		
		GLFW.glfwMakeContextCurrent(0);
		
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
		
		GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, 0);
		
		this.updateInfo();
		
	}
	
	public long getWindowId()
	{
		return this.id;
	}
	
	private void updateInfo()
	{
		GLFW.glfwGetWindowSize(this.id, this.w, this.h);
		
		this.width = this.w.get();
		this.height = this.h.get();
		
		this.w.position(0);
		this.h.position(0);
		
	}
	
}
