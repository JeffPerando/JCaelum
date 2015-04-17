
package com.elusivehawk.caelum.render;

import java.io.Closeable;
import java.util.List;
import com.elusivehawk.caelum.CaelumEngine;
import com.elusivehawk.util.DelayedUpdater;
import com.elusivehawk.util.IUpdatable;
import com.elusivehawk.util.Logger;
import com.elusivehawk.util.ShutdownHelper;
import com.elusivehawk.util.storage.SyncList;

/**
 * 
 * Handles window updating.
 * 
 * @author Elusivehawk
 */
public final class WindowManager implements Closeable, IUpdatable
{
	private final DelayedUpdater cleaner = new DelayedUpdater(1.0, ((delta) ->
	{
		Deletables.instance().cleanup();
		
	}));
	private final List<Window> windows = SyncList.newList();
	
	@Override
	public void update(double delta) throws Throwable
	{
		for (Window window : this.windows)
		{
			if (!window.isInitiated())
			{
				Logger.verbose("Initiating window \"%s\"", window.getName());
				
				window.initWindow();
				
			}
			
			window.update(delta);
			
			if (window.isClosed())
			{
				this.windows.remove(window);
				
			}
			
		}
		
		this.cleaner.update(delta);
		
		if (this.windows.isEmpty() && !CaelumEngine.game().isGameHeadless())
		{
			ShutdownHelper.exit(0);
			
		}
		
	}
	
	@Override
	public void close()
	{
		this.windows.forEach(((window) ->
		{
			if (!window.isClosed())
			{
				try
				{
					window.close();
					
				}
				catch (Throwable e)
				{
					Logger.err(e);
					
				}
				
			}
			
		}));
		
		this.windows.clear();
		
	}
	
	public void updateInput(double delta)
	{
		this.windows.forEach(((window) -> {window.updateInput(delta);}));
		
	}
	
	public void sendInputEvents(double delta)
	{
		this.windows.forEach(((window) -> {window.sendInputEvents(delta);}));
		
	}
	
	public Window createDisplay(String name, WindowSettings settings, IRenderer renderer)
	{
		if (name == null || name.equalsIgnoreCase(""))
		{
			return null;
		}
		
		if (this.getWindow(name) != null)
		{
			return null;
		}
		
		if (settings == null)
		{
			return null;
		}
		
		Window ret = new Window(name, settings, renderer);
		
		this.windows.add(ret);
		
		return ret;
	}
	
	public Window getWindow(String name)
	{
		for (Window window : this.windows)
		{
			if (window.getName().equalsIgnoreCase(name))
			{
				return window;
			}
			
		}
		
		return null;
	}
	
}
