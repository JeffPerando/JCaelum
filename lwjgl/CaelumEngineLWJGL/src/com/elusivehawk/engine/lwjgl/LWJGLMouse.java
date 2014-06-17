
package com.elusivehawk.engine.lwjgl;

import org.lwjgl.input.Mouse;
import com.elusivehawk.engine.core.EnumInputType;
import com.elusivehawk.engine.core.Input;
import com.elusivehawk.engine.util.storage.DirtableStorage;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class LWJGLMouse extends Input
{
	private final DirtableStorage<Boolean> grabMouse = new DirtableStorage<Boolean>(false);
	
	public LWJGLMouse()
	{
		super(EnumInputType.MOUSE);
		
	}
	
	@Override
	public boolean initiateInput()
	{
		try
		{
			Mouse.create();
			
			return true;
		}
		catch (Exception e){}
		
		return false;
	}
	
	@Override
	protected void updateInput()
	{
		Mouse.poll();
		
		if (this.grabMouse.isDirty())
		{
			Mouse.setGrabbed(this.grabMouse.get());
			
		}
		
		/*int buttons = Mouse.getButtonCount();
		
		for (int c = 0; c < buttons; c++)
		{
			this.bools.put(InputConst.MOUSE_CLICK | ((c + 1) << 16), Mouse.isButtonDown(c));
			
		}
		
		this.bools.put(InputConst.MOUSE_LOCK, Mouse.isGrabbed());
		
		this.integers.put(InputConst.MOUSE_X, Mouse.getX());
		this.integers.put(InputConst.MOUSE_Y, Mouse.getY());
		this.integers.put(InputConst.MOUSE_DX, Mouse.getDX());
		this.integers.put(InputConst.MOUSE_DY, Mouse.getDY());
		
		if (Mouse.hasWheel())
		{
			this.integers.put(InputConst.MOUSE_DWHEEL, Mouse.getDWheel());
			this.bools.put(InputConst.MOUSE_CWHEEL, false);//FIXME
			
		}*/
		
	}
	
	@Override
	public void cleanup()
	{
		Mouse.destroy();
		
	}
	
	@Override
	public void setFlag(String name, boolean value)
	{
		if ("lock".equalsIgnoreCase(name))
		{
			this.grabMouse.set(value);
			
		}
		
	}
	
}
