
package com.elusivehawk.caelum.lwjgl;

import java.io.IOException;
import java.nio.DoubleBuffer;
import org.lwjgl.system.glfw.GLFW;
import com.elusivehawk.caelum.CaelumException;
import com.elusivehawk.caelum.Display;
import com.elusivehawk.caelum.input.EnumInputType;
import com.elusivehawk.caelum.input.EnumMouseClick;
import com.elusivehawk.caelum.input.Input;
import com.elusivehawk.caelum.input.InputConst;
import com.elusivehawk.caelum.input.InputManager;
import com.elusivehawk.caelum.input.MouseEvent;
import com.elusivehawk.util.Logger;
import com.elusivehawk.util.math.MathConst;
import com.elusivehawk.util.math.Vector;
import com.elusivehawk.util.storage.BufferHelper;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class LWJGLMouse extends Input
{
	//private long cursor = 0; TODO Write custom cursor system
	
	private final EnumMouseClick[] buttons = new EnumMouseClick[InputConst.MOUSE_BUTTONS];
	private final EnumMouseClick[] buttonsOld = new EnumMouseClick[InputConst.MOUSE_BUTTONS];
	private final DoubleBuffer
					x = BufferHelper.createDoubleBuffer(1),
					y = BufferHelper.createDoubleBuffer(1);
	private final Vector
					pos = new Vector(2),
					posOld = new Vector(2);
	
	public LWJGLMouse(InputManager mgr)
	{
		super(mgr);
		
	}
	
	@Override
	public void close() throws IOException
	{
		//GLFW.glfwDestroyCursor(this.cursor);
		
	}
	
	@Override
	public EnumInputType getType()
	{
		return EnumInputType.MOUSE;
	}
	
	@Override
	protected void pollInput(Display display)
	{
		long window = ((LWJGLDisplayImpl)display.getImpl()).getWindowId();
		
		this.x.clear();
		this.y.clear();
		
		if (display.getHeight() == 0 || display.getWidth() == 0)
		{
			throw new CaelumException("WHY WOULD YOU TRY TO UPDATE AN ESSENTIALLY NULL DISPLAY?!");
		}
		
		GLFW.glfwGetCursorPos(window, this.x, this.y);
		
		this.pos.set(MathConst.X, (float)((int)this.x.get() / display.getWidth()));
		this.pos.set(MathConst.Y, (float)((int)this.y.get() / display.getHeight()));
		
		boolean emit = this.pos.isDirty();
		
		if (emit)
		{
			Logger.log().verbose("WE GOT MOVIE SIGN!!");
			
		}
		
		for (int c = 0; c < InputConst.MOUSE_BUTTONS; c++)
		{
			boolean down = (GLFW.glfwGetMouseButton(window, c) == GLFW.GLFW_PRESS);
			EnumMouseClick status = EnumMouseClick.UP;
			
			if (down)
			{
				status = EnumMouseClick.DOWN;
				
			}
			else if (this.buttonsOld[c] == EnumMouseClick.DOWN)
			{
				status = EnumMouseClick.LIFTED;
				
			}
			
			if (!emit)
			{
				if (this.buttons[c] != status)
				{
					emit = true;
					
					Logger.log().verbose("WE GOT MOVIE SIGN #%s!!", c);
					
				}
				
			}
			
			this.buttonsOld[c] = this.buttons[c];
			this.buttons[c] = status;
			
		}
		
		if (emit)
		{
			this.manager.queueInputEvent(new MouseEvent(display, this.pos, this.posOld, this.buttons, this.buttonsOld));
			
		}
		
		this.posOld.set(this.pos);
		this.pos.setIsDirty(false);
		
	}
	
}
