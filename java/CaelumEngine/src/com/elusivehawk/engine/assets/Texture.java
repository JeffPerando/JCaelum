
package com.elusivehawk.engine.assets;

import com.elusivehawk.engine.render.ILegibleImage;
import com.elusivehawk.engine.render.RenderHelper;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class Texture implements Asset
{
	protected final ILegibleImage image;
	protected int[] ids = new int[2];
	
	@SuppressWarnings("unqualified-field-access")
	public Texture(ILegibleImage img)
	{
		image = img;
		
	}
	
	@Override
	public EnumAssetType getType()
	{
		return EnumAssetType.TEXTURE;
	}
	
	@Override
	public int[] getIds()
	{
		return this.ids;
	}
	
	@Override
	public boolean isFinished()
	{
		return this.ids[0] != 0;
	}
	
	@Override
	public void finish()
	{
		this.ids[0] = RenderHelper.processImage(this.image);
		
	}
	
}
