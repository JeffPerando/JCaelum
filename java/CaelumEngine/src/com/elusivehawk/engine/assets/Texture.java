
package com.elusivehawk.engine.assets;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public abstract class Texture extends GraphicAsset
{
	protected Texture(String filepath)
	{
		super(filepath);
		
	}
	
	public abstract int getTexId(int frame);
	
	public abstract int getFrameCount();
	
	public boolean isAnimated()
	{
		return this.getFrameCount() > 1;
	}
	
}
