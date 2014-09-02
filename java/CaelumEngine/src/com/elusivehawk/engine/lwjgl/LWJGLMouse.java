
package com.elusivehawk.engine.lwjgl;

import static com.elusivehawk.engine.input.EnumMouseClick.DOWN;
import static com.elusivehawk.engine.input.EnumMouseClick.DRAG;
import static com.elusivehawk.engine.input.EnumMouseClick.UP;
import org.lwjgl.input.Mouse;
import com.elusivehawk.engine.CaelumEngine;
import com.elusivehawk.engine.CaelumException;
import com.elusivehawk.engine.input.EnumMouseClick;
import com.elusivehawk.engine.render.IDisplay;
import com.elusivehawk.util.Logger;
import com.elusivehawk.util.math.Vector;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class LWJGLMouse extends com.elusivehawk.engine.input.Mouse
{
	protected EnumMouseClick[]
			buttons = null,
			oldButtons = null;
	protected final Vector mousePos, mousePosDelta;
			
	protected float wheel = 0f;
	
	@SuppressWarnings("unqualified-field-access")
	public LWJGLMouse()
	{
		mousePos = new Vector(0f, 0f);
		mousePosDelta = new Vector(0f, 0f);
		
	}
	
	@Override
	public void close()
	{
		Mouse.destroy();
		
	}
	
	@Override
	public Vector getMousePos()
	{
		return this.mousePos;
	}
	
	@Override
	public Vector getMousePosDelta()
	{
		return this.mousePosDelta;
	}
	
	@Override
	public EnumMouseClick getClickStatus(int button)
	{
		return this.buttons[button];
	}
	
	@Override
	public EnumMouseClick getOldClickStatus(int button)
	{
		return this.oldButtons[button];
	}
	
	@Override
	public int getButtonCount()
	{
		return this.buttons.length;
	}
	
	@Override
	public float getWheelScroll()
	{
		return this.wheel;
	}
	
	@Override
	public void setGrabMouse(boolean grab)
	{
		Mouse.setGrabbed(grab);
		
	}
	
	@Override
	public boolean initiateInput()
	{
		try
		{
			Mouse.create();
			
		}
		catch (Exception e)
		{
			Logger.log().err(e);
			
			return false;
		}
		
		if (!Mouse.isCreated())
		{
			return false;
		}
		
		this.buttons = new EnumMouseClick[Mouse.getButtonCount()];
		this.oldButtons = new EnumMouseClick[Mouse.getButtonCount()];
		
		for (int b = 0; b < this.buttons.length; b++)
		{
			this.buttons[b] = EnumMouseClick.UP;
			this.oldButtons[b] = EnumMouseClick.UP;
			
		}
		
		return true;
	}
	
	@Override
	protected void poll()
	{
		if (!Mouse.isCreated())
		{
			throw new CaelumException("Cannot poll keyboard: It wasn't created!");
		}
		
		IDisplay display = CaelumEngine.display();
		int b;
		
		while (Mouse.next())
		{
			this.mousePos.set(Mouse.getEventX() / display.getWidth(), Mouse.getEventY() / display.getHeight());
			this.mousePosDelta.set(Mouse.getEventDX() / display.getWidth(), Mouse.getEventDY() / display.getHeight());
			this.wheel = Mouse.getEventDWheel() / display.getHeight();
			
			EnumMouseClick cur = this.buttons[b = Mouse.getEventButton()];
			
			if (Mouse.getEventButtonState())
			{
				switch (cur)
				{
					case DOWN:
						if (this.mousePos.isDirty())
						{
							this.oldButtons[b] = DOWN;
							this.buttons[b] = DRAG;
						};
						break;
					case UP:
						this.oldButtons[b] = UP;
						this.buttons[b] = DOWN;
						break;
				}
				
			}
			else
			{
				this.oldButtons[b] = cur;
				this.buttons[b] = UP;
				
			}
			
		}
		
	}
	
	@Override
	protected void postUpdate()
	{
		this.mousePos.setIsDirty(false);
		this.mousePosDelta.setIsDirty(false);
		
	}
	
}
