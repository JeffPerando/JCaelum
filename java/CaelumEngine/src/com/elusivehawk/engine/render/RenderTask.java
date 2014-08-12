
package com.elusivehawk.engine.render;

import com.elusivehawk.engine.CaelumEngine;
import com.elusivehawk.util.task.ITaskListener;
import com.elusivehawk.util.task.Task;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public abstract class RenderTask extends Task
{
	private int glId = -1;
	
	public RenderTask(ITaskListener tlis)
	{
		super(tlis);
		
	}
	
	@Override
	protected final boolean finishTask() throws Throwable
	{
		boolean ret = true;
		
		try
		{
			this.glId = this.finishRTask(RenderHelper.renderContext());
			
		}
		catch (RenderException e)
		{
			ret = false;
			
			CaelumEngine.log().err(null, e);
			
		}
		
		return ret;
	}
	
	public int getGLId()
	{
		return this.glId;
	}
	
	protected abstract int finishRTask(RenderContext rcon) throws RenderException;
	
}
