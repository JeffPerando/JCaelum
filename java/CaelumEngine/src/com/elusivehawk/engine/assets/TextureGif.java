
package com.elusivehawk.engine.assets;

import java.util.List;
import com.elusivehawk.engine.render.ILegibleImage;
import com.elusivehawk.engine.util.Buffer;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class TextureGif extends AbstractTexture
{
	protected final List<ILegibleImage> imgs;
	protected Buffer<Texture> textures;
	
	@SuppressWarnings("unqualified-field-access")
	public TextureGif(String filename, List<ILegibleImage> listimgs)
	{
		super(filename);
		imgs = listimgs;
		
	}
	
	@Override
	public int[] getIds()
	{
		return this.ids;
	}
	
	@Override
	protected boolean finishAsset()
	{
		Texture tex;
		boolean flag = true;
		
		this.textures = new Buffer<Texture>(this.getFrameCount());
		
		for (ILegibleImage img : this.imgs)
		{
			tex = new Texture(this.name, img);
			
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
		
		Asset tex = this.textures.get();
		
		this.ids[0] = tex.getIds()[0];
		
	}
	
	@Override
	public boolean isAnimated()
	{
		return true;
	}
	
	@Override
	public int getFrameCount()
	{
		return this.imgs.size();
	}
	
	@Override
	public Object getAttachment()
	{
		return this.imgs;
	}
	
}
