
package com.elusivehawk.caelum.render;

import java.io.Closeable;
import java.nio.IntBuffer;
import org.lwjgl.PointerBuffer;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GLContext;
import com.elusivehawk.caelum.CaelumException;
import com.elusivehawk.caelum.input.InputManager;
import com.elusivehawk.caelum.render.gl.GL1;
import com.elusivehawk.util.IUpdatable;
import com.elusivehawk.util.Logger;
import com.elusivehawk.util.storage.BufferHelper;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class Window implements Closeable, IUpdatable
{
	private final String name;
	private final InputManager input;
	private final RenderContext rcon;
	
	private final IntBuffer
				w = BufferHelper.createIntBuffer(1),
				h = BufferHelper.createIntBuffer(1);
	
	private WindowSettings settings = null;
	private GLContext context = null;
	
	private long windowId = 0;
	private int width = 0, height = 0;
	private float aspectRatio = 0f;
	private boolean refresh = true, closed = false, close = false, initiated = false;
	
	@SuppressWarnings("unqualified-field-access")
	public Window(String str, WindowSettings ws, IRenderer r)
	{
		assert ws != null;
		assert r != null;
		
		name = str;
		settings = ws;
		rcon = new RenderContext(this, r);
		
		input = new InputManager(this);
		
	}
	
	@Override
	public void update(double delta) throws Throwable
	{
		if (this.closed)
		{
			throw new CaelumException("Trying to update a closed display: Not a good idea");
		}
		
		if (!this.initiated)
		{
			throw new CaelumException("Cannot render, display wasn't initiated");
		}
		
		GLFW.glfwMakeContextCurrent(this.windowId);
		
		if (GLFW.glfwWindowShouldClose(this.windowId) != 0 || this.close)
		{
			try
			{
				this.context.destroy();
				GLFW.glfwDestroyWindow(this.windowId);
				
				this.input.close();
				
			}
			catch (Throwable e)
			{
				Logger.err(e);
				
			}
			finally
			{
				synchronized (this)
				{
					this.closed = true;
					
				}
				
			}
			
			return;
		}
		
		if (this.refresh)
		{
			this.updateSettings();
			
			this.refresh = false;
			
		}
		
		this.rcon.update(delta);
		
		GLFW.glfwSwapBuffers(this.windowId);
		
		GLFW.glfwPollEvents();
		
		GLFW.glfwMakeContextCurrent(0);
		
	}
	
	@Override
	public synchronized void close()
	{
		this.close = true;
		
	}
	
	public void initWindow() throws Throwable
	{
		if (this.initiated)
		{
			throw new CaelumException("Already initiated!");
		}
		
		long monitor = 0;
		
		if (this.settings.monitor == null)
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
				
				Logger.debug("Found display %s", name);//XXX Debug
				
				if (name.equalsIgnoreCase(this.settings.monitor))
				{
					monitor = m;
					
					break;
				}
				
			}
			
		}
		
		this.windowId = GLFW.glfwCreateWindow(this.settings.width, this.settings.height, this.settings.title, monitor, 1/*Enables for resource sharing*/);
		
		GLFW.glfwMakeContextCurrent(this.windowId);
		
		GLFW.glfwSwapInterval(this.settings.vsync ? 1 : 0);
		GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, 0);
		
		GLFW.glfwShowWindow(this.windowId);
		
		this.context = GLContext.createFromCurrent();
		
		this.updateInfo();
		
		synchronized (this)
		{
			this.initiated = true;
			
		}
		
		GLFW.glfwMakeContextCurrent(0);
		
	}
	
	public void updateInput(double delta)
	{
		this.input.update(delta);
		
	}
	
	public void sendInputEvents(double delta)
	{
		if (this.initiated)
		{
			this.input.sendInputEvents(delta);
			
		}
		
	}
	
	public native String getName();
	
	public native long getId();
	
	public native int getHeight();
	
	public native int getWidth();
	
	public native float getAspectRatio();
	
	public native boolean isClosed();
	
	public InputManager getInput()
	{
		return this.input;
	}
	
	public boolean isInitiated()
	{
		return this.initiated;
	}
	
	public synchronized void updateSettings(WindowSettings ds)
	{
		assert ds != null;
		
		this.settings = ds;
		this.refresh = true;
		
	}
	
	public float interpolateX(int x)
	{
		return x / (float)this.width;
	}
	
	public float interpolateY(int y)
	{
		return y / (float)this.height;
	}
	
	private void updateSettings()
	{
		GLFW.glfwSetWindowTitle(this.windowId, this.settings.title);
		GLFW.glfwSetWindowSize(this.windowId, this.settings.width, this.settings.height);
		GLFW.glfwSwapInterval(this.settings.vsync ? 1 : 0);
		
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
	
	private void updateInfo()
	{
		GLFW.glfwGetWindowSize(this.windowId, this.w, this.h);
		
		synchronized (this)
		{
			this.width = this.w.get();
			this.height = this.h.get();
			this.aspectRatio = (float)this.height / (float)this.width;
			
		}
		
		this.w.position(0);
		this.h.position(0);
		
		GL1.glViewport(this);
		GL1.glClearColor(this.settings.bg);
		
	}
	
}
