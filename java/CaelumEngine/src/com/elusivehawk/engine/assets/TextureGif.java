
package com.elusivehawk.engine.assets;

import java.util.List;
import com.elusivehawk.engine.render.ILegibleImage;
import com.elusivehawk.engine.render.RenderHelper;
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
	protected final Buffer<Integer> tex;
	
	@SuppressWarnings("unqualified-field-access")
	public TextureGif(String filename, List<ILegibleImage> listimgs)
	{
		super(filename);
		imgs = listimgs;
		tex = new Buffer<Integer>(imgs.size());
		
	}
	
	@Override
	public int[] getIds()
	{
		return this.ids;
	}
	
	@Override
	protected boolean finishAsset()
	{
		boolean flag = true;
		int i = 0;
		
		for (ILegibleImage img : this.imgs)
		{
			i = RenderHelper.processImage(img);
			
			if (i == 0)
			{
				flag = false;
				break;
			}
			
			this.tex.add(i);
			
		}
		
		if (!flag)
		{
			this.tex.clear();
			
		}
		
		return flag;
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
		
	}
	
	@Override
	public boolean isAnimated()
	{
		return true;
	}
	
	@Override
	public void setColor(int color)
	{
		this.ids[2] = color;
		
	}
	
	@Override
	public Object getAttachment()
	{
		return this.imgs;
	}
	
}