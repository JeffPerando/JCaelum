
package com.elusivehawk.caelum.lwjgl;

import java.util.List;
import org.lwjgl.system.glfw.GLFW;
import com.elusivehawk.caelum.Display;
import com.elusivehawk.caelum.input.EnumInputType;
import com.elusivehawk.caelum.input.Input;
import com.elusivehawk.caelum.input.InputManager;
import com.elusivehawk.caelum.input.Key;
import com.elusivehawk.caelum.input.KeyEvent;
import com.elusivehawk.caelum.input.PasteEvent;
import com.elusivehawk.util.storage.Tuple;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class LWJGLKeyboard extends Input
{
	public static final ImmutableList<Tuple<Integer, Key>> GLFW_ENUMS;
	
	private final List<Key> downKeys = Lists.newArrayList();
	
	static
	{
		List<Tuple<Integer, Key>> list = Lists.newArrayList();
		
		list.add(Tuple.create(GLFW.GLFW_KEY_A, Key.A));
		list.add(Tuple.create(GLFW.GLFW_KEY_B, Key.B));
		list.add(Tuple.create(GLFW.GLFW_KEY_C, Key.C));
		list.add(Tuple.create(GLFW.GLFW_KEY_D, Key.D));
		list.add(Tuple.create(GLFW.GLFW_KEY_E, Key.E));
		list.add(Tuple.create(GLFW.GLFW_KEY_F, Key.F));
		list.add(Tuple.create(GLFW.GLFW_KEY_G, Key.G));
		list.add(Tuple.create(GLFW.GLFW_KEY_H, Key.H));
		list.add(Tuple.create(GLFW.GLFW_KEY_I, Key.I));
		list.add(Tuple.create(GLFW.GLFW_KEY_J, Key.J));
		list.add(Tuple.create(GLFW.GLFW_KEY_K, Key.K));
		list.add(Tuple.create(GLFW.GLFW_KEY_L, Key.L));
		list.add(Tuple.create(GLFW.GLFW_KEY_M, Key.M));
		list.add(Tuple.create(GLFW.GLFW_KEY_N, Key.N));
		list.add(Tuple.create(GLFW.GLFW_KEY_O, Key.O));
		list.add(Tuple.create(GLFW.GLFW_KEY_P, Key.P));
		list.add(Tuple.create(GLFW.GLFW_KEY_Q, Key.Q));
		list.add(Tuple.create(GLFW.GLFW_KEY_R, Key.R));
		list.add(Tuple.create(GLFW.GLFW_KEY_S, Key.S));
		list.add(Tuple.create(GLFW.GLFW_KEY_T, Key.T));
		list.add(Tuple.create(GLFW.GLFW_KEY_U, Key.U));
		list.add(Tuple.create(GLFW.GLFW_KEY_V, Key.V));
		list.add(Tuple.create(GLFW.GLFW_KEY_W, Key.W));
		list.add(Tuple.create(GLFW.GLFW_KEY_X, Key.X));
		list.add(Tuple.create(GLFW.GLFW_KEY_Y, Key.Y));
		list.add(Tuple.create(GLFW.GLFW_KEY_Z, Key.Z));
		
		list.add(Tuple.create(GLFW.GLFW_KEY_1, Key.NUM_1));
		list.add(Tuple.create(GLFW.GLFW_KEY_2, Key.NUM_2));
		list.add(Tuple.create(GLFW.GLFW_KEY_3, Key.NUM_3));
		list.add(Tuple.create(GLFW.GLFW_KEY_4, Key.NUM_4));
		list.add(Tuple.create(GLFW.GLFW_KEY_5, Key.NUM_5));
		list.add(Tuple.create(GLFW.GLFW_KEY_6, Key.NUM_6));
		list.add(Tuple.create(GLFW.GLFW_KEY_7, Key.NUM_7));
		list.add(Tuple.create(GLFW.GLFW_KEY_8, Key.NUM_8));
		list.add(Tuple.create(GLFW.GLFW_KEY_9, Key.NUM_9));
		list.add(Tuple.create(GLFW.GLFW_KEY_0, Key.NUM_0));
		
		list.add(Tuple.create(GLFW.GLFW_KEY_LEFT_ALT, Key.ALT));
		list.add(Tuple.create(GLFW.GLFW_KEY_RIGHT_ALT, Key.ALT));
		list.add(Tuple.create(GLFW.GLFW_KEY_BACKSPACE, Key.BACKSPACE));
		list.add(Tuple.create(GLFW.GLFW_KEY_LEFT_CONTROL, Key.CONTROL));
		list.add(Tuple.create(GLFW.GLFW_KEY_RIGHT_CONTROL, Key.CONTROL));
		list.add(Tuple.create(GLFW.GLFW_KEY_DELETE, Key.DELETE));
		list.add(Tuple.create(GLFW.GLFW_KEY_END, Key.END));
		list.add(Tuple.create(GLFW.GLFW_KEY_ESCAPE, Key.ESCAPE));
		list.add(Tuple.create(GLFW.GLFW_KEY_HOME, Key.HOME));
		list.add(Tuple.create(GLFW.GLFW_KEY_INSERT, Key.INSERT));
		list.add(Tuple.create(GLFW.GLFW_KEY_PAGE_UP, Key.PAGE_UP));
		list.add(Tuple.create(GLFW.GLFW_KEY_PAGE_DOWN, Key.PAGE_DOWN));
		list.add(Tuple.create(GLFW.GLFW_KEY_LEFT_SHIFT, Key.SHIFT));
		list.add(Tuple.create(GLFW.GLFW_KEY_RIGHT_SHIFT, Key.SHIFT));
		list.add(Tuple.create(GLFW.GLFW_KEY_LEFT_SUPER, Key.START));
		list.add(Tuple.create(GLFW.GLFW_KEY_RIGHT_SUPER, Key.START));
		
		list.add(Tuple.create(GLFW.GLFW_KEY_F1, Key.F1));
		list.add(Tuple.create(GLFW.GLFW_KEY_F2, Key.F2));
		list.add(Tuple.create(GLFW.GLFW_KEY_F3, Key.F3));
		list.add(Tuple.create(GLFW.GLFW_KEY_F4, Key.F4));
		list.add(Tuple.create(GLFW.GLFW_KEY_F5, Key.F5));
		list.add(Tuple.create(GLFW.GLFW_KEY_F6, Key.F6));
		list.add(Tuple.create(GLFW.GLFW_KEY_F7, Key.F7));
		list.add(Tuple.create(GLFW.GLFW_KEY_F8, Key.F8));
		list.add(Tuple.create(GLFW.GLFW_KEY_F9, Key.F9));
		list.add(Tuple.create(GLFW.GLFW_KEY_F10, Key.F10));
		list.add(Tuple.create(GLFW.GLFW_KEY_F11, Key.F11));
		list.add(Tuple.create(GLFW.GLFW_KEY_F12, Key.F12));
		list.add(Tuple.create(GLFW.GLFW_KEY_F13, Key.F13));
		list.add(Tuple.create(GLFW.GLFW_KEY_F14, Key.F14));
		list.add(Tuple.create(GLFW.GLFW_KEY_F15, Key.F15));
		list.add(Tuple.create(GLFW.GLFW_KEY_F16, Key.F16));
		list.add(Tuple.create(GLFW.GLFW_KEY_F17, Key.F17));
		list.add(Tuple.create(GLFW.GLFW_KEY_F18, Key.F18));
		list.add(Tuple.create(GLFW.GLFW_KEY_F19, Key.F19));
		
		list.add(Tuple.create(GLFW.GLFW_KEY_BACKSLASH, Key.BACKSLASH));
		list.add(Tuple.create(GLFW.GLFW_KEY_LEFT_BRACKET, Key.BRACKET_LEFT));
		list.add(Tuple.create(GLFW.GLFW_KEY_RIGHT_BRACKET, Key.BRACKET_RIGHT));
		list.add(Tuple.create(GLFW.GLFW_KEY_COMMA, Key.COMMA));
		list.add(Tuple.create(GLFW.GLFW_KEY_MINUS, Key.DASH));
		list.add(Tuple.create(GLFW.GLFW_KEY_EQUAL, Key.EQUALS));
		list.add(Tuple.create(GLFW.GLFW_KEY_GRAVE_ACCENT, Key.GRAVE));
		list.add(Tuple.create(GLFW.GLFW_KEY_PERIOD, Key.PERIOD));
		list.add(Tuple.create(GLFW.GLFW_KEY_SEMICOLON, Key.SEMICOLON));
		list.add(Tuple.create(GLFW.GLFW_KEY_SLASH, Key.SLASH));
		
		list.add(Tuple.create(GLFW.GLFW_KEY_DOWN, Key.ARROW_DOWN));
		list.add(Tuple.create(GLFW.GLFW_KEY_LEFT, Key.ARROW_LEFT));
		list.add(Tuple.create(GLFW.GLFW_KEY_RIGHT, Key.ARROW_RIGHT));
		list.add(Tuple.create(GLFW.GLFW_KEY_UP, Key.ARROW_UP));
		list.add(Tuple.create(GLFW.GLFW_KEY_CAPS_LOCK, Key.CAPS_LOCK));
		list.add(Tuple.create(GLFW.GLFW_KEY_ENTER, Key.ENTER));
		list.add(Tuple.create(GLFW.GLFW_KEY_NUM_LOCK, Key.NUM_LOCK));
		list.add(Tuple.create(GLFW.GLFW_KEY_SPACE, Key.SPACE));
		list.add(Tuple.create(GLFW.GLFW_KEY_TAB, Key.TAB));
		
		GLFW_ENUMS = ImmutableList.copyOf(list);
		
	}
	
	public LWJGLKeyboard(InputManager mgr)
	{
		super(mgr);
		
	}
	
	@Override
	public void close(){}
	
	@Override
	public EnumInputType getType()
	{
		return EnumInputType.KEYBOARD;
	}
	
	@Override
	protected void pollInput(double delta, Display display)
	{
		long window = ((LWJGLDisplay)display.getImpl()).getWindowId();
		
		String paste = GLFW.glfwGetClipboardString(window);
		
		if (paste != null)
		{
			this.manager.queueInputEvent(new PasteEvent(paste));
			
		}
		
		for (Tuple<Integer, Key> t : GLFW_ENUMS)
		{
			int status = GLFW.glfwGetKey(window, t.one);
			boolean down = (status == GLFW.GLFW_PRESS);
			boolean downPrev = false;
			
			if (down || (downPrev = this.downKeys.contains(t.two)))
			{
				if (down)
				{
					if (!downPrev)
					{
						this.downKeys.add(t.two);
						
					}
					
				}
				else
				{
					this.downKeys.remove(t.two);
					
				}
				
				this.manager.queueInputEvent(new KeyEvent(t.two, down, downPrev));
				
			}
			
		}
		
	}
	
}
