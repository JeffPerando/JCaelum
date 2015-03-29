
package com.elusivehawk.caelum.prefab;

import java.util.List;
import java.util.UUID;
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
	private final UUID id;
	
	private final List<IComponent> children = Lists.newArrayList();
	
	private final List<IUpdatable> updateChildren = Lists.newArrayList();
	private final List<IRenderer> renderChildren = Lists.newArrayList();
	
	private World world = null;
	private boolean initiated = false, dead = false;
	
	public Entity()
	{
		this(UUID.randomUUID());
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public Entity(UUID uuid)
	{
		assert uuid != null;
		
		id = uuid;
		
	}
	
	public Entity(IPopulator<Entity> pop)
	{
		this();
		
		pop.populate(this);
		
	}
	
	public Entity(UUID uuid, IPopulator<Entity> pop)
	{
		this(uuid);
		
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
		if (!this.initiated)
		{
			throw new CaelumException("Attempt made to update an uninitiated entity!");
		}
		
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
	public void dispose()
	{
		this.children.forEach(((comp) -> {comp.dispose();}));
		
	}
	
	public void initiate(World worldObj)
	{
		assert worldObj != null;
		
		if (this.initiated)
		{
			throw new CaelumException("Cannot initiate an initiated Entity.");
		}
		
		this.world = worldObj;
		
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
	
	public void setIsDead()
	{
		this.dead = true;
		
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
	
	public World getWorld()
	{
		return this.world;
	}
	
	public UUID getID()
	{
		return this.id;
	}
	
	public boolean isDead()
	{
		return this.dead;
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
