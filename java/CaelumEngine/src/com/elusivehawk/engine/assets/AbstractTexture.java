
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
	
	public void setColor(int frame, int color)
	{
		this.ids[2] = color;
		
	}
	
	public void updateTexture(){}
	
	@SuppressWarnings("static-method")
	public int getFrameCount()
	{
		return 0;
	}
	
	public abstract boolean isAnimated();
	
}
