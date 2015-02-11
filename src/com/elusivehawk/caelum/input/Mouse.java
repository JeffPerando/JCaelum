
package com.elusivehawk.caelum.input;

import java.util.List;
import java.util.function.Consumer;
import com.elusivehawk.caelum.Display;
import com.elusivehawk.caelum.render.tex.ILegibleImage;
import com.elusivehawk.util.math.Vector;
import com.elusivehawk.util.storage.DirtableStorage;
import com.elusivehawk.util.storage.Tuple;
import com.google.common.collect.Lists;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class Mouse extends Input
{
	private final List<Tuple<int[], Consumer<Mouse>>> hooks = Lists.newArrayList();
	private final EnumMouseClick[] buttons = new EnumMouseClick[InputConst.MOUSE_BUTTONS];
	private final Vector
				pos = new Vector(2).setSync(),
				oldPos = new Vector(2).setSync(),
				deltaPos = new Vector(2).setSync();
	private final DirtableStorage<ILegibleImage> icon = new DirtableStorage<ILegibleImage>().setSync();
	private final DirtableStorage<Boolean> grab = new DirtableStorage<Boolean>().setSync();
	
	@SuppressWarnings("unqualified-field-access")
	public Mouse(Display screen)
	{
		super(screen);
		
		for (int c = 0; c < buttons.length; c++)
		{
			buttons[c] = EnumMouseClick.UP;
			
		}
		
	}
	
	@Override
	public void updateInput(double delta) throws Throwable
	{
		for (int c = 0; c < this.buttons.length; c++)
		{
			if (this.buttons[c] == EnumMouseClick.LIFTED)
			{
				this.buttons[c] = EnumMouseClick.UP;
				
			}
			
		}
		
		this.updateImpl(delta);
		
	}
	
	@Override
	public void triggerHooks(double delta)
	{
		this.hooks.forEach(((tuple) ->
		{
			for (int button : tuple.one)
			{
				if (this.getStatus(button) != EnumMouseClick.DOWN)
				{
					return;
				}
				
			}
			
			tuple.two.accept(this);
			
		}));
		
		super.triggerHooks(delta);
		
	}
	
	public void addHook(Consumer<Mouse> hook, int... buttons)
	{
		this.hooks.add(Tuple.create(buttons, hook));
		
	}
	
	public EnumMouseClick getStatus(int button)
	{
		return this.buttons[button];
	}
	
	public Vector getPosition()
	{
		return this.pos;
	}
	
	public float getX()
	{
		return this.pos.get(0);
	}
	
	public float getY()
	{
		return this.pos.get(1);
	}
	
	public Vector getDelta()
	{
		return this.deltaPos;
	}
	
	public float getXD()
	{
		return this.deltaPos.get(0);
	}
	
	public float getYD()
	{
		return this.deltaPos.get(1);
	}
	
	public ILegibleImage getIcon()
	{
		return this.icon.get();
	}
	
	public boolean getGrab()
	{
		return this.grab.get();
	}
	
	public DirtableStorage<ILegibleImage> getIconStorage()
	{
		return this.icon;
	}
	
	public DirtableStorage<Boolean> getGrabStorage()
	{
		return this.grab;
	}
	
	public void onButtonClicked(int button)
	{
		if (this.buttons[button] != EnumMouseClick.DOWN)
		{
			synchronized (this)
			{
				this.buttons[button] = EnumMouseClick.DOWN;
				
			}
			
			this.setIsDirty(true);
			
		}
		
	}
	
	public void onButtonRaised(int button)
	{
		if (this.buttons[button] == EnumMouseClick.DOWN)
		{
			synchronized (this)
			{
				this.buttons[button] = EnumMouseClick.LIFTED;
				
			}
			
			this.setIsDirty(true);
			
		}
		
	}
	
	public void setPosition(float x, float y)
	{
		this.oldPos.set(this.pos);
		this.pos.set(x, y);
		this.pos.sub(this.oldPos, this.deltaPos);
		
	}
	
}
