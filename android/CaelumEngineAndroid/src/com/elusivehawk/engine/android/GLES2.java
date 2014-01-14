
package com.elusivehawk.engine.android;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import android.annotation.TargetApi;
import android.opengl.GLES20;
import android.os.Build;
import com.elusivehawk.engine.render.opengl.IGL2;
import com.elusivehawk.engine.util.BufferHelper;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class GLES2 implements IGL2
{
	@Override
	public void glAttachShader(int program, int shader)
	{
		GLES20.glAttachShader(program, shader);
		
	}
	
	@Override
	public void glBindAttribLocation(int program, int index, String name)
	{
		GLES20.glBindAttribLocation(program, index, name);
		
	}
	
	@Override
	public void glBlendEquationSeparate(int modeRGB, int modeAlpha)
	{
		GLES20.glBlendEquationSeparate(modeRGB, modeAlpha);
		
	}
	
	@Override
	public void glCompileShader(int shader)
	{
		GLES20.glCompileShader(shader);
		
	}
	
	@Override
	public int glCreateProgram()
	{
		return GLES20.glCreateProgram();
	}
	
	@Override
	public int glCreateShader(int type)
	{
		return GLES20.glCreateShader(type);
	}
	
	@Override
	public void glDeleteProgram(int program)
	{
		GLES20.glDeleteProgram(program);
		
	}
	
	@Override
	public void glDeleteShader(int shader)
	{
		GLES20.glDeleteShader(shader);
		
	}
	
	@Override
	public void glDetachShader(int program, int shader)
	{
		GLES20.glDetachShader(program, shader);
		
	}
	
	@Override
	public void glDisableVertexAttribArray(int index)
	{
		GLES20.glDisableVertexAttribArray(index);
		
	}
	
	@Override
	public String glGetActiveAttrib(int program, int index, int maxLength)
	{
		return null;//XXX No clue.
	}
	
	@Override
	public String glGetActiveAttrib(int program, int index, int maxLength, IntBuffer sizeType)
	{
		return null;//XXX No clue.
	}
	
	@Override
	public int glGetActiveAttribSize(int program, int index)
	{
		return 0;//XXX No clue.
	}
	
	@Override
	public int glGetActiveAttribType(int program, int index)
	{
		return 0;//XXX No clue.
	}
	
	@Override
	public String glGetActiveUniform(int program, int index, int maxLength)
	{
		return null;//XXX No clue.
	}
	
	@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
	@Override
	public String glGetActiveUniform(int program, int index, int maxLength, IntBuffer sizeType)
	{
		return GLES20.glGetActiveUniform(program, index, BufferHelper.makeIntBuffer(new int[maxLength]), sizeType);//XXX Check usage.
	}
	
	@Override
	public void glGetActiveUniform(int program, int index, IntBuffer length, IntBuffer size, IntBuffer type, ByteBuffer name)
	{
		//XXX No clue.
		
	}
	
	@Override
	public int glGetActiveUniformType(int program, int index)
	{
		return 0;//XXX no clue.
	}
	
	@Override
	public void glGetAttachedShaders(int program, IntBuffer count, IntBuffer shaders)
	{
		GLES20.glGetAttachedShaders(program, count.remaining()/*Check usage*/, count, shaders);
		
	}
	
	@Override
	public int glGetAttribLocation(int program, String name)
	{
		return GLES20.glGetAttribLocation(program, name);
	}
	
	@Override
	public void glGetProgram(int program, int pname, IntBuffer params)
	{
		GLES20.glGetProgramiv(program, pname, params);
		
	}
	
	@Override
	public int glGetProgrami(int program, int pname)
	{
		return 0;//XXX No clue.
	}
	
	@Override
	public String glGetProgramInfoLog(int program, int maxLength)
	{
		String ret = GLES20.glGetProgramInfoLog(program);
		
		if (ret == null)
		{
			return null;
		}
		
		if (ret.length() <= maxLength)
		{
			return ret;
		}
		
		return ret.subSequence(0, maxLength).toString();
	}
	
	@Override
	public void glGetProgramInfoLog(int program, IntBuffer length, ByteBuffer infoLog)
	{
		//XXX No clue.
		
	}
	
	@Override
	public void glGetShader(int shader, int pname, IntBuffer params)
	{
		GLES20.glGetShaderiv(shader, pname, params);
		
	}
	
	@Override
	public int glGetShaderi(int shader, int pname)
	{
		return 0;//XXX No clue.
	}
	
	@Override
	public String glGetShaderInfoLog(int shader, int maxLength)
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void glGetShaderInfoLog(int shader, IntBuffer length,
			ByteBuffer infoLog)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public String glGetShaderSource(int shader, int maxLength)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void glGetShaderSource(int shader, IntBuffer length,
			ByteBuffer source)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void glGetUniform(int program, int location, FloatBuffer params)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void glGetUniform(int program, int location, IntBuffer params)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public int glGetUniformLocation(int program, String name)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void glGetVertexAttrib(int index, int pname, FloatBuffer params)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void glGetVertexAttrib(int index, int pname, IntBuffer params)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public String glGetVertexAttribPointer(int index, int pname,
			long result_size)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean glIsProgram(int program)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean glIsShader(int shader)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void glShaderSource(int shader, String string)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void glStencilFuncSeparate(int face, int func, int ref, int mask)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void glStencilMaskSeparate(int face, int mask)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void glStencilOpSeparate(int face, int sfail, int dpfail, int dppass)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void glUniform1f(int location, float x)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void glUniform1fv(int location, int count, FloatBuffer v)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void glUniform1i(int location, int x)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void glUniform1iv(int location, int count, IntBuffer v)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void glUniform2f(int location, float x, float y)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void glUniform2fv(int location, int count, FloatBuffer v)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void glUniform2i(int location, int x, int y)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void glUniform2iv(int location, int count, IntBuffer v)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void glUniform3f(int location, float x, float y, float z)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void glUniform3fv(int location, int count, FloatBuffer v)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void glUniform3i(int location, int x, int y, int z)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void glUniform3iv(int location, int count, IntBuffer v)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void glUniform4f(int location, float x, float y, float z, float w)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void glUniform4fv(int location, int count, FloatBuffer v)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void glUniform4i(int location, int x, int y, int z, int w)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void glUniform4iv(int location, int count, IntBuffer v)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void glUniformMatrix2fv(int location, int count, boolean transpose,
			FloatBuffer value)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void glUniformMatrix3fv(int location, int count, boolean transpose,
			FloatBuffer value)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void glUniformMatrix4fv(int location, int count, boolean transpose,
			FloatBuffer value)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void glUseProgram(int program)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void glValidateProgram(int program)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void glVertexAttrib1f(int index, float x)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void glVertexAttrib2f(int index, float x, float y)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void glVertexAttrib3f(int index, float x, float y, float z)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void glVertexAttrib4f(int index, float x, float y, float z, float w)
	{
		// TODO Auto-generated method stub
		
	}
	
}
