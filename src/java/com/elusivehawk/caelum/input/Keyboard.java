
package com.elusivehawk.caelum.input;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Consumer;
import com.elusivehawk.caelum.lwjgl.LWJGLKeyboardImpl;
import com.elusivehawk.caelum.render.Display;
import com.elusivehawk.util.storage.DirtableStorage;
import com.elusivehawk.util.storage.SyncList;
import com.elusivehawk.util.storage.Tuple;
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
	private final List<Tuple<Key[], Consumer<Keyboard>>> hooks = Lists.newArrayList();
	
	private final List<Key> downKeys = SyncList.newList();
	private final Map<Key, EnumKeyStatus> keyStats = Maps.newConcurrentMap();
	private final double[] keyTime = new double[InputConst.KEY_COUNT];
	private final DirtableStorage<String> paste = new DirtableStorage<String>().setSync();
	
	public Keyboard(Display screen)
	{
		super(screen, new LWJGLKeyboardImpl());
		
	}
	
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
		
		this.updateImpl(delta);
		
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
	
	public void setPasted(String str)
	{
		this.paste.set(str);
		
		if (this.paste.isDirty())
		{
			this.setIsDirty(true);
			
			this.paste.setIsDirty(false);
			
		}
		
	}
	
}
