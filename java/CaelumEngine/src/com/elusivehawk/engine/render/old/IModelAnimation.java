
package com.elusivehawk.engine.render.old;

import com.elusivehawk.engine.render.RenderTicket;

/**
 * 
 * Used in order to animate the model represented by {@link RenderTicket}s.
 * 
 * @deprecated To be replaced with an actual implementation.
 * 
 * @author Elusivehawk
 */
@Deprecated
public interface IModelAnimation
{
	/**
	 * 
	 * Called once every frame.
	 * 
	 * @param tkt The ticket being animated
	 * @param usedBefore If this animation is continuing on from the last frame
	 * @param fin If this is the final frame of the animation
	 * @return True to update the VBO of the ticket.
	 */
	public boolean update(RenderTicket tkt, boolean usedBefore, boolean fin);
	
	/**
	 * @return The number of frames in this animation.
	 */
	public int getFrameCount();
	
}
