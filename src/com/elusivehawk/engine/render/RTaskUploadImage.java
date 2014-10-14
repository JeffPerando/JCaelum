
package com.elusivehawk.engine.render;

import com.elusivehawk.engine.render.old.RenderTask;
import com.elusivehawk.util.task.ITaskListener;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class RTaskUploadImage extends RenderTask
{
	private final ILegibleImage img;
	private final int frame;
	
	@SuppressWarnings("unqualified-field-access")
	public RTaskUploadImage(ITaskListener tlis, ILegibleImage limg, int f)
	{
		super(tlis);
		img = limg;
		frame = f;
		
	}
	
	@Override
	protected int finishRTask(RenderContext rcon) throws RenderException
	{
		return RenderHelper.genTexture(rcon, this.img);
	}
	
	public int getFrame()
	{
		return this.frame;
	}
	
}
