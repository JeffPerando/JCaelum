
package com.elusivehawk.engine.render;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public interface IModelAnimation
{
	public void update(RenderTicket tkt, boolean usedBefore, boolean finished);
	
	public int getFrameCount();
	
}
