
package com.elusivehawk.caelum.prefab.gui;

import java.util.List;
import com.elusivehawk.caelum.input.IInputListener;
import com.elusivehawk.caelum.input.Input;
import com.elusivehawk.caelum.input.InputConst;
import com.elusivehawk.caelum.input.Mouse;
import com.elusivehawk.caelum.render.Canvas;
import com.elusivehawk.util.Dirtable;
import com.elusivehawk.util.IPopulator;
import com.google.common.collect.Lists;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class Gui extends Dirtable implements IInputListener
{
	public static final int
					DEFAULT = 0,
					HIGHLIGHTED = 1,
					PUSHED = 2,
					INACTIVE = 3,
					STATE_COUNT = 4;
	
	private final List<IGuiComponent> components = Lists.newArrayList();
	private final boolean[] isMouseDown = new boolean[InputConst.MOUSE_BUTTONS];
	
	private IGuiComponent active = null, lastClicked = null;
	
	public Gui(){}
	
	public Gui(IPopulator<Gui> pop)
	{
		this();
		
		pop.populate(this);
		
	}
	
	@Override
	public void onInputReceived(Input input, double delta)
	{
		if (this.components.isEmpty())
		{
			return;
		}
		
		if (input instanceof Mouse)
		{
			Mouse m = (Mouse)input;
			
			IGuiComponent a = null;
			
			for (IGuiComponent comp : this.components)
			{
				if (!comp.isActive())
				{
					continue;
				}
				
				if (comp.getBounds().within(m.getPosition()))
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
				for (int c = 0; c < InputConst.MOUSE_BUTTONS; c++)
				{
					switch (m.getStatus(c))
					{
						case LIFTED:
						{
							this.active.onClicked(m.getWindow(), c);
							this.setIsDirty(true);
							
							synchronized (this)
							{
								this.lastClicked = this.active;
								this.isMouseDown[c] = false;
								
							}
							
						}; break;
						case DOWN:
						{
							synchronized (this)
							{
								this.isMouseDown[c] = true;
								
							}
							
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
	
	public void draw(Canvas canvas)
	{
		if (this.isDirty())
		{
			this.components.forEach(((comp) ->
			{
				comp.drawComponent(canvas, (comp.isActive() ? (comp == this.active ? (this.isMouseDown[InputConst.MOUSE_LEFT] ? PUSHED : HIGHLIGHTED) : DEFAULT) : INACTIVE));
				
			}));
			
			this.setIsDirty(false);
			
		}
		
	}
	
}
