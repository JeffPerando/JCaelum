
package com.elusivehawk.caelum.prefab.gui;

import java.util.List;
import com.elusivehawk.caelum.input.IInputListener;
import com.elusivehawk.caelum.input.InputEvent;
import com.elusivehawk.caelum.input.MouseEvent;
import com.elusivehawk.caelum.render.Canvas;
import com.elusivehawk.caelum.render.IRenderable;
import com.elusivehawk.caelum.render.RenderContext;
import com.elusivehawk.caelum.render.RenderException;
import com.elusivehawk.caelum.render.tex.Material;
import com.elusivehawk.util.IDirty;
import com.google.common.collect.Lists;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class Gui implements IInputListener, IRenderable, IDirty
{
	public static final int
					DEFAULT = 0,
					HIGHLIGHTED = 1,
					PUSHED = 2;
	
	private final List<IGuiComponent> components = Lists.newArrayList();
	
	private final Canvas canvas;
	
	private IGuiComponent active = null, lastClicked = null;
	private boolean dirty = true, mouseDown = false;
	
	@SuppressWarnings("unqualified-field-access")
	public Gui(Canvas cvs)
	{
		canvas = cvs;
		
	}
	
	@Override
	public boolean isDirty()
	{
		return this.dirty;
	}

	@Override
	public void setIsDirty(boolean b)
	{
		if (b != this.dirty)
		{
			synchronized (this)
			{
				this.dirty = b;
				
			}
			
		}
		
	}
	
	@Override
	public void preRender(RenderContext rcon, double delta)
	{
		if (this.isDirty())
		{
			this.canvas.clear();
			this.components.forEach(((comp) -> {comp.drawComponent(this.canvas, comp == this.active ? (this.mouseDown ? PUSHED : HIGHLIGHTED) : DEFAULT);}));
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
		
	}
	
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
			
			for (IGuiComponent comp : this.components)
			{
				if (comp.getBounds().within(me.pos))
				{
					this.active = comp;
					break;
				}
				
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
							
							break;
						}
						case DOWN:
						{
							synchronized (this){this.mouseDown = true;}
							
							this.setIsDirty(true);
							
							break;
						}
						
					}
					
				}
				
			}
			
		}
		
	}
	
	public IGuiComponent getLastClicked()
	{
		return this.lastClicked;
	}
	
	public Gui addComponent(IGuiComponent component)
	{
		this.components.add(component);
		this.setIsDirty(true);
		
		return this;
	}
	
	public Gui setMaterial(int state, Material m)
	{
		assert m != null;
		
		this.canvas.addMaterials(m);
		
		return this;
	}
	
}
