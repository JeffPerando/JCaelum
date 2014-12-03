
package com.elusivehawk.caelum.render.tex;

import java.io.DataInputStream;
import java.util.List;
import com.elusivehawk.caelum.CaelumEngine;
import com.elusivehawk.caelum.CaelumException;
import com.elusivehawk.caelum.assets.Asset;
import com.elusivehawk.caelum.assets.AssetManager;
import com.elusivehawk.caelum.assets.EnumAssetType;
import com.elusivehawk.caelum.render.GraphicAsset;
import com.elusivehawk.caelum.render.RenderContext;
import com.elusivehawk.caelum.render.RenderHelper;
import com.elusivehawk.caelum.render.gl.GL1;
import com.google.common.collect.Lists;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class TextureAsset extends GraphicAsset implements ITexture
{
	protected ILegibleImage[] sources = null;
	protected int[] frames = null;
	protected int frame = 0;
	
	public TextureAsset(String filepath)
	{
		super(filepath, EnumAssetType.TEXTURE);
		
	}
	
	@Override
	public void preRender(RenderContext rcon, double delta)
	{
		if (this.frames == null)
		{
			return;
		}
		
		if (!this.isStatic())
		{
			this.frame++;
			
			if (this.frame == this.getFrameCount())
			{
				this.frame = 0;
				
			}
			
		}
		
		if (this.frames[this.frame] == 0)
		{
			ILegibleImage src = this.sources[this.frame];
			
			if (src != null)
			{
				this.frames[this.frame] = RenderHelper.genTexture(rcon, src);
				
			}
			
		}
		
	}
	
	@Override
	public void delete(RenderContext rcon)
	{
		if (this.frames != null)
		{
			GL1.glDeleteTextures(this.frames);
			
		}
		
	}
	
	@Override
	public int getTexId()
	{
		return this.frames == null ? 0 : this.frames[this.frame];
	}
	
	@Override
	public boolean isStatic()
	{
		return this.getFrameCount() < 2;
	}
	
	@Override
	public void initiate(RenderContext rcon){}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean readAsset(DataInputStream in) throws Throwable
	{
		AssetManager mgr = CaelumEngine.assets();
		
		Object read = mgr.readObjectForAsset(this, in);
		
		List<ILegibleImage> imgs = Lists.newArrayList();
		
		if (read instanceof ILegibleImage)
		{
			imgs.add((ILegibleImage)read);
			
		}
		else if (read instanceof List<?>)
		{
			imgs.addAll((List<ILegibleImage>)read);
			
		}
		else throw new CaelumException("Unusable return type for texture of type %s: %s", this.ext, read);
		
		ILegibleImage[] srcs = imgs.toArray(new ILegibleImage[imgs.size()]);
		int[] fs = new int[imgs.size()];
		
		synchronized (this)
		{
			this.sources = srcs;
			this.frames = fs;
			
		}
		
		return true;
	}
	
	@Override
	public void onExistingAssetFound(Asset a)
	{
		super.onExistingAssetFound(a);
		
		if (a instanceof TextureAsset && ((TextureAsset)a).isLoaded())
		{
			this.frames = ((TextureAsset)a).frames;
			
		}
		
	}
	
	public int getFrameCount()
	{
		return this.frames == null ? 0 : this.frames.length;
	}
	
}
