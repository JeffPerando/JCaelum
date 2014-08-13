
package com.elusivehawk.engine.render;

import java.io.File;
import java.util.List;
import com.elusivehawk.engine.CaelumEngine;
import com.elusivehawk.engine.assets.GraphicAsset;
import com.elusivehawk.util.task.Task;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class Texture extends GraphicAsset
{
	protected int[] frames = null;
	protected int frameCount = -1;
	
	public Texture(String filepath)
	{
		super(filepath);
		
	}
	
	@Override
	protected boolean readAsset(File asset) throws Throwable
	{
		List<ILegibleImage> imgs = RenderHelper.readImg(asset);
		
		if (imgs == null || imgs.isEmpty())
		{
			return false;
		}
		
		this.frames = new int[this.frameCount = imgs.size()];
		
		for (int c = 0; c < imgs.size(); c++)
		{
			CaelumEngine.scheduleRenderTask(new RTaskUploadImage(this, imgs.get(c), c));
			this.frames[c] = -1;
			
		}
		
		return true;
	}
	
	@Override
	public void onTaskComplete(Task task)
	{
		if (this.isLoaded())
		{
			throw new RenderException("We're already full up on frames, sir...");
		}
		
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
	
	@Override
	public void delete(RenderContext rcon)
	{
		rcon.getGL1().glDeleteTextures(this.frames);
		
	}
	
	public int getTexId(int frame)
	{
		return this.frames == null ? -1 : this.frames[frame];
	}
	
	public int getFrameCount()
	{
		return this.frameCount;
	}
	
	public boolean isAnimated()
	{
		return this.getFrameCount() > 1;
	}
	
}
