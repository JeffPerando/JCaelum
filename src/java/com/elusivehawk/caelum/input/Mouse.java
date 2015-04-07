
package com.elusivehawk.caelum.input;

import java.io.IOException;
import java.nio.DoubleBuffer;
import java.util.List;
import java.util.function.Consumer;
import org.lwjgl.glfw.GLFW;
import com.elusivehawk.caelum.render.Display;
import com.elusivehawk.caelum.render.tex.ColorFormat;
import com.elusivehawk.caelum.render.tex.ILegibleImage;
import com.elusivehawk.util.math.VectorF;
import com.elusivehawk.util.storage.BufferHelper;
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
	
	private final DoubleBuffer
				x = BufferHelper.createDoubleBuffer(1),
				y = BufferHelper.createDoubleBuffer(1);
	private long cursor = 0L;
	
	private final VectorF
				pos = (VectorF)new VectorF(2).setSync(),
				oldPos = (VectorF)new VectorF(2).setSync(),
				deltaPos = (VectorF)new VectorF(2).setSync();
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
	public void close() throws IOException
	{
		if (this.cursor != 0)
		{
			GLFW.glfwDestroyCursor(this.cursor);
			
			this.cursor = 0;
			
		}
		
	}
	
	@Override
	public void updateInput(double delta) throws Throwable
	{
		for (int c = 0; c < this.buttons.length; c++)
		{
			if (this.buttons[c] == EnumMouseClick.LIFTED)
			{
				synchronized (this)
				{
					this.buttons[c] = EnumMouseClick.UP;
					
				}
				
			}
			
		}
		
		long window = this.display.getId();
		
		this.x.position(0);
		this.y.position(0);
		
		GLFW.glfwGetCursorPos(window, this.x, this.y);
		
		this.setPosition((float)(this.x.get() / this.display.getWidth()), (float)(this.y.get() / this.display.getHeight()));
		
		for (int c = 0; c < InputConst.MOUSE_BUTTONS; c++)
		{
			if (GLFW.glfwGetMouseButton(window, c) == GLFW.GLFW_PRESS && !this.isButtonDown(c))
			{
				synchronized (this)
				{
					this.buttons[c] = EnumMouseClick.DOWN;
					
				}
				
				this.setIsDirty(true);
				
			}
			else if (this.isButtonDown(c))
			{
				synchronized (this)
				{
					this.buttons[c] = EnumMouseClick.LIFTED;
					
				}
				
				this.setIsDirty(true);
				
			}
			
		}
		
		if (this.icon.isDirty())
		{
			ILegibleImage img = this.icon.get();
			
			if (this.cursor != 0)
			{
				GLFW.glfwDestroyCursor(this.cursor);
				
			}
			
			if (img != null)
			{
				this.cursor = GLFW.glfwCreateCursor(img.toBytes(ColorFormat.RGBA), img.getWidth(), img.getHeight());
				
			}
			
			this.icon.setIsDirty(false);
			
		}
		
		if (this.grab.isDirty())
		{
			GLFW.glfwSetInputMode(window, GLFW.GLFW_CURSOR, this.grab.get() ? GLFW.GLFW_CURSOR_DISABLED : GLFW.GLFW_CURSOR_NORMAL);
			
			this.grab.setIsDirty(false);
			
		}
		
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
	
	public boolean isButtonDown(int button)
	{
		return this.buttons[button] == EnumMouseClick.DOWN;
	}
	
	public VectorF getPosition()
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
	
	public VectorF getDelta()
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
	
	public void setPosition(float x, float y)
	{
		this.oldPos.set(this.pos);
		this.pos.set(x, y);
		this.pos.sub(this.oldPos, this.deltaPos);
		
	}
	
}
