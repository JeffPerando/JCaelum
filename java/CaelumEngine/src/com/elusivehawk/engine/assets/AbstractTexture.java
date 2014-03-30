
package com.elusivehawk.engine.assets;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public abstract class AbstractTexture extends Asset
{
	protected final int[] ids = new int[]{0, this.isAnimated() ? 1 : 0};
	
	protected AbstractTexture(String filename)
	{
		super(filename, EnumAssetType.TEXTURE);
		
	}
	
	public void updateTexture(){}
	
	@SuppressWarnings("static-method")
	public int getFrameCount()
	{
		return 0;
	}
	
	@SuppressWarnings("static-method")
	public boolean isAnimated()
	{
		return false;
	}
	
}
