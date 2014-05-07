
package com.elusivehawk.engine.render;

import com.elusivehawk.engine.util.storage.SyncList;

/**
 * 
 * Implement this to all entities in your game world.
 * 
 * @author Elusivehawk
 */
public interface IModelGroup
{
	/**
	 * 
	 * @return The name of this group
	 */
	public String getName();
	
	/**
	 * 
	 * @return The tickets (aka models) to render
	 */
	public SyncList<RenderTicket> getTickets();
	
}
