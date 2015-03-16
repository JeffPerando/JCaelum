
package com.elusivehawk.caelum.prefab;

import java.util.List;
import java.util.function.Predicate;
import com.elusivehawk.caelum.CaelumException;
import com.elusivehawk.caelum.IDisposable;
import com.elusivehawk.caelum.render.IRenderer;
import com.elusivehawk.caelum.render.RenderContext;
import com.elusivehawk.caelum.render.RenderException;
import com.elusivehawk.util.IPopulator;
import com.elusivehawk.util.IUpdatable;
import com.elusivehawk.util.Logger;
import com.google.common.collect.Lists;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class Entity implements IDisposable, IUpdatable, IRenderer 
{
	private final Object world;
	
	private final List<IComponent> children = Lists.newArrayList();
	
	private final List<IUpdatable> updateChildren = Lists.newArrayList();
	private final List<IRenderer> renderChildren = Lists.newArrayList();
	
	private boolean initiated = false;
	
	@SuppressWarnings("unqualified-field-access")
	public Entity(Object worldObj)
	{
		world = worldObj;
		
	}
	
	public Entity(Object worldObj, IPopulator<Entity> pop)
	{
		this(worldObj);
		
		pop.populate(this);
		
	}
	
	@Override
	public void postRender(RenderContext rcon) throws RenderException
	{
		this.renderChildren.forEach(((ren) -> {ren.postRender(rcon);}));
		
	}
	
	@Override
	public void preRender(RenderContext rcon) throws RenderException
	{
		this.renderChildren.forEach(((ren) -> {ren.preRender(rcon);}));
		
	}
	
	@Override
	public void render(RenderContext rcon) throws RenderException
	{
		this.renderChildren.forEach(((ren) -> {ren.render(rcon);}));
		
	}
	
	@Override
	public void update(double delta) throws Throwable
	{
		this.updateChildren.forEach(((upd) ->
		{
			try
			{
				upd.update(delta);
				
			}
			catch (Throwable e)
			{
				Logger.err(e);
				
			}
			
		}));
	}
	
	@Override
	public void dispose(Object... args)
	{
		this.children.forEach(((comp) -> {comp.dispose(args);}));
		
	}
	
	public void initiate()
	{
		if (this.initiated)
		{
			throw new CaelumException("Cannot initiate an initiated Entity.");
		}
		
		this.children.forEach(((comp) ->
		{
			comp.initiate(this);
			
			if (comp instanceof IRenderer)
			{
				this.renderChildren.add((IRenderer)comp);
				
			}
			
			if (comp instanceof IUpdatable)
			{
				this.updateChildren.add((IUpdatable)comp);
				
			}
			
		}));
		
		this.initiated = true;
		
	}
	
	public Entity addChild(IComponent comp)
	{
		assert comp != null;
		
		if (this.initiated)
		{
			throw new CaelumException("Cannot add a component after an entity has been initiated!");
		}
		
		this.children.add(comp);
		
		return this;
	}
	
	public Object getWorld()
	{
		return this.world;
	}
	
	public IComponent getChild(Class<?> clazz)
	{
		return this.getChild(clazz::isInstance);
	}
	
	public IComponent getChild(Predicate<IComponent> pred)
	{
		for (IComponent comp : this.children)
		{
			if (pred.test(comp))
			{
				return comp;
			}
			
		}
		
		throw new CaelumException("Cannot get child: No valid child found");
	}
	
	public List<IComponent> getChildren(Class<?> clazz)
	{
		return this.getChildren(clazz::isInstance);
	}
	
	public List<IComponent> getChildren(Predicate<IComponent> pred)
	{
		if (this.children.isEmpty())
		{
			throw new CaelumException("Cannot get children: No children found");
		}
		
		List<IComponent> ret = Lists.newArrayList();
		
		this.children.forEach(((comp) ->
		{
			if (pred.test(comp))
			{
				ret.add(comp);
				
			}
			
		}));
		
		return ret;
	}
	
}
