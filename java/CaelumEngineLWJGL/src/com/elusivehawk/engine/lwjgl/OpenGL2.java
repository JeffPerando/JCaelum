
package com.elusivehawk.engine.lwjgl;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import org.lwjgl.opengl.GL20;
import com.elusivehawk.engine.render.opengl.GLConst;
import com.elusivehawk.engine.render.opengl.IGL2;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class OpenGL2 implements IGL2
{
	@Override
	public void glAttachShader(int program, int shader)
	{
		GL20.glAttachShader(program, shader);
		
	}
	
	@Override
	public void glBindAttribLocation(int program, int index, String name)
	{
		GL20.glBindAttribLocation(program, index, name);
		
	}
	
	@Override
	public void glBlendEquationSeparate(int modeRGB, int modeAlpha)
	{
		GL20.glBlendEquationSeparate(modeRGB, modeAlpha);
		
	}
	
	@Override
	public void glCompileShader(int shader)
	{
		GL20.glCompileShader(shader);
		
	}
	
	@Override
	public int glCreateProgram()
	{
		return GL20.glCreateProgram();
	}
	
	@Override
	public int glCreateShader(int type)
	{
		return GL20.glCreateShader(type);
	}
	
	@Override
	public void glDeleteProgram(int program)
	{
		GL20.glDeleteProgram(program);
		
	}
	
	@Override
	public void glDeleteShader(int shader)
	{
		GL20.glDeleteShader(shader);
		
	}
	
	@Override
	public void glDetachShader(int program, int shader)
	{
		GL20.glDetachShader(program, shader);
		
	}
	
	@Override
	public void glDisableVertexAttribArray(int index)
	{
		GL20.glDisableVertexAttribArray(index);
		
	}
	
	@Override
	public void glEnableVertexAttribArray(int index)
	{
		GL20.glEnableVertexAttribArray(index);
	}
	
	@Override
	public String glGetActiveAttrib(int program, int index, int maxLength)
	{
		return GL20.glGetActiveAttrib(program, index, maxLength);
	}
	
	@Override
	public String glGetActiveAttrib(int program, int index, int maxLength, IntBuffer sizeType)
	{
		return GL20.glGetActiveAttrib(program, index, maxLength, sizeType);
	}
	
	@Override
	public int glGetActiveAttribSize(int program, int index)
	{
		return GL20.glGetActiveAttribSize(program, index);
	}
	
	@Override
	public int glGetActiveAttribType(int program, int index)
	{
		return GL20.glGetActiveAttribType(program, index);
	}
	
	@Override
	public String glGetActiveUniform(int program, int index, int maxLength)
	{
		return GL20.glGetActiveUniform(program, index, maxLength);
	}
	
	@Override
	public String glGetActiveUniform(int program, int index, int maxLength, IntBuffer sizeType)
	{
		return GL20.glGetActiveUniform(program, index, maxLength, sizeType);
	}
	
	@Override
	public void glGetActiveUniform(int program, int index, IntBuffer length, IntBuffer size, IntBuffer type, ByteBuffer name)
	{
		GL20.glGetActiveUniform(program, index, length, size, type, name);
		
	}
	
	@Override
	public int glGetActiveUniformType(int program, int index)
	{
		return GL20.glGetActiveUniformType(program, index);
	}
	
	@Override
	public void glGetAttachedShaders(int program, IntBuffer count, IntBuffer shaders)
	{
		GL20.glGetAttachedShaders(program, count, shaders);
		
	}
	
	@Override
	public int glGetAttribLocation(int program, String name)
	{
		return GL20.glGetAttribLocation(program, name);
	}
	
	@Override
	public void glGetProgram(int program, int pname, IntBuffer params)
	{
		GL20.glGetProgram(program, pname, params);
		
	}
	
	@Override
	public int glGetProgrami(int program, int pname)
	{
		return GL20.glGetProgrami(program, pname);
	}
	
	@Override
	public String glGetProgramInfoLog(int program, int maxLength)
	{
		return GL20.glGetProgramInfoLog(program, maxLength);
	}
	
	@Override
	public void glGetProgramInfoLog(int program, IntBuffer length, ByteBuffer infoLog)
	{
		GL20.glGetProgramInfoLog(program, length, infoLog);
		
	}
	
	@Override
	public void glGetShader(int shader, int pname, IntBuffer params)
	{
		GL20.glGetShader(shader, pname, params);
		
	}
	
	@Override
	public int glGetShaderi(int shader, int pname)
	{
		return GL20.glGetShaderi(shader, pname);
	}
	
	@Override
	public String glGetShaderInfoLog(int shader, int maxLength)
	{
		return GL20.glGetShaderInfoLog(shader, maxLength);
	}
	
	@Override
	public String glGetShaderSource(int shader, int maxLength)
	{
		// TODO Auto-generated method stub
		return null;
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
	public boolean glIsProgram(int program)
	{
		return GL20.glIsProgram(program);
	}
	
	@Override
	public boolean glIsShader(int shader)
	{
		return GL20.glIsShader(shader);
	}
	
	@Override
	public void glLinkProgram(int program)
	{
		GL20.glLinkProgram(program);
	}
	
	@Override
	public void glShaderSource(int shader, String string)
	{
		GL20.glShaderSource(shader, string);
		
	}
	
	@Override
	public void glStencilFuncSeparate(int face, int func, int ref, int mask)
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void glStencilMaskSeparate(int face, int mask)
	{
		GL20.glStencilMaskSeparate(face, mask);
		
	}
	
	@Override
	public void glStencilOpSeparate(int face, int sfail, int dpfail, int dppass)
	{
		GL20.glStencilOpSeparate(face, sfail, dpfail, dppass);
		
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
		GL20.glUseProgram(program);
		
	}
	
	@Override
	public void glValidateProgram(int program)
	{
		GL20.glValidateProgram(program);
		
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
	
	@Override
	public void glVertexAttribPointer(int index, int size, int type, boolean normalized, int stride, Buffer buffer)
	{
		switch (type)
		{
			case GLConst.GL_BYTE: GL20.glVertexAttribPointer(index, size, false, normalized, stride, (ByteBuffer)buffer);
			case GLConst.GL_SHORT: GL20.glVertexAttribPointer(index, size, false, normalized, stride, (ShortBuffer)buffer);
			case GLConst.GL_INT: GL20.glVertexAttribPointer(index, size, false, normalized, stride, (IntBuffer)buffer);
			case GLConst.GL_FLOAT: GL20.glVertexAttribPointer(index, size, false, stride, (FloatBuffer)buffer);
			case GLConst.GL_DOUBLE: GL20.glVertexAttribPointer(index, size, false, stride, (DoubleBuffer)buffer);
			
		}
		
	}
	
}
