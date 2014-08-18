
package com.elusivehawk.engine.lwjgl;

import static com.elusivehawk.engine.EnumMouseClick.*;
import org.lwjgl.input.Mouse;
import com.elusivehawk.engine.CaelumEngine;
import com.elusivehawk.engine.EnumMouseClick;
import com.elusivehawk.engine.MouseInput;
import com.elusivehawk.engine.render.IDisplay;
import com.elusivehawk.util.math.Vector;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class LWJGLMouse extends MouseInput
{
	protected EnumMouseClick[]
			buttons = null,
			oldButtons = null;
	protected Vector
			mousePos = new Vector(0f, 0f),
			mousePosDelta = new Vector(0f, 0f);
	protected float wheel = 0f;
	
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
			CaelumEngine.log().err(e);
			
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
		Mouse.poll();
		Mouse.updateCursor();
		
		IDisplay display = CaelumEngine.getDisplay();
		
		this.mousePos.set(Mouse.getX() / display.getWidth(), Mouse.getY() / display.getHeight());
		this.mousePosDelta.set(Mouse.getDX() / display.getWidth(), Mouse.getDY() / display.getHeight());
		this.wheel = Mouse.getDWheel() / display.getHeight();
		
		EnumMouseClick cur;
		boolean click;
		
		for (int b = 0; b < this.buttons.length; b++)
		{
			cur = this.buttons[b];
			
			click = Mouse.isButtonDown(b);
			
			if (click)
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
