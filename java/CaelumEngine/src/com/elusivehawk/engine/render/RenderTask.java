
package com.elusivehawk.engine.render;

import com.elusivehawk.engine.core.CaelumEngine;
import com.elusivehawk.engine.util.task.ITaskListener;
import com.elusivehawk.engine.util.task.Task;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public abstract class RenderTask extends Task
{
	public RenderTask(ITaskListener tlis)
	{
		super(tlis);
		
	}
	
	@Override
	protected final boolean finishTask() throws Throwable
	{
		return this.finishRTask(CaelumEngine.renderContext());
	}
	
	protected abstract boolean finishRTask(RenderContext context);
	
}
