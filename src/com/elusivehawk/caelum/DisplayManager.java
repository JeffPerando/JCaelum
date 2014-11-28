
package com.elusivehawk.caelum;

import java.io.Closeable;
import java.util.Iterator;
import java.util.List;
import com.elusivehawk.caelum.render.IRenderable;
import com.elusivehawk.caelum.render.RenderException;
import com.elusivehawk.util.IUpdatable;
import com.elusivehawk.util.Logger;
import com.elusivehawk.util.ShutdownHelper;
import com.elusivehawk.util.storage.SyncList;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public final class DisplayManager implements Closeable, IUpdatable
{
	private final IGameEnvironment env;
	private final List<Display>
			displays = SyncList.newList(),
			displaysToInit = SyncList.newList();
	
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
			Iterator<Display> itr = this.displaysToInit.iterator();
			
			while (itr.hasNext())
			{
				Display display = itr.next();
				
				if (!display.isInitiated())
				{
					if (this.getDisplay(display.getName()) == null && !this.displays.contains(display))
					{
						Logger.log().info("Initiating display \"%s\"", display.getName());
						
						boolean accept = true;
						
						try
						{
							display.initDisplay(this.env);
							
						}
						catch (Throwable e)
						{
							Logger.log().err(e);
							
							accept = false;
							
						}
						
						if (accept)
						{
							this.displays.add(display);
							
						}
						else
						{
							Logger.log().warn("Could not initiate display \"%s\"", display.getName());
							
							display.close();
							
						}
						
					}
					
				}
				
				this.displaysToInit.remove(display);
				
			}
			
		}
		
		for (Display display : this.displays)
		{
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
