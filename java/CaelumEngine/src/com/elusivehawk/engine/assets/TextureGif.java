
package com.elusivehawk.engine.assets;

import com.elusivehawk.engine.render.ILegibleImage;
import com.elusivehawk.engine.util.storage.Buffer;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class TextureGif extends Texture
{
	protected final ILegibleImage[] imgs;
	protected Buffer<TextureStatic> textures;
	
	@SuppressWarnings("unqualified-field-access")
	public TextureGif(String filename, ILegibleImage[] listimgs)
	{
		super(filename);
		imgs = listimgs;
		
	}
	
	@Override
	protected boolean finishAsset()
	{
		TextureStatic tex;
		boolean flag = true;
		
		this.textures = new Buffer<TextureStatic>(this.getFrameCount());
		
		for (ILegibleImage img : this.imgs)
		{
			tex = new TextureStatic(this.name, img);
			
			tex.finish();
			
			if (!tex.isFinished())
			{
				flag = false;
				break;
			}
			
			this.textures.add(tex);
			
		}
		
		if (!flag)
		{
			this.textures.clear();
			
		}
		
		return flag;
	}
	
	@Override
	public void updateTexture()
	{
		if (this.textures.remaining() == 0)
		{
			this.textures.rewind();
			
		}
		else
		{
			this.textures.next();
			
		}
		
		this.tex = this.textures.get().getTexId();
		
	}
	
	@Override
	public boolean isAnimated()
	{
		return true;
	}
	
	@Override
	public int getFrameCount()
	{
		return this.imgs.length;
	}
	
	@Override
	public ILegibleImage getSourceImg(int frame)
	{
		return this.imgs[frame];
	}
	
}
