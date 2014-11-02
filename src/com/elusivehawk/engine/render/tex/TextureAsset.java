
package com.elusivehawk.engine.render.tex;

import java.io.DataInputStream;
import java.util.List;
import com.elusivehawk.engine.CaelumEngine;
import com.elusivehawk.engine.CaelumException;
import com.elusivehawk.engine.assets.Asset;
import com.elusivehawk.engine.assets.AssetManager;
import com.elusivehawk.engine.assets.EnumAssetType;
import com.elusivehawk.engine.render.GraphicAsset;
import com.elusivehawk.engine.render.ILegibleImage;
import com.elusivehawk.engine.render.RTaskUploadImage;
import com.elusivehawk.engine.render.RenderContext;
import com.elusivehawk.util.task.Task;
import com.google.common.collect.Lists;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class TextureAsset extends GraphicAsset implements ITexture
{
	protected int[] frames = null;
	protected int frame = 0;
	
	public TextureAsset(String filepath)
	{
		super(filepath, EnumAssetType.TEXTURE);
		
	}
	
	@Override
	public void delete(RenderContext rcon)
	{
		rcon.getGL1().glDeleteTextures(this.frames);
		
	}
	
	@Override
	public void preRender(RenderContext rcon, double delta)
	{
		if (this.isStatic())
		{
			this.frame++;
			
			if (this.frame == this.getFrameCount())
			{
				this.frame = 0;
			}
			
		}
		
	}
	
	@Override
	public boolean isStatic()
	{
		return this.getFrameCount() > 1;
	}
	
	@Override
	public int getTexId()
	{
		return this.frames == null ? 0 : this.frames[this.frame];
	}
	
	@Override
	public void finishGPULoading(RenderContext rcon)
	{
		rcon.registerPreRenderer(this);
		
	}
	
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
		
		if (imgs.isEmpty())
		{
			return false;
		}
		
		this.frames = new int[imgs.size()];
		
		for (int c = 0; c < imgs.size(); c++)
		{
			CaelumEngine.scheduleRenderTask(new RTaskUploadImage(this, imgs.get(c), c));
			this.frames[c] = 0;
			
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
	
	@Override
	public void onTaskComplete(Task task)
	{
		if (this.isLoaded())
		{
			throw new CaelumException("We're already full up on frames, sir...");
		}
		
		super.onTaskComplete(task);
		
		RTaskUploadImage t = (RTaskUploadImage)task;
		
		this.frames[t.getFrame()] = t.getGLId();
		
		boolean b = true;
		
		for (int c = 0; c < this.getFrameCount(); c++)
		{
			if (this.frames[c] == -1)
			{
				b = false;
				break;
			}
			
		}
		
		this.loaded = b;
		
	}
	
	public int getFrameCount()
	{
		return this.frames == null ? 0 : this.frames.length;
	}
	
}
