
package com.elusivehawk.engine.render.old;

import com.elusivehawk.engine.render.three.RenderTicket;
import com.elusivehawk.util.storage.SyncList;

/**
 * 
 * Implement this to all entities in your game world.
 * 
 * @author Elusivehawk
 */
@Deprecated
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
