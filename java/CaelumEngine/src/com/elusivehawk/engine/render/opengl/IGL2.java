
package com.elusivehawk.engine.render.opengl;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

/**
 * 
 * Supports OpenGL v2.0.
 * 
 * @author Elusivehawk
 */
public interface IGL2
{
	public void glAttachShader(int program, int shader);
	
	public void glBindAttribLocation(int program, int index, String name);
	
	public void glBlendEquationSeparate(int modeRGB, int modeAlpha);
	
	public void glCompileShader(int shader);
	
	public int glCreateProgram();
	
	public int glCreateShader(int type);
	
	public void glDeleteProgram(GLProgram program);
	
	public void glDeleteProgram(int program);
	
	public void glDeleteShader(int shader);
	
	public void glDetachShader(int program, int shader);
	
	public void glDisableVertexAttribArray(int index);
	
	public void glEnableVertexAttribArray(int index);
	
	public String glGetActiveAttrib(int program, int index, int maxLength);
	
	public String glGetActiveAttrib(int program, int index, int maxLength, IntBuffer sizeType);
	
	public int glGetActiveAttribSize(int program, int index);
	
	public int glGetActiveAttribType(int program, int index);
	
	public String glGetActiveUniform(int program, int index, int maxLength);
	
	public String glGetActiveUniform(int program, int index, int maxLength, IntBuffer sizeType);
	
	public void glGetActiveUniform(int program, int index, IntBuffer length, IntBuffer size, IntBuffer type, ByteBuffer name);
	
	public int glGetActiveUniformType(int program, int index);
	
	public void glGetAttachedShaders(int program, IntBuffer count, IntBuffer shaders);
	
	public int glGetAttribLocation(int program, String name);
	
	public void glGetProgram(int program, int pname, IntBuffer params);
	
	public int glGetProgrami(int program, int pname);
	
	public String glGetProgramInfoLog(int program, int maxLength);
	
	public void glGetProgramInfoLog(int program, IntBuffer length, ByteBuffer infoLog);
	
	public void glGetShader(int shader, int pname, IntBuffer params);
	
	public int glGetShaderi(int shader, int pname);
	
	public String glGetShaderInfoLog(int shader, int maxLength);
	
	public String glGetShaderSource(int shader, int maxLength);
	
	public void glGetUniform(int program, int location, FloatBuffer params);
	
	public void glGetUniform(int program, int location, IntBuffer params);
	
	public int glGetUniformLocation(int program, String name);
	
	public void glGetVertexAttrib(int index, int pname, FloatBuffer params);
	
	public void glGetVertexAttrib(int index, int pname, IntBuffer params);
	
	//public String glGetVertexAttribPointer(int index, int pname, long result_size);
	
	public boolean glIsProgram(int program);
	
	public boolean glIsShader(int shader);
	
	public void glLinkProgram(GLProgram program);
	
	public void glLinkProgram(int program);
	
	public void glShaderSource(int shader, String string);
	
	public void glStencilFuncSeparate(int face, int func, int ref, int mask);
	
	public void glStencilMaskSeparate(int face, int mask);
	
	public void glStencilOpSeparate(int face, int sfail, int dpfail, int dppass);
	
	public void glUniform1f(int location, float x);
	
	public void glUniform1fv(int location, int count, FloatBuffer v);
	
	public void glUniform1i(int location, int x);
	
	public void glUniform1iv(int location, int count, IntBuffer v);
	
	public void glUniform2f(int location, float x, float y);
	
	public void glUniform2fv(int location, int count, FloatBuffer v);
	
	public void glUniform2i(int location, int x, int y);
	
	public void glUniform2iv(int location, int count, IntBuffer v);
	
	public void glUniform3f(int location, float x, float y, float z);
	
	public void glUniform3fv(int location, int count, FloatBuffer v);
	
	public void glUniform3i(int location, int x, int y, int z);
	
	public void glUniform3iv(int location, int count, IntBuffer v);
	
	public void glUniform4f(int location, float x, float y, float z, float w);
	
	public void glUniform4fv(int location, int count, FloatBuffer v);
	
	public void glUniform4i(int location, int x, int y, int z, int w);
	
	public void glUniform4iv(int location, int count, IntBuffer v);
	
	public void glUniformMatrix2fv(int location, int count, boolean transpose, FloatBuffer value);
	
	public void glUniformMatrix3fv(int location, int count, boolean transpose, FloatBuffer value);
	
	public void glUniformMatrix4fv(int location, int count, boolean transpose, FloatBuffer value);
	
	public void glUseProgram(GLProgram program);
	
	public void glUseProgram(int program);
	
	public void glValidateProgram(GLProgram program);
	
	public void glValidateProgram(int program);
	
	public void glVertexAttrib1f(int index, float x);
	
	public void glVertexAttrib2f(int index, float x, float y);
	
	public void glVertexAttrib3f(int index, float x, float y, float z);
	
	public void glVertexAttrib4f(int index, float x, float y, float z, float w);
	
	public void glVertexAttribPointer(int index, int size, int type, boolean normalized, int stride, java.nio.Buffer buf);
	
}
