
package com.elusivehawk.caelum;

import java.io.Closeable;
import java.util.List;
import com.elusivehawk.caelum.render.IRenderable;
import com.elusivehawk.caelum.render.RenderException;
import com.elusivehawk.util.IUpdatable;
import com.elusivehawk.util.Logger;
import com.elusivehawk.util.ShutdownHelper;
import com.elusivehawk.util.storage.SyncList;

/**
 * 
 * Handles display updating.
 * 
 * @author Elusivehawk
 */
public final class DisplayManager implements Closeable, IUpdatable
{
	private final IGameEnvironment env;
	private final List<Display> displays = SyncList.newList();
	
	/*private IDisplayImpl display = null;
	private boolean init = false;*/
	
	@SuppressWarnings("unqualified-field-access")
	public DisplayManager(IGameEnvironment ge)
	{
		env = ge;
		
	}
	
	@Override
	public void update(double delta) throws Throwable
	{
		/* Works:
		
		if (!this.init)
		{
			this.display = this.env.createDisplay();
			
			this.display.createDisplay(new DisplaySettings());
			
			this.display.postInit();
			
			this.init = true;
			
		}
		
		this.display.preRenderDisplay();
		
		GL1.glClear(GLConst.GL_COLOR_BUFFER_BIT | GLConst.GL_DEPTH_BUFFER_BIT | GLConst.GL_STENCIL_BUFFER_BIT);
		
		GL11.glBegin(GL11.GL_TRIANGLES);
		
		GL11.glVertex2f(0.2f, 0.2f);
		GL11.glVertex2f(0.2f, 0.5f);
		GL11.glVertex2f(0.5f, 0.2f);
		
		GL11.glEnd();
		
		this.display.updateDisplay();*/
		
		for (Display display : this.displays)
		{
			if (!display.isInitiated())
			{
				Logger.log().verbose("Initiating display \"%s\"", display.getName());
				
				display.initDisplay(this.env);
				
			}
			
			try
			{
				display.update(delta);
				
			}
			catch (Throwable e)
			{
				Logger.log().err(new RenderException("Error caught while updating display %s", e, display.getName()));
				
			}
			
			if (display.isClosed())
			{
				this.displays.remove(display);
				
			}
			
		}
		
		if (this.displays.isEmpty())
		{
			ShutdownHelper.exit(0);
			
		}
		
	}
	
	@Override
	public void close()
	{
		this.displays.forEach(((display) ->
		{
			if (!display.isClosed())
			{
				try
				{
					display.close();
					
				}
				catch (Throwable e)
				{
					Logger.log().err(e);
					
				}
				
			}
			
		}));
		
		this.displays.clear();
		
	}
	
	public void pollInput(double delta)
	{
		this.displays.forEach(((display) -> {display.pollInput(delta);}));
		
	}
	
	public Display createDisplay(String name, DisplaySettings settings, IRenderable renderer)
	{
		if (name == null || name.equalsIgnoreCase(""))
		{
			return null;
		}
		
		if (this.getDisplay(name) != null)
		{
			return null;
		}
		
		if (settings == null)
		{
			return null;
		}
		
		Display ret = new Display(name, settings, renderer);
		
		this.displays.add(ret);
		
		return ret;
	}
	
	public Display getDisplay(String name)
	{
		for (Display display : this.displays)
		{
			if (display.getName().equalsIgnoreCase(name))
			{
				return display;
			}
			
		}
		
		return null;
	}
	
	public void sendInputEvents(double delta)
	{
		this.displays.forEach(((display) -> {display.sendInputEvents(delta);}));
		
	}
	
}
