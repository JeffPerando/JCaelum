
package com.elusivehawk.caelum;

import java.io.Closeable;
import com.elusivehawk.caelum.input.InputManager;
import com.elusivehawk.caelum.render.IRenderer;
import com.elusivehawk.caelum.render.RenderContext;
import com.elusivehawk.caelum.render.gl.GL1;
import com.elusivehawk.util.IUpdatable;
import com.elusivehawk.util.Logger;

/**
 * 
 * Core class for the Caelum Engine's display system.
 * 
 * @author Elusivehawk
 */
public class Display implements Closeable, IUpdatable
{
	private final String name;
	private final InputManager input;
	private final RenderContext rcon;
	
	private DisplaySettings settings = null;
	private IDisplayImpl impl = null;
	
	private int width = 0, height = 0;
	private float aspectRatio = 0f;
	private boolean refresh = true, closed = false, close = false, initiated = false;
	
	@SuppressWarnings("unqualified-field-access")
	public Display(String str, DisplaySettings ds, IRenderer r)
	{
		assert ds != null;
		assert r != null;
		
		name = str;
		settings = ds;
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
		
		this.impl.preRenderDisplay();
		
		if (this.impl.isCloseRequested() || this.close)
		{
			try
			{
				this.rcon.close();
				this.input.close();
				this.impl.close();
				
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
			this.impl.updateSettings(this.settings);
			
			synchronized (this)
			{
				this.height = this.impl.getHeight();
				this.width = this.impl.getWidth();
				this.aspectRatio = (float)this.height / (float)this.width;
				
			}
			
			GL1.glViewport(this);
			GL1.glClearColor(this.settings.bg);
			
			this.refresh = false;
			
		}
		
		this.rcon.update(delta);
		
		this.impl.updateDisplay();
		
	}
	
	@Override
	public synchronized void close()
	{
		this.close = true;
		
	}
	
	public void initDisplay(IGameEnvironment ge) throws Throwable
	{
		IDisplayImpl imp = ge.createDisplay();
		
		if (imp == null)
		{
			return;
		}
		
		imp.createDisplay(this.settings);
		
		this.impl = imp;
		
		synchronized (this)
		{
			this.height = this.impl.getHeight();
			this.width = this.impl.getWidth();
			
			this.initiated = true;
			
		}
		
		this.input.initiateInput(ge);
		
		if (!this.rcon.initContext())
		{
			return;
		}
		
		imp.postInit();
		
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
	
	public String getName()
	{
		return this.name;
	}
	
	public int getHeight()
	{
		return this.height;
	}
	
	public int getWidth()
	{
		return this.width;
	}
	
	public float getAspectRatio()
	{
		return this.aspectRatio;
	}
	
	public boolean isClosed()
	{
		return this.closed;
	}
	
	public IDisplayImpl getImpl()
	{
		return this.impl;
	}
	
	public InputManager getInput()
	{
		return this.input;
	}
	
	public boolean isInitiated()
	{
		return this.initiated;
	}
	
	public synchronized void updateSettings(DisplaySettings ds)
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
	
}
