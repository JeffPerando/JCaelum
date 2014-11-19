
package com.elusivehawk.caelum.lwjgl;

import java.io.IOException;
import java.nio.DoubleBuffer;
import org.lwjgl.system.glfw.GLFW;
import com.elusivehawk.caelum.Display;
import com.elusivehawk.caelum.input.EnumInputType;
import com.elusivehawk.caelum.input.EnumMouseClick;
import com.elusivehawk.caelum.input.Input;
import com.elusivehawk.caelum.input.InputManager;
import com.elusivehawk.caelum.input.MouseEvent;
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
	public static final int MOUSE_BUTTONS = 8;
	
	//private long cursor = 0; TODO Write custom cursor system
	
	private final EnumMouseClick[] statuses = new EnumMouseClick[MOUSE_BUTTONS];
	private final boolean[] buttonsOld = new boolean[MOUSE_BUTTONS];
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
	protected void pollInput(double delta, Display display)
	{
		long window = ((LWJGLDisplay)display.getImpl()).getWindowId();
		
		DoubleBuffer posBuf = BufferHelper.createDoubleBuffer(2);
		
		GLFW.glfwGetCursorPos(window, posBuf, posBuf);
		
		this.pos.set((float)posBuf.get(), (float)posBuf.get());
		
		for (int c = 0; c < MOUSE_BUTTONS; c++)
		{
			boolean down = (GLFW.glfwGetMouseButton(window, c) == GLFW.GLFW_PRESS);
			EnumMouseClick status = EnumMouseClick.UP;
			
			if (down)
			{
				if (this.buttonsOld[c])
				{
					if (this.pos.isDirty())
					{
						status = EnumMouseClick.DRAG;
						
					}
					else
					{
						status = EnumMouseClick.DOWN;
						
					}
					
				}
				else
				{
					status = EnumMouseClick.DOWN;
					
				}
				
			}
			else if (this.buttonsOld[c])
			{
				status = EnumMouseClick.LIFTED;
				
			}
			
			this.statuses[c] = status;
			this.buttonsOld[c] = down;
			
		}
		
		this.manager.queueInputEvent(new MouseEvent(this.pos, this.posOld, this.statuses));
		
		this.posOld.set(this.pos);
		this.pos.setIsDirty(false);
		
	}
	
}
