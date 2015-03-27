
package com.elusivehawk.caelum.lwjgl;

import java.nio.DoubleBuffer;
import org.lwjgl.glfw.GLFW;
import com.elusivehawk.caelum.input.IInputImpl;
import com.elusivehawk.caelum.input.Input;
import com.elusivehawk.caelum.input.InputConst;
import com.elusivehawk.caelum.input.Mouse;
import com.elusivehawk.caelum.render.Display;
import com.elusivehawk.caelum.render.tex.ColorFormat;
import com.elusivehawk.caelum.render.tex.ILegibleImage;
import com.elusivehawk.util.storage.BufferHelper;
import com.elusivehawk.util.storage.DirtableStorage;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class LWJGLMouseImpl implements IInputImpl
{
	private final boolean[] buttonsDown = new boolean[InputConst.MOUSE_BUTTONS];
	private final DoubleBuffer
					x = BufferHelper.createDoubleBuffer(1),
					y = BufferHelper.createDoubleBuffer(1);
	
	private long cursor = 0L;
	
	@Override
	public void close()
	{
		if (this.cursor != 0)
		{
			GLFW.glfwDestroyCursor(this.cursor);
			
			this.cursor = 0;
			
		}
		
	}
	
	@Override
	public void updateInput(double delta, Input input)
	{
		Display display = input.getDisplay();
		Mouse mouse = (Mouse)input;
		
		long window = ((LWJGLDisplayImpl)display.getImpl()).getWindowId();
		
		this.x.position(0);
		this.y.position(0);
		
		GLFW.glfwGetCursorPos(window, this.x, this.y);
		
		mouse.setPosition((float)(this.x.get() / display.getWidth()), (float)(this.y.get() / display.getHeight()));
		
		for (int c = 0; c < InputConst.MOUSE_BUTTONS; c++)
		{
			if (GLFW.glfwGetMouseButton(window, c) == GLFW.GLFW_PRESS && !this.buttonsDown[c])
			{
				mouse.onButtonClicked(c);
				
				this.buttonsDown[c] = true;
				
			}
			else if (this.buttonsDown[c])
			{
				mouse.onButtonRaised(c);
				
				this.buttonsDown[c] = false;
				
			}
			
		}
		
		DirtableStorage<ILegibleImage> icon = mouse.getIconStorage();
		
		if (icon.isDirty())
		{
			ILegibleImage img = icon.get();
			
			if (this.cursor != 0)
			{
				GLFW.glfwDestroyCursor(this.cursor);
				
			}
			
			if (img != null)
			{
				this.cursor = GLFW.glfwCreateCursor(img.toBytes(ColorFormat.RGBA), img.getWidth(), img.getHeight());
				
			}
			
			icon.setIsDirty(false);
			
		}
		
		DirtableStorage<Boolean> grab = mouse.getGrabStorage();
		
		if (grab.isDirty())
		{
			GLFW.glfwSetInputMode(window, GLFW.GLFW_CURSOR, grab.get() ? GLFW.GLFW_CURSOR_DISABLED : GLFW.GLFW_CURSOR_NORMAL);
			
			grab.setIsDirty(false);
			
		}
		
	}
	
}
