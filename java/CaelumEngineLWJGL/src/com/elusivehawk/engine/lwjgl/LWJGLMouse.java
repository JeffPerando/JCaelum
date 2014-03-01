
package com.elusivehawk.engine.lwjgl;

import org.lwjgl.input.Mouse;
import com.elusivehawk.engine.core.EnumInputType;
import com.elusivehawk.engine.core.IInputConst;
import com.elusivehawk.engine.core.Input;
import com.elusivehawk.engine.core.InputConst;
import com.elusivehawk.engine.util.DirtableStorage;

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
		
		this.bools.put(InputConst.MouseConst.MOUSE_LOCK.getValue(), Mouse.isGrabbed());
		
		this.integers.put(InputConst.MouseConst.MOUSE_X.getValue(), Mouse.getX());
		this.integers.put(InputConst.MouseConst.MOUSE_Y.getValue(), Mouse.getY());
		this.integers.put(InputConst.MouseConst.MOUSE_DX.getValue(), Mouse.getDX());
		this.integers.put(InputConst.MouseConst.MOUSE_DY.getValue(), Mouse.getDY());
		
	}
	
	@Override
	public void cleanup()
	{
		Mouse.destroy();
		
	}
	
	@Override
	public void setFlag(IInputConst name, boolean value)
	{
		if (name == InputConst.MouseConst.MOUSE_LOCK)
		{
			this.grabMouse.set(value);
			
		}
		
	}
	
}
