
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
		this.forEveryChild(((child) -> {child.onAssetLoaded(a);}));
		
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
		
		this.maxPriority = Math.max(this.maxPriority, comp.getPriority()); 
		
		return this;
	}
	
	public boolean removeComponent(Component comp)
	{
		boolean found = false;
		
		if (this.childMap != null)
		{
			for (List<Component> children : this.childMap.values())
			{
				if (children.remove(comp))
				{
					found = true;
					
				}
				
			}
			
		}
		
		return found;
	}
	
	public void forEveryChild(Consumer<Component> consumer)
	{
		if (this.childMap == null)
		{
			return;
		}
		
		for (int c = 0; c < this.maxPriority; c++)
		{
			List<Component> children = this.childMap.get(c);
			
			if (children == null)
			{
				continue;
			}
			
			children.forEach(consumer);
			
		}
		
	}
	
}
