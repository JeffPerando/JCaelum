
package com.elusivehawk.caelum;

import java.io.Closeable;
import java.util.List;
import com.elusivehawk.caelum.render.IRenderable;
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
	
	@SuppressWarnings("unqualified-field-access")
	public DisplayManager(IGameEnvironment ge)
	{
		assert ge != null;
		
		env = ge;
		
	}
	
	@Override
	public void update(double delta) throws Throwable
	{
		for (Display display : this.displays)
		{
			if (!display.isInitiated())
			{
				Logger.verbose("Initiating display \"%s\"", display.getName());
				
				display.initDisplay(this.env);
				
			}
			
			display.update(delta);
			
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
					Logger.err(e);
					
				}
				
			}
			
		}));
		
		this.displays.clear();
		
	}
	
	public void updateInput(double delta)
	{
		this.displays.forEach(((display) -> {display.updateInput(delta);}));
		
	}
	
	public void sendInputEvents(double delta)
	{
		this.displays.forEach(((display) -> {display.sendInputEvents(delta);}));
		
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
	
}
