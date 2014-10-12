
package com.elusivehawk.engine.prefab;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import com.elusivehawk.engine.assets.Asset;
import com.elusivehawk.engine.assets.IAssetReceiver;
import com.elusivehawk.engine.render.IRenderable;
import com.elusivehawk.engine.render.RenderContext;
import com.elusivehawk.engine.render.RenderException;
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
public abstract class Component implements IAssetReceiver, IRenderable, IUpdatable
{
	protected final Component parent;
	protected final int priority;
	
	protected Map<Integer, List<Component>> childMap = null;
	protected List<Component> childList = null;
	protected int maxPriority = -1;
	protected boolean locked = false;
	
	public Component(Component owner, int p, IPopulator<Component> pop)
	{
		this(owner, p);
		
		pop.populate(this);
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public Component(Component owner, int p)
	{
		parent = owner;
		priority = p;
		
	}
	
	@Override
	public void update(double delta, Object... extra)
	{
		this.forEveryChild(((child) -> {child.update(delta, extra);}));
		
	}
	
	@Override
	public void render(RenderContext rcon) throws RenderException
	{
		this.forEveryChild(((child) -> {child.render(rcon);}));
		
	}
	
	@Override
	public void onAssetLoaded(Asset a)
	{
		this.forEveryChild(false, ((child) -> {child.onAssetLoaded(a);}));
		
	}
	
	@Override
	public void preRender(RenderContext rcon, double delta)
	{
		this.forEveryChild(((child) -> {child.preRender(rcon, delta);}));
		
	}
	
	@Override
	public void postRender(RenderContext rcon)
	{
		this.forEveryChild(((child) -> {child.postRender(rcon);}));
		
	}
	
	public Component getParent()
	{
		return this.parent;
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
			return;
		}
		
		if (comp == this.parent)
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
				for (int c = 0; c < this.maxPriority; c++)
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
