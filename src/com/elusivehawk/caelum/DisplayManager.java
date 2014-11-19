
package com.elusivehawk.caelum;

import java.io.Closeable;
import java.util.List;
import com.elusivehawk.caelum.render.IRenderable;
import com.elusivehawk.caelum.render.RenderException;
import com.elusivehawk.util.IUpdatable;
import com.elusivehawk.util.Logger;
import com.elusivehawk.util.storage.SyncList;
import com.google.common.collect.Lists;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public final class DisplayManager implements Closeable, IUpdatable
{
	private final IGameEnvironment env;
	private final List<Display> displays = Lists.newArrayList();
	private final List<Display> displaysToInit = SyncList.newList();
	
	@SuppressWarnings("unqualified-field-access")
	public DisplayManager(IGameEnvironment ge)
	{
		env = ge;
		
	}
	
	@Override
	public void update(double delta) throws Throwable
	{
		if (!this.displaysToInit.isEmpty())
		{
			for (Display display : this.displaysToInit)
			{
				if (this.getDisplay(display.getName()) == null)
				{
					if (display.initDisplay(this.env))
					{
						this.displays.add(display);
						
					}
					else
					{
						Logger.log().warn("Could not initiate display \"%s\"", display.getName());
						
					}
					
				}
				
				this.displaysToInit.remove(display);
				
			}
			
		}
		
		this.displays.forEach(((display) ->
		{
			try
			{
				display.update(delta);
				
			}
			catch (Throwable e)
			{
				Logger.log().err(new RenderException("Error caught while updating display %s", e, display.getName()));
				
			}
			
		}));
		
	}
	
	@Override
	public void close()
	{
		this.displays.forEach(((display) ->
		{
			try
			{
				display.close();
				
			}
			catch (Throwable e)
			{
				Logger.log().err(e);
				
			}
			
		}));
		
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
		
		this.displaysToInit.add(ret);
		
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
