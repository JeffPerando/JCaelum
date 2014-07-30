
package com.elusivehawk.engine.render.two;

import com.elusivehawk.engine.render.ILogicalRender;
import com.elusivehawk.engine.render.RenderSystem;
import com.elusivehawk.engine.render.opengl.GLProgram;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class CanvasLayer implements ILogicalRender
{
	//private FloatBuffer vtx, icons;
	//private FloatBufferer vtxB, iconsB;
	
	@SuppressWarnings("unused")
	public CanvasLayer(int imageCount)
	{
		
	}

	@Override
	public boolean updateBeforeUse(RenderSystem sys)
	{
		return false;
	}
	
	@Override
	public GLProgram getProgram()
	{
		return null;
	}
	
}
