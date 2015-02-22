
package com.elusivehawk.caelum.prefab;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import com.elusivehawk.caelum.render.RenderContext;
import com.elusivehawk.caelum.render.RenderException;
import com.elusivehawk.caelum.render.Renderable;
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
public abstract class Component extends Renderable implements IUpdatable
{
	protected final int priority;
	
	protected Map<Integer, List<Component>> childMap = null;
	protected List<Component> childList = null;
	protected int maxPriority = Integer.MIN_VALUE, minPriority = Integer.MAX_VALUE;
	protected boolean locked = false;
	
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
		this.forEveryChild(((child) -> {child.update(delta);}));
		
	}
	
	@Override
	public void delete(RenderContext rcon)
	{
		this.forEveryChild(false, ((child) -> {child.delete(rcon);}));
		
	}
	
	@Override
	public void renderImpl(RenderContext rcon) throws RenderException
	{
		this.forEveryChild(((child) -> {child.render(rcon);}));
		
	}
	
	@Override
	public void preRender(RenderContext rcon)
	{
		this.forEveryChild(((child) -> {child.preRender(rcon);}));
		
	}
	
	@Override
	public void postRender(RenderContext rcon)
	{
		this.forEveryChild(((child) -> {child.postRender(rcon);}));
		
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
		if (this.childList != null)
		{
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
			
		}
		
		return false;
	}
	
	public void forEveryChild(Consumer<Component> consumer)
	{
		this.forEveryChild(true, consumer);
		
	}
	
	public void forEveryChild(boolean usePriority, Consumer<Component> consumer)
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
