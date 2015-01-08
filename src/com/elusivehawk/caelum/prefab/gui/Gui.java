
package com.elusivehawk.caelum.prefab.gui;

import java.util.List;
import com.elusivehawk.caelum.input.IInputListener;
import com.elusivehawk.caelum.input.InputEvent;
import com.elusivehawk.caelum.input.MouseEvent;
import com.elusivehawk.util.IDirty;
import com.elusivehawk.util.IPopulator;
import com.google.common.collect.Lists;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class Gui implements IInputListener, IDirty
{
	public static final int
					DEFAULT = 0,
					HIGHLIGHTED = 1,
					PUSHED = 2,
					INACTIVE = 3,
					STATE_COUNT = 4;
	
	private final List<IGuiComponent> components = Lists.newArrayList();
	
	private IGuiComponent active = null, lastClicked = null;
	private boolean dirty = true, mouseDown = false;
	
	public Gui(){}
	
	public Gui(IPopulator<Gui> pop)
	{
		this();
		
		pop.populate(this);
		
	}
	
	@Override
	public boolean isDirty()
	{
		return this.dirty;
	}

	@Override
	public synchronized void setIsDirty(boolean b)
	{
		this.dirty = b;
		
	}
	
	/*@Override
	public void preRender(RenderContext rcon, double delta)
	{
		if (this.isDirty())
		{
			this.components.forEach(((comp) ->
			{
				comp.drawComponent(this.canvas, (comp.isActive() ? (comp == this.active ? (this.mouseDown ? PUSHED : HIGHLIGHTED) : DEFAULT) : INACTIVE));
				
			}));
			
			this.setIsDirty(false);
			
		}
		
		this.canvas.preRender(rcon, delta);
		
	}
	
	@Override
	public void postRender(RenderContext rcon)
	{
		this.canvas.postRender(rcon);
		
	}
	
	@Override
	public void render(RenderContext rcon) throws RenderException
	{
		this.canvas.render(rcon);
		
	}*/
	
	@Override
	public void onInputReceived(InputEvent event, double delta)
	{
		if (this.components.isEmpty())
		{
			return;
		}
		
		if (event instanceof MouseEvent)
		{
			MouseEvent me = (MouseEvent)event;
			
			IGuiComponent a = null;
			
			for (IGuiComponent comp : this.components)
			{
				if (!comp.isActive())
				{
					continue;
				}
				
				if (comp.getBounds().within(me.pos))
				{
					a = comp;
					break;
				}
				
			}
			
			if (this.active != a)
			{
				synchronized (this)
				{
					this.active = a;
					
				}
				
				this.setIsDirty(true);
				
			}
			
			if (this.active != null)
			{
				for (int c = 0; c < me.status.length; c++)
				{
					switch (me.status[c])
					{
						case UP: continue;
						case LIFTED:
						{
							this.active.onClicked(event.display, c);
							this.setIsDirty(true);
							
							synchronized (this)
							{
								this.lastClicked = this.active;
								this.mouseDown = false;
								
							}
							
						}; break;
						case DOWN:
						{
							synchronized (this){this.mouseDown = true;}
							
							this.setIsDirty(true);
							
						}; break;
						
					}
					
				}
				
			}
			
		}
		
	}
	
	public IGuiComponent getLastClicked()
	{
		return this.lastClicked;
	}
	
	public Gui addComponent(IGuiComponent comp)
	{
		assert comp != null;
		
		this.components.add(comp);
		this.setIsDirty(true);
		
		return this;
	}
	
}
