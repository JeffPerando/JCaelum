
package com.elusivehawk.engine.assets;

import java.io.File;
import java.util.List;
import com.elusivehawk.engine.CaelumEngine;
import com.elusivehawk.engine.render.ILegibleImage;
import com.elusivehawk.engine.render.RTaskUploadImage;
import com.elusivehawk.engine.render.RenderHelper;
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
			
		}
		
		return true;
	}
	
	@Override
	public void onTaskComplete(Task task)
	{
		RTaskUploadImage t = (RTaskUploadImage)task;
		
		this.frames[t.getFrame()] = t.getGLId();
		
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
