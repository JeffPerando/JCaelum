
package com.elusivehawk.caelum.render.tex;

import java.io.DataInputStream;
import java.util.List;
import com.elusivehawk.caelum.CaelumEngine;
import com.elusivehawk.caelum.CaelumException;
import com.elusivehawk.caelum.assets.Asset;
import com.elusivehawk.caelum.assets.AssetManager;
import com.elusivehawk.caelum.assets.EnumAssetType;
import com.elusivehawk.caelum.render.RenderContext;
import com.elusivehawk.caelum.render.RenderHelper;
import com.elusivehawk.caelum.render.gl.GL1;
import com.elusivehawk.util.Logger;
import com.google.common.collect.Lists;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class TextureAsset extends Asset implements ITexture
{
	private ILegibleImage[] sources;
	private int[] frames;
	
	private int frame = 0;
	
	public TextureAsset(String filepath)
	{
		super(filepath, EnumAssetType.TEXTURE);
		
	}
	
	public TextureAsset(String filepath, boolean readNow)
	{
		super(filepath, EnumAssetType.TEXTURE, readNow);
		
	}
	
	@Override
	public void preRender(RenderContext rcon)
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
				this.frames[this.frame] = RenderHelper.genTexture(src);
				
				if (this.frames[this.frame] == 0)
				{
					Logger.debug("Failed to load texture #%s in %s", this.frame + 1, this);
					
				}
				else
				{
					Logger.debug("Successfully loaded texture #%s in %s", this.frame + 1, this);
					
				}
				
			}
			
		}
		
	}
	
	@Override
	public boolean disposeImpl(Object... args)
	{
		if (this.frames == null)
		{
			return false;
		}
		
		GL1.glDeleteTextures(this.frames);
		
		return true;
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
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean readAsset(DataInputStream in) throws Throwable
	{
		AssetManager mgr = CaelumEngine.assets();
		
		Object read = mgr.readObjectForAsset(this, in);
		
		if (read == null)
		{
			throw new NullPointerException(String.format("Cannot use null for jack, diddly, or squat in texture asset %s", this));
		}
		
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
	public void delete(RenderContext rcon)
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void onExistingAssetFound(Asset a)
	{
		super.onExistingAssetFound(a);
		
		if (a instanceof TextureAsset)
		{
			TextureAsset ta = (TextureAsset)a;
			
			this.sources = ta.sources;
			this.frames = ta.frames;
			
		}
		
	}
	
	public int getFrameCount()
	{
		return this.frames == null ? 0 : this.frames.length;
	}
	
}
