
package com.elusivehawk.engine.core;

import java.util.UUID;
import com.elusivehawk.engine.util.IGettable;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public final class AssetTicket implements IGettable<Asset>
{
	private final UUID uuid;
	private Asset asset = null;
	
	@SuppressWarnings("unqualified-field-access")
	public AssetTicket(UUID id)
	{
		uuid = id;
		
	}
	
	@Override
	public Asset get()
	{
		return this.asset;
	}
	
	public UUID getId()
	{
		return this.uuid;
	}
	
	public synchronized void setAsset(Asset a)
	{
		assert a != null;
		
		this.asset = a;
		
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if (obj instanceof AssetTicket)
		{
			return this.getId().equals(((AssetTicket)obj).getId());
		}
		
		return false;
	}
	
}
