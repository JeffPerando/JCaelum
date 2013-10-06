
package com.elusivehawk.engine.render;

import java.util.List;

/**
 * 
 * Implement this to all entities in your game world.
 * 
 * @author Elusivehawk
 */
public interface IModelGroup
{
	public String getName();
	
	public List<RenderTicket> getTickets();
	
	public ITexture getTexture(int index);
	
}
