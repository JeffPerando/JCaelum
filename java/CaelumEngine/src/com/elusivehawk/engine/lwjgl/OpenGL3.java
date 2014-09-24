
package com.elusivehawk.engine.lwjgl;

import java.nio.IntBuffer;
import org.lwjgl.opengl.GL30;
import com.elusivehawk.engine.render.RenderHelper;
import com.elusivehawk.engine.render.opengl.IGL3;
import com.elusivehawk.util.BufferHelper;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class OpenGL3 extends OpenGL2 implements IGL3
{
	@Override
	public void glBindVertexArray(int array)
	{
		GL30.glBindVertexArray(array);
		
		RenderHelper.checkForGLError(this);
		
	}
	
	@Override
	public void glDeleteVertexArrays(int array)
	{
		GL30.glDeleteVertexArrays(array);
		
		RenderHelper.checkForGLError(this);
		
	}
	
	@Override
	public void glGenerateMipmap(int target)
	{
		GL30.glGenerateMipmap(target);
		
		RenderHelper.checkForGLError(this);
		
	}
	
	@Override
	public int glGenVertexArrays()
	{
		int ret = GL30.glGenVertexArrays();
		
		RenderHelper.checkForGLError(this);
		
		return ret;
	}
	
	@Override
	public void glGenVertexArrays(int[] arrays)
	{
		IntBuffer a = BufferHelper.createWrapper(arrays);
		
		GL30.glGenVertexArrays(a);
		
		RenderHelper.checkForGLError(this);
		
	}
	
}
