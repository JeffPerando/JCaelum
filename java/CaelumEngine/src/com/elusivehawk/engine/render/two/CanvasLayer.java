
package com.elusivehawk.engine.render.two;

import java.nio.FloatBuffer;
import com.elusivehawk.engine.render.ILogicalRender;
import com.elusivehawk.engine.render.RenderContext;
import com.elusivehawk.engine.render.opengl.GLProgram;
import com.elusivehawk.util.FloatBufferer;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class CanvasLayer implements ILogicalRender
{
	private FloatBuffer vtx, icons;
	private FloatBufferer vtxB, iconsB;
	
	public CanvasLayer(int imageCount)
	{
		
	}

	@Override
	public boolean updateBeforeUse(RenderContext context)
	{
		return false;
	}
	
	@Override
	public GLProgram getProgram()
	{
		return null;
	}
	
}
