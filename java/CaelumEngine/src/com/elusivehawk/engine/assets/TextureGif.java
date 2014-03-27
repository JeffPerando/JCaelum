
package com.elusivehawk.engine.assets;

import java.util.List;
import com.elusivehawk.engine.render.ILegibleImage;
import com.elusivehawk.engine.render.INonStaticTexture;
import com.elusivehawk.engine.render.RenderHelper;
import com.elusivehawk.engine.util.Buffer;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class TextureGif implements INonStaticTexture
{
	protected final List<ILegibleImage> imgs;
	protected final Buffer<Integer> tex;
	protected final int[] ids = new int[]{0, 1};
	protected boolean dirty = true;
	
	@SuppressWarnings("unqualified-field-access")
	public TextureGif(List<ILegibleImage> listimgs)
	{
		imgs = listimgs;
		tex = new Buffer<Integer>(imgs.size());
		
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
		return !this.tex.isEmpty();
	}
	
	@Override
	public void finish()
	{
		for (ILegibleImage img : this.imgs)
		{
			this.tex.add(RenderHelper.processImage(img));
			
		}
		
	}
	
	@Override
	public boolean isDirty()
	{
		return this.dirty;
	}
	
	@Override
	public void setIsDirty(boolean b)
	{
		this.dirty = b;
		
	}
	
	@Override
	public void updateTexture()
	{
		if (this.tex.remaining() == 0)
		{
			this.tex.rewind();
			
		}
		else
		{
			this.tex.next();
			
		}
		
		this.ids[0] = this.tex.get();
		
		this.dirty = true;
		
	}
	
}
