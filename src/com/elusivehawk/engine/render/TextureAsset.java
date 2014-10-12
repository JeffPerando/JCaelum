
package com.elusivehawk.engine.render;

import java.io.InputStream;
import java.util.List;
import com.elusivehawk.engine.CaelumEngine;
import com.elusivehawk.engine.CaelumException;
import com.elusivehawk.engine.assets.Asset;
import com.elusivehawk.engine.assets.EnumAssetType;
import com.elusivehawk.util.task.Task;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class TextureAsset extends GraphicAsset implements ITexture
{
	private final boolean animate;
	
	protected volatile int[] frames = null;
	protected int frame = 0;
	
	public TextureAsset(String filepath)
	{
		this(filepath, filepath.endsWith(".gif"));
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public TextureAsset(String filepath, boolean animated)
	{
		super(filepath, EnumAssetType.TEXTURE);
		
		animate = animated;
		
	}
	
	@Override
	public void delete(RenderContext rcon)
	{
		rcon.getGL1().glDeleteTextures(this.frames);
		
	}
	
	@Override
	protected boolean readAsset(InputStream in) throws Throwable
	{
		List<ILegibleImage> imgs = RenderHelper.readImg(in, this.animate);
		
		if (imgs == null || imgs.isEmpty())
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
	
	@Override
	public void preRender(RenderContext rcon, double delta)
	{
		if (this.isAnimated())
		{
			this.frame++;
			
			if (this.frame == this.getFrameCount())
			{
				this.frame = 0;
			}
			
		}
		
	}
	
	@Override
	public boolean isAnimated()
	{
		return this.animate && this.getFrameCount() > 1;
	}
	
	@Override
	public int getTexId()
	{
		return this.frames == null ? 0 : this.frames[this.frame];
	}
	
	@Override
	public void initR(RenderContext rcon)
	{
		super.initR(rcon);
		
		rcon.registerPreRenderer(this);
		
	}
	
}
