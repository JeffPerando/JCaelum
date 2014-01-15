
package com.elusivehawk.engine.android;

import com.elusivehawk.engine.render.DisplaySettings;
import com.elusivehawk.engine.render.IDisplay;
import com.elusivehawk.engine.render.IRenderEnvironment;
import com.elusivehawk.engine.render.opengl.IGL1;
import com.elusivehawk.engine.render.opengl.IGL2;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class AndroidREnvironment implements IRenderEnvironment
{
	protected final IGL1 GL_1 = new GLES1();
	protected final IGL2 GL_2 = new GLES2();
	protected final Object GL_3 = null;
	protected final Object GL_4  = null;
	
	@Override
	public Object getGL(int version)
	{
		switch (version)
		{
			case 1: return this.GL_1;
			case 2: return this.GL_2;
			case 3: return this.GL_3;
			case 4: return this.GL_4;
			default: return null;
		}
		
	}
	
	@Override
	public IDisplay createDisplay(String name, DisplaySettings settings)
	{
		// TODO Auto-generated method stub
		return null;
	}
	
}
