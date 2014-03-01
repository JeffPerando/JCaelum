
package com.elusivehawk.engine.core;

import com.elusivehawk.engine.render.RenderContext;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public interface Asset
{
	public EnumAssetType getType();
	
	public int[] getIds();
	
	public boolean isFinished();
	
	public void finishTextureOrModel(RenderContext context);
	
	public void finishSound(Object soundContext);
	
}
