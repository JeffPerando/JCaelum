
package com.elusivehawk.engine.assets;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public abstract class AbstractTexture extends Asset
{
	protected final int[] ids = new int[]{0, this.isAnimated() ? 1 : 0, 0xFFFFFF};
	
	protected AbstractTexture(String filename)
	{
		super(filename, EnumAssetType.TEXTURE);
		
	}
	
	public void setColor(int color)
	{
		this.ids[2] = color;
		
	}
	
	public void updateTexture(){}
	
	public abstract boolean isAnimated();
	
}
