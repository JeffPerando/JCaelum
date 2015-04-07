
package com.elusivehawk.caelum.input;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Consumer;
import org.lwjgl.glfw.GLFW;
import com.elusivehawk.caelum.render.Display;
import com.elusivehawk.util.storage.DirtableStorage;
import com.elusivehawk.util.storage.SyncList;
import com.elusivehawk.util.storage.Tuple;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class Keyboard extends DelayedInput
{
	public static final ImmutableList<Tuple<Integer, Key>> GLFW_ENUMS;
	
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
	
	private final List<Tuple<Key[], Consumer<Keyboard>>> hooks = Lists.newArrayList();
	
	private final List<Key> downKeys = SyncList.newList();
	private final Map<Key, EnumKeyStatus> keyStats = Maps.newConcurrentMap();
	private final double[] keyTime = new double[InputConst.KEY_COUNT];
	private final DirtableStorage<String> paste = new DirtableStorage<String>().setSync();
	
	public Keyboard(Display screen)
	{
		super(screen);
		
	}
	
	@Override
	public void close(){}
	
	@Override
	public void updateInput(double delta) throws Throwable
	{
		for (Entry<Key, EnumKeyStatus> entry : this.keyStats.entrySet())
		{
			if (entry.getValue() == EnumKeyStatus.RELEASED)
			{
				this.keyStats.remove(entry.getKey());
				
				synchronized (this)
				{
					this.keyTime[entry.getKey().ordinal()] = 0;
					
				}
				
			}
			
		}
		
		long window = this.getDisplay().getId();
		
		for (Tuple<Integer, Key> t : GLFW_ENUMS)
		{
			Key key = t.two;
			
			int status = GLFW.glfwGetKey(window, t.one);
			
			boolean down = (status == GLFW.GLFW_PRESS);
			int ki = this.downKeys.indexOf(key);
			
			if (down)
			{
				if (ki == -1)
				{
					this.onKeyPushed(key, delta);
					
				}
				
			}
			else if (ki != -1)
			{
				this.onKeyRaised(key);
				
			}
			
		}
		
		if (this.areKeysDown(Key.CONTROL, Key.V))
		{
			String paste = GLFW.glfwGetClipboardString(window);
			
			if (paste != null)
			{
				this.paste.set(paste);
				
				this.setIsDirty(true);
				
			}
			
		}
		
	}
	
	@Override
	public void triggerHooks(double delta)
	{
		this.hooks.forEach(((tuple) ->
		{
			if (this.areKeysDown(tuple.one))
			{
				tuple.two.accept(this);
				
			}
			
		}));
		
		super.triggerHooks(delta);
		
	}
	
	public void addHook(Consumer<Keyboard> hook, Key... keys)
	{
		this.hooks.add(Tuple.create(keys, hook));
		
	}
	
	public EnumKeyStatus getStatus(Key key)
	{
		EnumKeyStatus ret = this.keyStats.get(key);
		
		if (ret == null)
		{
			return EnumKeyStatus.UP;
		}
		
		return ret;
	}
	
	public boolean isKeyDown(Key key)
	{
		return this.downKeys.contains(key);
	}
	
	public boolean areKeysDown(Key... keys)
	{
		int found = 0;
		
		for (Key k : keys)
		{
			for (Key k0 : this.downKeys)
			{
				if (k == k0)
				{
					found++;
					
					break;
				}
				
			}
			
		}
		
		return found == keys.length;
	}
	
	public List<Key> getDownKeys()
	{
		return this.downKeys;
	}
	
	public double getDelta(Key key)
	{
		return this.keyTime[key.ordinal()];
	}
	
	public String getPaste()
	{
		return this.paste.get();
	}
	
	public void onKeyPushed(Key key, double delta)
	{
		synchronized (this)
		{
			this.keyTime[key.ordinal()] += delta;
			
		}
		
		if (key.isLock() && this.getStatus(key) == EnumKeyStatus.DOWN)
		{
			this.downKeys.remove(key);
			this.keyStats.put(key, EnumKeyStatus.RELEASED);
			
		}
		else if (!this.downKeys.contains(key))
		{
			this.downKeys.add(key);
			this.keyStats.put(key, EnumKeyStatus.DOWN);
			
		}
		
		this.setIsDirty(true);
		
	}
	
	public void onKeyRaised(Key key)
	{
		if (key.isLock())
		{
			return;
		}
		
		int i = this.downKeys.indexOf(key);
		
		if (i != -1)
		{
			this.downKeys.remove(i);
			this.keyStats.put(key, EnumKeyStatus.RELEASED);
			
			this.setIsDirty(true);
			
		}
		
	}
	
}
