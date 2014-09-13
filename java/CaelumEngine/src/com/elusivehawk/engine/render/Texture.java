
package com.elusivehawk.engine.render;

import java.io.BufferedInputStream;
import java.util.List;
import com.elusivehawk.engine.CaelumEngine;
import com.elusivehawk.engine.CaelumException;
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
	protected final boolean animate;
	
	protected int[] frames = null;
	protected int frameCount = -1;
	
	public Texture(String filepath)
	{
		this(filepath, filepath.endsWith(".gif"));
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public Texture(String filepath, boolean animated)
	{
		super(filepath);
		
		animate = animated;
		
	}
	
	@Override
	protected boolean readAsset(BufferedInputStream in) throws Throwable
	{
		List<ILegibleImage> imgs = RenderHelper.readImg(in, this.animate);
		
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
