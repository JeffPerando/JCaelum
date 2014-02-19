
package com.elusivehawk.engine.render2;

import java.util.List;

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
	public List<RenderTicket> getTickets();
	
	/**
	 * 
	 * @param index The index of the {@link RenderTicket} that is about to be rendered
	 * @return True to render the ticket represented by index.
	 */
	public boolean doRenderTicket(int index);
	
}
