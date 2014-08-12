
package com.elusivehawk.engine.assets;

import java.io.File;
import com.elusivehawk.engine.render.RenderContext;
import com.elusivehawk.util.task.ITaskListener;
import com.elusivehawk.util.task.Task;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class Texture extends GraphicAsset implements ITaskListener
{
	protected int[] frames = null;
	protected int frameCount = -1;
	
	protected Texture(String filepath)
	{
		super(filepath);
		
	}
	
	@Override
	public void onTaskComplete(Task task)
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	protected boolean loadAssetIntoGPU(RenderContext rcon)
	{
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	protected boolean readAsset(File asset) throws Throwable
	{
		// TODO Auto-generated method stub
		return false;
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
