
package com.elusivehawk.engine.prefab;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import com.elusivehawk.engine.assets.Asset;
import com.elusivehawk.engine.assets.IAssetReceiver;
import com.elusivehawk.engine.render.IRenderable;
import com.elusivehawk.engine.render.RenderContext;
import com.elusivehawk.engine.render.RenderException;
import com.elusivehawk.util.IUpdatable;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public abstract class ComponentGroup implements IAssetReceiver, IRenderable, IUpdatable
{
	protected final ComponentGroup parent;
	
	protected Map<Integer, List<Component>> childMap = null;
	protected List<Component> childList = null;
	protected volatile int maxPriority = -1;
	
	public ComponentGroup()
	{
		this(null);
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public ComponentGroup(ComponentGroup owner)
	{
		parent = owner;
		
	}
	
	@Override
	public void update(double delta, Object... extra)
	{
		this.forEveryChild(((child) -> {child.update(delta, extra);}));
		
	}
	
	@Override
	public void render(RenderContext rcon, double delta) throws RenderException
	{
		this.forEveryChild(((child) -> {child.render(rcon, delta);}));
		
	}
	
	@Override
	public void onAssetLoaded(Asset a)
	{
		this.forEveryChild(false, ((child) -> {child.onAssetLoaded(a);}));
		
	}
	
	public ComponentGroup getParent()
	{
		return this.parent;
	}
	
	public ComponentGroup addComponent(Component comp)
	{
		if (this.childMap == null)
		{
			this.childMap = Maps.newHashMap();
			
		}
		
		List<Component> children = this.childMap.get(comp.getPriority());
		
		if (children == null)
		{
			children = Lists.newArrayList();
			this.childMap.put(comp.getPriority(), children);
			
		}
		
		children.add(comp);
		
		if (this.childList == null)
		{
			this.childList = Lists.newArrayList();
			
		}
		
		this.childList.add(comp);
		
		this.maxPriority = Math.max(this.maxPriority, comp.getPriority()); 
		
		return this;
	}
	
	public boolean removeComponent(Component comp)
	{
		boolean found = false;
		
		if (this.childList != null)
		{
			found = this.childList.remove(comp);
			
		}
		
		if (found)
		{
			for (List<Component> children : this.childMap.values())
			{
				children.remove(comp);
				
			}
			
		}
		
		return found;
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
