
package com.elusivehawk.engine.lwjgl;

import com.elusivehawk.engine.render.DisplaySettings;
import com.elusivehawk.engine.render.IDisplay;
import com.elusivehawk.engine.render.IRenderEnvironment;
import com.elusivehawk.engine.render.RenderContext;
import com.elusivehawk.util.concurrent.IThreadStoppable;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class OpenGLEnvironment implements IRenderEnvironment
{
	protected final OpenGL3 GL_3 = new OpenGL3();
	protected final Object GL_4 = null;
	
	@Override
	public boolean initiate()
	{
		// TODO Auto-generated method stub
		return true;
	}
	
	@Override
	public Object getGL(int version)
	{
		switch (version)
		{
			case 1:
			case 2:
			case 3: return this.GL_3;
			case 4: return this.GL_4;
			default: return null;
		}
		
	}
	
	@Override
	public IDisplay createDisplay(DisplaySettings settings)
	{
		LWJGLDisplay ret = new LWJGLDisplay();
		
		ret.updateSettings(settings);
		
		return ret;
	}
	
	@Override
	public IThreadStoppable createRenderThread(RenderContext rcon)
	{
		return null;
	}
	
}
