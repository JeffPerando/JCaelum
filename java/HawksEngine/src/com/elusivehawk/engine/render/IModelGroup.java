
package com.elusivehawk.engine.render;

import java.util.Collection;

/**
 * 
 * Implement this to all entities in your game world.
 * 
 * @author Elusivehawk
 */
public interface IModelGroup
{
	public String getName();
	
	public Collection<RenderTicket> getTickets();
	
}
