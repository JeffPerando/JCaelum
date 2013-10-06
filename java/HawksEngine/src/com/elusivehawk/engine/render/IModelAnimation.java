
package com.elusivehawk.engine.render;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public interface IModelAnimation
{
	public void update(VertexBufferObject vbo, int frame, boolean usedBefore);
	
	public int getFrames();
	
	public void onCompletion(RenderTicket ticket);
	
}
