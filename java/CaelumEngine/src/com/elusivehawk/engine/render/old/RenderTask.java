
package com.elusivehawk.engine.render.old;

import com.elusivehawk.engine.render.RenderContext;
import com.elusivehawk.engine.render.RenderException;
import com.elusivehawk.engine.render.RenderHelper;
import com.elusivehawk.util.Logger;
import com.elusivehawk.util.task.ITaskListener;
import com.elusivehawk.util.task.Task;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 * 
 * @deprecated To be removed when OpenGL NG support comes out.
 */
@Deprecated
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
			
			Logger.log().err(null, e);
			
		}
		
		return ret;
	}
	
	public int getGLId()
	{
		return this.glId;
	}
	
	protected abstract int finishRTask(RenderContext rcon) throws RenderException;
	
}
