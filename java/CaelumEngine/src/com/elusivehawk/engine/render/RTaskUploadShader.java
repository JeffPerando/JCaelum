
package com.elusivehawk.engine.render;

import com.elusivehawk.engine.render.opengl.GLEnumShader;
import com.elusivehawk.util.task.ITaskListener;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class RTaskUploadShader extends RenderTask
{
	private final GLEnumShader type;
	private final String src;
	
	@SuppressWarnings("unqualified-field-access")
	public RTaskUploadShader(ITaskListener tlis, GLEnumShader stype, String source)
	{
		super(tlis);
		type = stype;
		src = source;
		
	}

	@Override
	protected int finishRTask(RenderContext rcon) throws RenderException
	{
		return RenderHelper.loadShader(this.src, this.type, rcon);
	}
	
}
