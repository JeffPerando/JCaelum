
package com.elusivehawk.engine.lwjgl;

import java.util.List;
import org.lwjgl.input.Keyboard;
import com.elusivehawk.engine.Key;
import com.elusivehawk.util.Logger;
import com.google.common.collect.Lists;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class LWJGLKeyboard extends com.elusivehawk.engine.Keyboard
{
	public static final Key[] LWJGL_TO_ENUM = new Key[Keyboard.KEYBOARD_SIZE];
	
	protected final boolean[] downKeys = new boolean[LWJGL_TO_ENUM.length];
	protected final List<Key> downKeyList = Lists.newArrayList();
	
	static
	{
		LWJGL_TO_ENUM[Keyboard.KEY_A] = Key.A;
		LWJGL_TO_ENUM[Keyboard.KEY_B] = Key.B;
		LWJGL_TO_ENUM[Keyboard.KEY_C] = Key.C;
		LWJGL_TO_ENUM[Keyboard.KEY_D] = Key.D;
		LWJGL_TO_ENUM[Keyboard.KEY_E] = Key.E;
		LWJGL_TO_ENUM[Keyboard.KEY_F] = Key.F;
		LWJGL_TO_ENUM[Keyboard.KEY_G] = Key.G;
		LWJGL_TO_ENUM[Keyboard.KEY_H] = Key.H;
		LWJGL_TO_ENUM[Keyboard.KEY_I] = Key.I;
		LWJGL_TO_ENUM[Keyboard.KEY_J] = Key.J;
		LWJGL_TO_ENUM[Keyboard.KEY_K] = Key.K;
		LWJGL_TO_ENUM[Keyboard.KEY_L] = Key.L;
		LWJGL_TO_ENUM[Keyboard.KEY_M] = Key.M;
		LWJGL_TO_ENUM[Keyboard.KEY_N] = Key.N;
		LWJGL_TO_ENUM[Keyboard.KEY_O] = Key.O;
		LWJGL_TO_ENUM[Keyboard.KEY_P] = Key.P;
		LWJGL_TO_ENUM[Keyboard.KEY_Q] = Key.Q;
		LWJGL_TO_ENUM[Keyboard.KEY_R] = Key.R;
		LWJGL_TO_ENUM[Keyboard.KEY_S] = Key.S;
		LWJGL_TO_ENUM[Keyboard.KEY_T] = Key.T;
		LWJGL_TO_ENUM[Keyboard.KEY_U] = Key.U;
		LWJGL_TO_ENUM[Keyboard.KEY_V] = Key.V;
		LWJGL_TO_ENUM[Keyboard.KEY_W] = Key.W;
		LWJGL_TO_ENUM[Keyboard.KEY_X] = Key.X;
		LWJGL_TO_ENUM[Keyboard.KEY_Y] = Key.Y;
		LWJGL_TO_ENUM[Keyboard.KEY_Z] = Key.Z;
		
		LWJGL_TO_ENUM[Keyboard.KEY_1] = Key.NUM_1;
		LWJGL_TO_ENUM[Keyboard.KEY_2] = Key.NUM_2;
		LWJGL_TO_ENUM[Keyboard.KEY_3] = Key.NUM_3;
		LWJGL_TO_ENUM[Keyboard.KEY_4] = Key.NUM_4;
		LWJGL_TO_ENUM[Keyboard.KEY_5] = Key.NUM_5;
		LWJGL_TO_ENUM[Keyboard.KEY_6] = Key.NUM_6;
		LWJGL_TO_ENUM[Keyboard.KEY_7] = Key.NUM_7;
		LWJGL_TO_ENUM[Keyboard.KEY_8] = Key.NUM_8;
		LWJGL_TO_ENUM[Keyboard.KEY_9] = Key.NUM_9;
		LWJGL_TO_ENUM[Keyboard.KEY_0] = Key.NUM_0;
		
		LWJGL_TO_ENUM[Keyboard.KEY_NUMPAD1] = Key.NUM_1;
		LWJGL_TO_ENUM[Keyboard.KEY_NUMPAD2] = Key.NUM_2;
		LWJGL_TO_ENUM[Keyboard.KEY_NUMPAD3] = Key.NUM_3;
		LWJGL_TO_ENUM[Keyboard.KEY_NUMPAD4] = Key.NUM_4;
		LWJGL_TO_ENUM[Keyboard.KEY_NUMPAD5] = Key.NUM_5;
		LWJGL_TO_ENUM[Keyboard.KEY_NUMPAD6] = Key.NUM_6;
		LWJGL_TO_ENUM[Keyboard.KEY_NUMPAD7] = Key.NUM_7;
		LWJGL_TO_ENUM[Keyboard.KEY_NUMPAD8] = Key.NUM_8;
		LWJGL_TO_ENUM[Keyboard.KEY_NUMPAD9] = Key.NUM_9;
		LWJGL_TO_ENUM[Keyboard.KEY_NUMPAD0] = Key.NUM_0;
		
		LWJGL_TO_ENUM[Keyboard.KEY_LMENU] = Key.ALT;
		LWJGL_TO_ENUM[Keyboard.KEY_RMENU] = Key.ALT;
		LWJGL_TO_ENUM[Keyboard.KEY_BACK] = Key.BACKSPACE;
		LWJGL_TO_ENUM[Keyboard.KEY_RCONTROL] = Key.CONTROL;
		LWJGL_TO_ENUM[Keyboard.KEY_LCONTROL] = Key.CONTROL;
		LWJGL_TO_ENUM[Keyboard.KEY_DELETE] = Key.DELETE;
		LWJGL_TO_ENUM[Keyboard.KEY_DECIMAL] = Key.DELETE;
		LWJGL_TO_ENUM[Keyboard.KEY_END] = Key.END;
		LWJGL_TO_ENUM[Keyboard.KEY_ESCAPE] = Key.ESCAPE;
		LWJGL_TO_ENUM[Keyboard.KEY_HOME] = Key.HOME;
		LWJGL_TO_ENUM[Keyboard.KEY_INSERT] = Key.INSERT;
		LWJGL_TO_ENUM[Keyboard.KEY_PRIOR] = Key.PAGE_DOWN;
		LWJGL_TO_ENUM[Keyboard.KEY_NEXT] = Key.PAGE_UP;
		LWJGL_TO_ENUM[Keyboard.KEY_RSHIFT] = Key.SHIFT;
		LWJGL_TO_ENUM[Keyboard.KEY_LSHIFT] = Key.SHIFT;
		LWJGL_TO_ENUM[Keyboard.KEY_LMETA] = Key.START;
		LWJGL_TO_ENUM[Keyboard.KEY_RMETA] = Key.START;
		
		LWJGL_TO_ENUM[Keyboard.KEY_F1] = Key.F1;
		LWJGL_TO_ENUM[Keyboard.KEY_F2] = Key.F2;
		LWJGL_TO_ENUM[Keyboard.KEY_F3] = Key.F3;
		LWJGL_TO_ENUM[Keyboard.KEY_F4] = Key.F4;
		LWJGL_TO_ENUM[Keyboard.KEY_F5] = Key.F5;
		LWJGL_TO_ENUM[Keyboard.KEY_F6] = Key.F6;
		LWJGL_TO_ENUM[Keyboard.KEY_F7] = Key.F7;
		LWJGL_TO_ENUM[Keyboard.KEY_F8] = Key.F8;
		LWJGL_TO_ENUM[Keyboard.KEY_F9] = Key.F9;
		LWJGL_TO_ENUM[Keyboard.KEY_F10] = Key.F10;
		LWJGL_TO_ENUM[Keyboard.KEY_F11] = Key.F11;
		LWJGL_TO_ENUM[Keyboard.KEY_F12] = Key.F12;
		LWJGL_TO_ENUM[Keyboard.KEY_F13] = Key.F13;
		LWJGL_TO_ENUM[Keyboard.KEY_F14] = Key.F14;
		LWJGL_TO_ENUM[Keyboard.KEY_F15] = Key.F15;
		LWJGL_TO_ENUM[Keyboard.KEY_F16] = Key.F16;
		LWJGL_TO_ENUM[Keyboard.KEY_F17] = Key.F17;
		LWJGL_TO_ENUM[Keyboard.KEY_F18] = Key.F18;
		LWJGL_TO_ENUM[Keyboard.KEY_F19] = Key.F19;
		
		LWJGL_TO_ENUM[Keyboard.KEY_BACKSLASH] = Key.BACKSLASH;
		LWJGL_TO_ENUM[Keyboard.KEY_LBRACKET] = Key.BRACKET_LEFT;
		LWJGL_TO_ENUM[Keyboard.KEY_RBRACKET] = Key.BRACKET_RIGHT;
		LWJGL_TO_ENUM[Keyboard.KEY_COMMA] = Key.COMMA;
		LWJGL_TO_ENUM[Keyboard.KEY_NUMPADCOMMA] = Key.COMMA;
		LWJGL_TO_ENUM[Keyboard.KEY_SUBTRACT] = Key.DASH;
		LWJGL_TO_ENUM[Keyboard.KEY_EQUALS] = Key.EQUALS;
		LWJGL_TO_ENUM[Keyboard.KEY_GRAVE] = Key.GRAVE;
		LWJGL_TO_ENUM[Keyboard.KEY_PERIOD] = Key.PERIOD;
		LWJGL_TO_ENUM[Keyboard.KEY_SEMICOLON] = Key.SEMIUCOLON;
		LWJGL_TO_ENUM[Keyboard.KEY_SLASH] = Key.SLASH;
		
		LWJGL_TO_ENUM[Keyboard.KEY_DOWN] = Key.ARROW_DOWN;
		LWJGL_TO_ENUM[Keyboard.KEY_LEFT] = Key.ARROW_LEFT;
		LWJGL_TO_ENUM[Keyboard.KEY_RIGHT] = Key.ARROW_RIGHT;
		LWJGL_TO_ENUM[Keyboard.KEY_UP] = Key.ARROW_UP;
		//CAPS_LOCK
		LWJGL_TO_ENUM[Keyboard.KEY_RETURN] = Key.ENTER;
		LWJGL_TO_ENUM[Keyboard.KEY_NUMLOCK] = Key.NUM_LOCK;
		LWJGL_TO_ENUM[Keyboard.KEY_SPACE] = Key.SPACE;
		LWJGL_TO_ENUM[Keyboard.KEY_TAB] = Key.TAB;
		
	}
	
	@Override
	public void close()
	{
		Keyboard.destroy();
		
	}
	
	@Override
	public boolean isKeyDown(Key key)
	{
		return this.downKeys[key.ordinal()];
	}
	
	@Override
	public List<Key> getPushedKeys()
	{
		return this.downKeyList;
	}
	
	@Override
	public boolean initiateInput()
	{
		try
		{
			Keyboard.create();
			
		}
		catch (Exception e)
		{
			Logger.log().err(e);
			return false;
		}
		
		return true;
	}
	
	@Override
	protected void poll()
	{
		Key key;
		
		while (Keyboard.next())
		{
			key = LWJGL_TO_ENUM[Keyboard.getEventKey()];
			
			if (key == null)
			{
				continue;
			}
			
			this.downKeys[key.ordinal()] = Keyboard.getEventKeyState();//TODO See how this affects caps lock
			
			if (this.isKeyDown(key))
			{
				this.downKeyList.add(key);
				
			}
			else
			{
				this.downKeyList.remove(key);
				
			}
			
		}
		
	}
	
	@Override
	protected void postUpdate()
	{
		
	}
	
}
