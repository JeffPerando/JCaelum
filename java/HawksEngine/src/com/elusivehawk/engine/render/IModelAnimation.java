
package com.elusivehawk.engine.render;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public interface IModelAnimation
{
	public boolean update(RenderTicket tkt, boolean usedBefore, boolean fin);
	
	public int getFrameCount();
	
}
