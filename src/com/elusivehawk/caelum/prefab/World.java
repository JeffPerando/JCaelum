
package com.elusivehawk.caelum.prefab;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import com.elusivehawk.util.IUpdatable;
import com.elusivehawk.util.storage.SyncList;
import com.google.common.collect.Maps;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public abstract class World implements IUpdatable
{
	private final Map<UUID, Entity> entities = Maps.newConcurrentMap();
	
	protected final List<Entity> entitiesToSpawn = SyncList.newList();
	
	@Override
	public void update(double delta) throws WorldException
	{
		if (!this.entitiesToSpawn.isEmpty())
		{
			this.entitiesToSpawn.forEach(((entity) -> {entity.initiate(this);}));
			
			this.entitiesToSpawn.forEach(((entity) ->
			{
				this.entities.put(entity.getID(), entity);
				
			}));
			
			this.entitiesToSpawn.clear();
			
		}
		
		this.entities.forEach(((id, entity) ->
		{
			if (entity.isDead())
			{
				this.entities.remove(id);
				
			}
			else
			{
				try
				{
					entity.update(delta);
					
				}
				catch (Throwable e)
				{
					throw new WorldException("Error thrown while uppdating entity %s", e, entity);
				}
				
			}
			
		}));
		
	}
	
	public UUID spawnEntity(Entity entity)
	{
		assert entity != null;
		assert !entity.isDead();
		assert entity.getWorld() == this;
		assert !(this.entitiesToSpawn.contains(entity) || this.entities.containsKey(entity.getID()));
		
		this.entitiesToSpawn.add(entity);
		
		return entity.getID();
	}
	
	public Entity getEntity(UUID id)
	{
		assert id != null;
		
		return this.entities.get(id);
	}
	
}
