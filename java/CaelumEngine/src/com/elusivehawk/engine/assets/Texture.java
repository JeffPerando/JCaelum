
package com.elusivehawk.engine.assets;

import com.elusivehawk.engine.render.ILegibleImage;
import com.elusivehawk.engine.render.RenderHelper;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class Texture extends Asset
{
	protected final ILegibleImage image;
	protected int[] ids = new int[2];
	
	@SuppressWarnings("unqualified-field-access")
	public Texture(String filename, ILegibleImage img)
	{
		super(filename, EnumAssetType.TEXTURE);
		image = img;
		
	}
	
	@Override
	public int[] getIds()
	{
		return this.ids;
	}
	
	@Override
	protected boolean finishAsset()
	{
		this.ids[0] = RenderHelper.processImage(this.image);
		
		return this.ids[0] != 0;
	}
	
	@Override
	public Object getAttachment()
	{
		return this.image;
	}
	
}
