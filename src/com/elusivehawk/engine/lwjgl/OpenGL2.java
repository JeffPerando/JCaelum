
package com.elusivehawk.engine.lwjgl;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import com.elusivehawk.engine.render.RenderHelper;
import com.elusivehawk.engine.render.gl.GLException;
import com.elusivehawk.engine.render.gl.IGL2;
import com.elusivehawk.util.BufferHelper;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class OpenGL2 extends OpenGL1 implements IGL2
{
	@Override
	public void glAttachShader(int program, int shader) throws GLException
	{
		GL20.glAttachShader(program, shader);
		
		RenderHelper.checkForGLError(this);
		
	}
	
	@Override
	public void glBindAttribLocation(int program, int index, String name) throws GLException
	{
		GL20.glBindAttribLocation(program, index, name);
		
		RenderHelper.checkForGLError(this);
		
	}
	
	@Override
	public void glBlendEquationSeparate(int modeRGB, int modeAlpha) throws GLException
	{
		GL20.glBlendEquationSeparate(modeRGB, modeAlpha);
		
		RenderHelper.checkForGLError(this);
		
	}
	
	@Override
	public void glCompileShader(int shader) throws GLException
	{
		GL20.glCompileShader(shader);
		
		RenderHelper.checkForGLError(this);
		
	}
	
	@Override
	public int glCreateProgram() throws GLException
	{
		int ret = GL20.glCreateProgram();
		
		RenderHelper.checkForGLError(this);
		
		return ret;
	}
	
	@Override
	public int glCreateShader(int type) throws GLException
	{
		int ret = GL20.glCreateShader(type);
		
		RenderHelper.checkForGLError(this);
		
		return ret;
	}
	
	@Override
	public void glDeleteProgram(int program) throws GLException
	{
		GL20.glDeleteProgram(program);
		
		RenderHelper.checkForGLError(this);
		
	}
	
	@Override
	public void glDeleteShader(int shader) throws GLException
	{
		GL20.glDeleteShader(shader);
		
		RenderHelper.checkForGLError(this);
		
	}
	
	@Override
	public void glDetachShader(int program, int shader) throws GLException
	{
		GL20.glDetachShader(program, shader);
		
		RenderHelper.checkForGLError(this);
		
	}
	
	@Override
	public void glDisableVertexAttribArray(int index) throws GLException
	{
		GL20.glDisableVertexAttribArray(index);
		
		RenderHelper.checkForGLError(this);
		
	}
	
	@Override
	public void glDrawBuffer(int buffer) throws GLException
	{
		GL20.glDrawBuffers(buffer);
		
	}
	
	@Override
	public void glDrawBuffers(int[] buffers) throws GLException
	{
		GL20.glDrawBuffers(BufferHelper.createWrapper(buffers));
		
	}
	
	@Override
	public void glEnableVertexAttribArray(int index) throws GLException
	{
		GL20.glEnableVertexAttribArray(index);
		
		RenderHelper.checkForGLError(this);
		
	}
	
	@Override
	public String glGetActiveAttrib(int program, int index, int maxLength) throws GLException
	{
		String ret = GL20.glGetActiveAttrib(program, index, maxLength);
		
		RenderHelper.checkForGLError(this);
		
		return ret;
	}
	
	@Override
	public String glGetActiveAttrib(int program, int index, int maxLength, IntBuffer sizeType) throws GLException
	{
		String ret = GL20.glGetActiveAttrib(program, index, maxLength, sizeType);
		
		RenderHelper.checkForGLError(this);
		
		return ret;
	}
	
	@Override
	public int glGetActiveAttribSize(int program, int index) throws GLException
	{
		int ret = GL20.glGetActiveAttribSize(program, index);
		
		RenderHelper.checkForGLError(this);
		
		return ret;
	}
	
	@Override
	public int glGetActiveAttribType(int program, int index) throws GLException
	{
		int ret = GL20.glGetActiveAttribType(program, index);
		
		RenderHelper.checkForGLError(this);
		
		return ret;
	}
	
	@Override
	public String glGetActiveUniform(int program, int index, int maxLength) throws GLException
	{
		String ret = GL20.glGetActiveUniform(program, index, maxLength);
		
		RenderHelper.checkForGLError(this);
		
		return ret;
	}
	
	@Override
	public String glGetActiveUniform(int program, int index, int maxLength, IntBuffer sizeType) throws GLException
	{
		String ret = GL20.glGetActiveUniform(program, index, maxLength, sizeType);
		
		RenderHelper.checkForGLError(this);
		
		return ret;
	}
	
	@Override
	public void glGetActiveUniform(int program, int index, IntBuffer length, IntBuffer size, IntBuffer type, ByteBuffer name) throws GLException
	{
		GL20.glGetActiveUniform(program, index, length, size, type, name);
		
		RenderHelper.checkForGLError(this);
		
	}
	
	@Override
	public int glGetActiveUniformType(int program, int index) throws GLException
	{
		int ret = GL20.glGetActiveUniformType(program, index);
		
		RenderHelper.checkForGLError(this);
		
		return ret;
	}
	
	@Override
	public void glGetAttachedShaders(int program, IntBuffer count, IntBuffer shaders) throws GLException
	{
		GL20.glGetAttachedShaders(program, count, shaders);
		
		RenderHelper.checkForGLError(this);
		
	}
	
	@Override
	public int glGetAttribLocation(int program, String name) throws GLException
	{
		int ret = GL20.glGetAttribLocation(program, name);
		
		RenderHelper.checkForGLError(this);
		
		return ret;
	}
	
	@Override
	public void glGetProgram(int program, int pname, IntBuffer params) throws GLException
	{
		GL20.glGetProgram(program, pname, params);
		
		RenderHelper.checkForGLError(this);
		
	}
	
	@Override
	public int glGetProgrami(int program, int pname) throws GLException
	{
		int ret = GL20.glGetProgrami(program, pname);
		
		RenderHelper.checkForGLError(this);
		
		return ret;
	}
	
	@Override
	public String glGetProgramInfoLog(int program, int maxLength) throws GLException
	{
		String ret = GL20.glGetProgramInfoLog(program, maxLength);
		
		RenderHelper.checkForGLError(this);
		
		return ret;
	}
	
	@Override
	public void glGetProgramInfoLog(int program, IntBuffer length, ByteBuffer infoLog) throws GLException
	{
		GL20.glGetProgramInfoLog(program, length, infoLog);
		
		RenderHelper.checkForGLError(this);
		
	}
	
	@Override
	public void glGetShader(int shader, int pname, IntBuffer params) throws GLException
	{
		GL20.glGetShader(shader, pname, params);
		
		RenderHelper.checkForGLError(this);
		
	}
	
	@Override
	public int glGetShaderi(int shader, int pname) throws GLException
	{
		int ret = GL20.glGetShaderi(shader, pname);
		
		RenderHelper.checkForGLError(this);
		
		return ret;
	}
	
	@Override
	public String glGetShaderInfoLog(int shader, int maxLength) throws GLException
	{
		String ret = GL20.glGetShaderInfoLog(shader, maxLength);
		
		RenderHelper.checkForGLError(this);
		
		return ret;
	}
	
	@Override
	public String glGetShaderSource(int shader, int maxLength) throws GLException
	{
		String ret = GL20.glGetShaderSource(shader, maxLength);
		
		RenderHelper.checkForGLError(this);
		
		return ret;
	}

	@Override
	public void glGetUniform(int program, int location, FloatBuffer params) throws GLException
	{
		GL20.glGetUniform(program, location, params);
		
		RenderHelper.checkForGLError(this);
		
	}

	@Override
	public void glGetUniform(int program, int location, IntBuffer params) throws GLException
	{
		GL20.glGetUniform(program, location, params);
		
		RenderHelper.checkForGLError(this);
		
	}

	@Override
	public int glGetUniformLocation(int program, String name) throws GLException
	{
		int ret = GL20.glGetUniformLocation(program, name);
		
		RenderHelper.checkForGLError(this);
		
		return ret;
	}

	@Override
	public void glGetVertexAttrib(int index, int pname, FloatBuffer params) throws GLException
	{
		GL20.glGetVertexAttrib(index, pname, params);
		
		RenderHelper.checkForGLError(this);
		
	}

	@Override
	public void glGetVertexAttrib(int index, int pname, IntBuffer params) throws GLException
	{
		GL20.glGetVertexAttrib(index, pname, params);
		
		RenderHelper.checkForGLError(this);
		
	}
	
	@Override
	public boolean glIsEnabled(int cap)
	{
		return GL11.glIsEnabled(cap);
	}
	
	@Override
	public boolean glIsProgram(int program) throws GLException
	{
		boolean ret = GL20.glIsProgram(program);
		
		RenderHelper.checkForGLError(this);
		
		return ret;
	}
	
	@Override
	public boolean glIsShader(int shader) throws GLException
	{
		boolean ret = GL20.glIsShader(shader);
		
		RenderHelper.checkForGLError(this);
		
		return ret;
	}
	
	@Override
	public void glLinkProgram(int program) throws GLException
	{
		GL20.glLinkProgram(program);
		
		RenderHelper.checkForGLError(this);
		
	}
	
	@Override
	public void glShaderSource(int shader, String string) throws GLException
	{
		GL20.glShaderSource(shader, string);
		
		RenderHelper.checkForGLError(this);
		
	}
	
	@Override
	public void glStencilFuncSeparate(int face, int func, int ref, int mask) throws GLException
	{
		GL20.glStencilFuncSeparate(face, func, ref, mask);
		
		RenderHelper.checkForGLError(this);
		
	}
	
	@Override
	public void glStencilMaskSeparate(int face, int mask) throws GLException
	{
		GL20.glStencilMaskSeparate(face, mask);
		
		RenderHelper.checkForGLError(this);
		
	}
	
	@Override
	public void glStencilOpSeparate(int face, int sfail, int dpfail, int dppass) throws GLException
	{
		GL20.glStencilOpSeparate(face, sfail, dpfail, dppass);
		
		RenderHelper.checkForGLError(this);
		
	}
	
	@Override
	public void glUniform1f(int location, float x) throws GLException
	{
		GL20.glUniform1f(location, x);
		
		RenderHelper.checkForGLError(this);
		
	}
	
	@Override
	public void glUniform1fv(int location, int count, FloatBuffer v) throws GLException
	{
		GL20.glUniform1(location, v);
		
		RenderHelper.checkForGLError(this);
		
	}
	
	@Override
	public void glUniform1i(int location, int x) throws GLException
	{
		GL20.glUniform1i(location, x);
		
		RenderHelper.checkForGLError(this);
		
	}
	
	@Override
	public void glUniform1iv(int location, int count, IntBuffer v) throws GLException
	{
		GL20.glUniform1(location, v);

		RenderHelper.checkForGLError(this);
		
	}
	
	@Override
	public void glUniform2f(int location, float x, float y) throws GLException
	{
		GL20.glUniform2f(location, x, y);

		RenderHelper.checkForGLError(this);
		
	}
	
	@Override
	public void glUniform2fv(int location, int count, FloatBuffer v) throws GLException
	{
		GL20.glUniform2(location, v);

		RenderHelper.checkForGLError(this);
		
	}
	
	@Override
	public void glUniform2i(int location, int x, int y) throws GLException
	{
		GL20.glUniform2i(location, x, y);

		RenderHelper.checkForGLError(this);
		
	}
	
	@Override
	public void glUniform2iv(int location, int count, IntBuffer v) throws GLException
	{
		GL20.glUniform2(location, v);
		
		RenderHelper.checkForGLError(this);
		
	}
	
	@Override
	public void glUniform3f(int location, float x, float y, float z) throws GLException
	{
		GL20.glUniform3f(location, x, y, z);
		
		RenderHelper.checkForGLError(this);
		
	}
	
	@Override
	public void glUniform3fv(int location, int count, FloatBuffer v) throws GLException
	{
		GL20.glUniform3(location, v);
		
		RenderHelper.checkForGLError(this);
		
	}
	
	@Override
	public void glUniform3i(int location, int x, int y, int z) throws GLException
	{
		GL20.glUniform3i(location, x, y, z);
		
		RenderHelper.checkForGLError(this);
		
	}
	
	@Override
	public void glUniform3iv(int location, int count, IntBuffer v) throws GLException
	{
		GL20.glUniform3(location, v);
		
		RenderHelper.checkForGLError(this);
		
	}
	
	@Override
	public void glUniform4f(int location, float x, float y, float z, float w) throws GLException
	{
		GL20.glUniform4f(location, x, y, z, w);
		
		RenderHelper.checkForGLError(this);
		
	}
	
	@Override
	public void glUniform4fv(int location, int count, FloatBuffer v) throws GLException
	{
		GL20.glUniform4(location, v);
		
		RenderHelper.checkForGLError(this);
		
	}
	
	@Override
	public void glUniform4i(int location, int x, int y, int z, int w) throws GLException
	{
		GL20.glUniform4i(location, x, y, z, w);
		
		RenderHelper.checkForGLError(this);
		
	}
	
	@Override
	public void glUniform4iv(int location, int count, IntBuffer v) throws GLException
	{
		GL20.glUniform4(location, v);
		
		RenderHelper.checkForGLError(this);
		
	}
	
	@Override
	public void glUniformMatrix2fv(int location, int count, boolean transpose, FloatBuffer value) throws GLException
	{
		GL20.glUniformMatrix2(location, transpose, value);
		
		RenderHelper.checkForGLError(this);
		
	}
	
	@Override
	public void glUniformMatrix3fv(int location, int count, boolean transpose, FloatBuffer value) throws GLException
	{
		GL20.glUniformMatrix3(location, transpose, value);
		
		RenderHelper.checkForGLError(this);
		
	}
	
	@Override
	public void glUniformMatrix4fv(int location, int count, boolean transpose, FloatBuffer value) throws GLException
	{
		GL20.glUniformMatrix4(location, transpose, value);
		
		RenderHelper.checkForGLError(this);
		
	}
	
	@Override
	public void glUseProgram(int program) throws GLException
	{
		GL20.glUseProgram(program);
		
		RenderHelper.checkForGLError(this);
		
	}
	
	@Override
	public void glValidateProgram(int program) throws GLException
	{
		GL20.glValidateProgram(program);
		
		RenderHelper.checkForGLError(this);
		
	}
	
	@Override
	public void glVertexAttrib1f(int index, float x) throws GLException
	{
		GL20.glVertexAttrib1f(index, x);

		RenderHelper.checkForGLError(this);
		
	}
	
	@Override
	public void glVertexAttrib2f(int index, float x, float y) throws GLException
	{
		GL20.glVertexAttrib2f(index, x, y);

		RenderHelper.checkForGLError(this);
		
	}
	
	@Override
	public void glVertexAttrib3f(int index, float x, float y, float z) throws GLException
	{
		GL20.glVertexAttrib3f(index, x, y, z);

		RenderHelper.checkForGLError(this);
		
	}
	
	@Override
	public void glVertexAttrib4f(int index, float x, float y, float z, float w) throws GLException
	{
		GL20.glVertexAttrib4f(index, x, y, z, w);

		RenderHelper.checkForGLError(this);
		
	}
	
	@Override
	public void glVertexAttribPointer(int index, int size, int type, boolean unsigned, boolean normalized, int stride, long first) throws GLException
	{
		GL20.glVertexAttribPointer(index, size, type, normalized, stride, first);
		
		RenderHelper.checkForGLError(this);
		
	}
	
}
