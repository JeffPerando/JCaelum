
package com.elusivehawk.caelum.prefab;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import com.elusivehawk.caelum.CaelumException;
import com.elusivehawk.caelum.IDisposable;
import com.elusivehawk.caelum.render.IRenderer;
import com.elusivehawk.caelum.render.RenderContext;
import com.elusivehawk.caelum.render.RenderException;
import com.elusivehawk.util.IPopulator;
import com.elusivehawk.util.IUpdatable;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public abstract class Component implements IRenderer, IDisposable, IUpdatable
{
	protected final int priority;
	
	protected Map<Integer, List<Component>> childMap = null;
	protected List<Component> childList = null;
	protected int maxPriority = Integer.MIN_VALUE, minPriority = Integer.MAX_VALUE;
	protected boolean locked = false, disposed = false;
	
	@SuppressWarnings("unqualified-field-access")
	public Component(int p)
	{
		priority = p;
		
	}
	
	public Component(int p, IPopulator<Component> pop)
	{
		this(p);
		
		pop.populate(this);
		
	}
	
	@Override
	public void update(double delta)
	{
		this.forEachChild(((child) -> {child.update(delta);}));
		
	}
	
	@Override
	public void dispose(Object... args)
	{
		if (this.disposed)
		{
			throw new CaelumException("One does not simply dispose of a disposed Component.");
		}
		
		this.forEachChild(false, ((child) -> {child.dispose(args);}));
		
		this.disposed = true;
		
	}
	
	@Override
	public void preRender(RenderContext rcon)
	{
		this.forEachChild(((child) -> {child.preRender(rcon);}));
		
	}
	
	@Override
	public void render(RenderContext rcon) throws RenderException
	{
		this.forEachChild(((child) -> {child.render(rcon);}));
		
	}
	
	@Override
	public void postRender(RenderContext rcon)
	{
		this.forEachChild(((child) -> {child.postRender(rcon);}));
		
	}
	
	public int getPriority()
	{
		return this.priority;
	}
	
	public boolean isLocked()
	{
		return this.locked;
	}
	
	public Component lock()
	{
		this.locked = true;
		
		return this;
	}
	
	public void addChild(Component comp)
	{
		if (this.locked)
		{
			return;
		}
		
		if (comp == null)
		{
			throw new NullPointerException();
		}
		
		if (comp.hasChild(this))
		{
			return;
		}
		
		if (this.childMap == null)
		{
			this.childMap = Maps.newHashMap();
			this.childList = Lists.newArrayList();
			
		}
		
		List<Component> children = this.childMap.get(comp.getPriority());
		
		if (children == null)
		{
			children = Lists.newArrayList();
			this.childMap.put(comp.getPriority(), children);
			
		}
		
		children.add(comp);
		this.childList.add(comp);
		
		this.maxPriority = Math.max(this.maxPriority, comp.getPriority());
		this.minPriority = Math.min(this.minPriority, comp.getPriority());
		
	}
	
	public boolean removeChild(Component comp)
	{
		if (this.locked)
		{
			return false;
		}
		
		if (comp == null)
		{
			return false;
		}
		
		if (this.childMap != null)
		{
			List<Component> children = this.childMap.get(comp.getPriority());
			
			if (children != null && children.remove(comp))
			{
				this.childList.remove(comp);
				
				return true;
			}
			
		}
		
		return false;
	}
	
	public boolean hasChild(Component comp)
	{
		if (this.childList == null)
		{
			return false;
		}
		
		for (Component child : this.childList)
		{
			if (child == comp)
			{
				return true;
			}
			
			if (child.hasChild(comp))
			{
				return true;
			}
			
		}
		
		return false;
	}
	
	@SuppressWarnings("unchecked")
	public <T extends Component> T getChild(Class<T> clazz)
	{
		if (this.childList == null)
		{
			return null;
		}
		
		for (Component comp : this.childList)
		{
			if (clazz.isInstance(comp))
			{
				return (T)comp;
			}
			
		}
		
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public <T extends Component> List<T> getChildren(Class<T> clazz)
	{
		List<T> ret = Lists.newArrayList();
		
		this.forEachChild(false, ((comp) ->
		{
			if (clazz.isInstance(comp))
			{
				ret.add((T)comp);
				
			}
			
		}));
		
		return ret;
	}
	
	public void forEachChild(Consumer<Component> consumer)
	{
		this.forEachChild(true, consumer);
		
	}
	
	public void forEachChild(boolean usePriority, Consumer<Component> consumer)
	{
		if (this.childList != null)
		{
			if (usePriority)
			{
				for (int c = this.minPriority; c < this.maxPriority; c++)
				{
					List<Component> children = this.childMap.get(c);
					
					if (children != null)
					{
						children.forEach(consumer);
						
					}
					
				}
				
			}
			else
			{
				this.childList.forEach(consumer);
				
			}
			
		}
		
	}
	
}
