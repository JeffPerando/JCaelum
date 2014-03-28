
package com.elusivehawk.engine.android;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import android.annotation.TargetApi;
import android.opengl.GLES20;
import android.os.Build;
import com.elusivehawk.engine.assets.Asset;
import com.elusivehawk.engine.assets.EnumAssetType;
import com.elusivehawk.engine.render.opengl.GLEnumShader;
import com.elusivehawk.engine.render.opengl.GLProgram;
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
	public void glAttachShader(GLProgram program, Asset shader)
	{
		assert shader.type == EnumAssetType.SHADER;
		
		this.glAttachShader(program.getId(), shader.getIds()[0]);
		
	}
	
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
	public void glCompileShader(GLEnumShader type)
	{
		this.glCompileShader(type.gl);
		
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
	public int glCreateShader(GLEnumShader type)
	{
		return this.glCreateShader(type.gl);
	}
	
	@Override
	public int glCreateShader(int type)
	{
		return GLES20.glCreateShader(type);
	}
	
	@Override
	public void glDeleteProgram(GLProgram program)
	{
		this.glDeleteProgram(program.getId());
		
	}
	
	@Override
	public void glDeleteProgram(int program)
	{
		GLES20.glDeleteProgram(program);
		
	}
	
	@Override
	public void glDeleteShader(Asset shader)
	{
		assert shader.type == EnumAssetType.SHADER;
		
		this.glDeleteShader(shader.getIds()[0]);
		
	}
	
	@Override
	public void glDeleteShader(int shader)
	{
		GLES20.glDeleteShader(shader);
		
	}
	
	@Override
	public void glDetachShader(GLProgram program, Asset shader)
	{
		assert shader.type == EnumAssetType.SHADER;
		
		this.glDetachShader(program.getId(), shader.getIds()[0]);
		
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
	public void glEnableVertexAttribArray(int index)
	{
		GLES20.glEnableVertexAttribArray(index);
		
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
		String ret = GLES20.glGetShaderInfoLog(shader);
		
		if (ret == null)
		{
			return ret;
		}
		
		if (ret.length() > maxLength)
		{
			return ret.subSequence(0, maxLength).toString();
		}
		
		return ret;
	}
	
	@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
	@Override
	public String glGetShaderSource(int shader, int maxLength)
	{
		String ret = GLES20.glGetShaderSource(shader);
		
		if (ret == null)
		{
			return ret;
		}
		
		if (ret.length() > maxLength)
		{
			return ret.subSequence(0, maxLength).toString();
		}
		
		return ret;
	}
	
	@Override
	public void glGetUniform(int program, int location, FloatBuffer params)
	{
		GLES20.glGetUniformfv(program, location, params);
		
	}
	
	@Override
	public void glGetUniform(int program, int location, IntBuffer params)
	{
		GLES20.glGetUniformiv(program, location, params);
		
	}
	
	@Override
	public int glGetUniformLocation(int program, String name)
	{
		return GLES20.glGetUniformLocation(program, name);
	}
	
	@Override
	public void glGetVertexAttrib(int index, int pname, FloatBuffer params)
	{
		GLES20.glGetVertexAttribfv(index, pname, params);
		
	}
	
	@Override
	public void glGetVertexAttrib(int index, int pname, IntBuffer params)
	{
		GLES20.glGetVertexAttribiv(index, pname, params);
		
	}
	
	@Override
	public boolean glIsProgram(int program)
	{
		return GLES20.glIsProgram(program);
	}
	
	@Override
	public boolean glIsShader(int shader)
	{
		return GLES20.glIsShader(shader);
	}
	
	@Override
	public void glLinkProgram(GLProgram program)
	{
		this.glLinkProgram(program.getId());
		
	}
	
	@Override
	public void glLinkProgram(int program)
	{
		GLES20.glLinkProgram(program);
		
	}
	
	@Override
	public void glShaderSource(int shader, String string)
	{
		GLES20.glShaderSource(shader, string);
		
	}
	
	@Override
	public void glStencilFuncSeparate(int face, int func, int ref, int mask)
	{
		GLES20.glStencilFuncSeparate(face, func, ref, mask);
		
	}
	
	@Override
	public void glStencilMaskSeparate(int face, int mask)
	{
		GLES20.glStencilMaskSeparate(face, mask);
		
	}
	
	@Override
	public void glStencilOpSeparate(int face, int sfail, int dpfail, int dppass)
	{
		GLES20.glStencilOpSeparate(face, sfail, dpfail, dppass);
		
	}
	
	@Override
	public void glUniform1f(int location, float x)
	{
		GLES20.glUniform1f(location, x);
		
	}
	
	@Override
	public void glUniform1fv(int location, int count, FloatBuffer v)
	{
		GLES20.glUniform1fv(location, count, v);
		
	}
	
	@Override
	public void glUniform1i(int location, int x)
	{
		GLES20.glUniform1i(location, x);
		
	}
	
	@Override
	public void glUniform1iv(int location, int count, IntBuffer v)
	{
		GLES20.glUniform1iv(location, count, v);
		
	}
	
	@Override
	public void glUniform2f(int location, float x, float y)
	{
		GLES20.glUniform2f(location, x, y);
		
	}
	
	@Override
	public void glUniform2fv(int location, int count, FloatBuffer v)
	{
		GLES20.glUniform2fv(location, count, v);
		
	}
	
	@Override
	public void glUniform2i(int location, int x, int y)
	{
		GLES20.glUniform2i(location, x, y);
		
	}
	
	@Override
	public void glUniform2iv(int location, int count, IntBuffer v)
	{
		GLES20.glUniform2iv(location, count, v);
		
	}
	
	@Override
	public void glUniform3f(int location, float x, float y, float z)
	{
		GLES20.glUniform3f(location, x, y, z);
		
	}
	
	@Override
	public void glUniform3fv(int location, int count, FloatBuffer v)
	{
		GLES20.glUniform3fv(location, count, v);
		
	}
	
	@Override
	public void glUniform3i(int location, int x, int y, int z)
	{
		GLES20.glUniform3i(location, x, y, z);
		
	}
	
	@Override
	public void glUniform3iv(int location, int count, IntBuffer v)
	{
		GLES20.glUniform3iv(location, count, v);
		
	}
	
	@Override
	public void glUniform4f(int location, float x, float y, float z, float w)
	{
		GLES20.glUniform4f(location, x, y, z, w);
		
	}
	
	@Override
	public void glUniform4fv(int location, int count, FloatBuffer v)
	{
		GLES20.glUniform4fv(location, count, v);
		
	}
	
	@Override
	public void glUniform4i(int location, int x, int y, int z, int w)
	{
		GLES20.glUniform4i(location, x, y, z, w);
		
	}
	
	@Override
	public void glUniform4iv(int location, int count, IntBuffer v)
	{
		GLES20.glUniform4iv(location, count, v);
		
	}
	
	@Override
	public void glUniformMatrix2fv(int location, int count, boolean transpose, FloatBuffer value)
	{
		GLES20.glUniformMatrix2fv(location, count, transpose, value);
		
	}
	
	@Override
	public void glUniformMatrix3fv(int location, int count, boolean transpose, FloatBuffer value)
	{
		GLES20.glUniformMatrix3fv(location, count, transpose, value);
		
	}
	
	@Override
	public void glUniformMatrix4fv(int location, int count, boolean transpose, FloatBuffer value)
	{
		GLES20.glUniformMatrix3fv(location, count, transpose, value);
		
	}
	
	@Override
	public void glUseProgram(GLProgram program)
	{
		this.glUseProgram(program.getId());
		
	}
	
	@Override
	public void glUseProgram(int program)
	{
		GLES20.glUseProgram(program);
		
	}
	
	@Override
	public void glValidateProgram(GLProgram program)
	{
		this.glValidateProgram(program.getId());
		
	}
	
	@Override
	public void glValidateProgram(int program)
	{
		GLES20.glValidateProgram(program);
		
	}
	
	@Override
	public void glVertexAttrib1f(int index, float x)
	{
		GLES20.glVertexAttrib1f(index, x);
		
	}
	
	@Override
	public void glVertexAttrib2f(int index, float x, float y)
	{
		GLES20.glVertexAttrib2f(index, x, y);
		
	}
	
	@Override
	public void glVertexAttrib3f(int index, float x, float y, float z)
	{
		GLES20.glVertexAttrib3f(index, x, y, z);
		
	}
	
	@Override
	public void glVertexAttrib4f(int index, float x, float y, float z, float w)
	{
		GLES20.glVertexAttrib4f(index, x, y, z, w);
		
	}
	
	@Override
	public void glVertexAttribPointer(int index, int size, int type, boolean normalized, int stride, Buffer buf)
	{
		GLES20.glVertexAttribPointer(index, size, type, normalized, stride, buf);
		
	}
	
}
