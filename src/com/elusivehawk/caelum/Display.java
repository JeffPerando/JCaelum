
package com.elusivehawk.caelum;

import java.io.Closeable;
import java.io.IOException;
import com.elusivehawk.caelum.input.EnumInputType;
import com.elusivehawk.caelum.input.IInputListener;
import com.elusivehawk.caelum.input.InputManager;
import com.elusivehawk.caelum.render.IRenderable;
import com.elusivehawk.caelum.render.RenderContext;
import com.elusivehawk.caelum.render.ThreadGameRender;
import com.elusivehawk.util.IUpdatable;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class Display implements Closeable, IUpdatable
{
	private final String name;
	private final IRenderable renderer;
	private final InputManager input;
	
	private DisplaySettings settings = null;
	private RenderContext rcon = null;
	private IDisplayImpl impl = null;
	
	private int width = 0, height = 0;
	private boolean refresh = true, closed = false;
	
	@SuppressWarnings("unqualified-field-access")
	public Display(String str, DisplaySettings ds, IRenderable r)
	{
		assert ds != null;
		assert r != null;
		
		name = str;
		settings = ds;
		renderer = r;
		
		input = new InputManager(this);
		
	}
	
	@Override
	public void update(double delta) throws Throwable
	{
		if (this.rcon == null)
		{
			throw new NullPointerException("Cannot render, render context wasn't made");
		}
		
		if (this.impl.isCloseRequested())
		{
			this.rcon.cleanup();
			this.impl.close();
			
			this.closed = true;
			
			return;
		}
		
		if (this.refresh)
		{
			this.impl.updateSettings(this.settings);
			
			this.refresh = false;
			
		}
		
		this.impl.preRenderDisplay();
		
		this.rcon.update(delta);
		
		this.impl.updateDisplay();
		
		this.input.update(delta);
		
	}
	
	@Override
	public void close() throws IOException
	{
		this.impl.close();
		
	}
	
	public boolean initDisplay(IGameEnvironment ge) throws Throwable
	{
		assert Thread.currentThread() instanceof ThreadGameRender : "Cannot initiate display outside of rendering thread";
		
		IDisplayImpl imp = ge.createDisplay(this.settings);
		
		if (imp == null)
		{
			return false;
		}
		
		imp.createDisplay();
		
		if (!imp.isCreated())
		{
			return false;
		}
		
		this.impl = imp;
		
		this.input.initiateInput(ge);
		
		this.rcon = new RenderContext(this, this.renderer);
		
		if (!this.rcon.initContext())
		{
			return false;
		}
		
		this.height = this.impl.getHeight();
		this.width = this.impl.getWidth();
		
		return true;
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
	
	public boolean isClosed()
	{
		return this.closed;
	}
	
	public IDisplayImpl getImpl()
	{
		return this.impl;
	}
	
	public void updateSettings(DisplaySettings ds)
	{
		assert ds != null;
		
		this.settings = ds;
		this.refresh = true;
		
	}
	
	public void addInputListener(EnumInputType type, IInputListener lis)
	{
		this.input.addListener(type, lis);
		
	}
	
	public void createInputType(EnumInputType type)
	{
		assert type != null;
		
		this.input.createInputType(type);
		
	}
	
	public void sendInputEvents(double delta)
	{
		this.input.sendInputEvents(delta);
		
	}
	
}
