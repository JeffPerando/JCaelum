
package com.elusivehawk.engine.lwjgl;

import java.nio.IntBuffer;
import org.lwjgl.opengl.GL30;
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
		
	}
	
	@Override
	public void glDeleteVertexArrays(int array)
	{
		GL30.glDeleteVertexArrays(array);
		
	}
	
	@Override
	public void glGenerateMipmap(int target)
	{
		GL30.glGenerateMipmap(target);
		
	}
	
	@Override
	public int glGenVertexArrays()
	{
		return GL30.glGenVertexArrays();
	}
	
	@Override
	public void glGenVertexArrays(int[] arrays)
	{
		IntBuffer a = BufferHelper.createIntBuffer(arrays.length);
		
		GL30.glGenVertexArrays(a);
		
		a.flip();
		
		int c = 0;
		
		while (a.remaining() > 0)
		{
			arrays[c++] = a.get();
			
		}
		
	}
	
}
