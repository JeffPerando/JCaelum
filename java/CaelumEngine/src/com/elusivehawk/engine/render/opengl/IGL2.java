
package com.elusivehawk.engine.render.opengl;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import com.elusivehawk.engine.render.Shader;

/**
 * 
 * Supports OpenGL v2.0.
 * 
 * @author Elusivehawk
 */
public interface IGL2
{
	default void glAttachShader(GLProgram program, Shader shader) throws GLException
	{
		this.glAttachShader(program.getId(), shader.getGLId());
		
	}
	
	public void glAttachShader(int program, int shader) throws GLException;
	
	public void glBindAttribLocation(int program, int index, String name) throws GLException;
	
	public void glBlendEquationSeparate(int modeRGB, int modeAlpha) throws GLException;
	
	default void glCompileShader(Shader shader) throws GLException
	{
		this.glCompileShader(shader.getGLId());
		
	}
	
	public void glCompileShader(int shader) throws GLException;
	
	public int glCreateProgram() throws GLException;
	
	default int glCreateShader(GLEnumShader type) throws GLException
	{
		return this.glCreateShader(type.gl);
	}
	
	public int glCreateShader(int type) throws GLException;
	
	default void glDeleteProgram(GLProgram program) throws GLException
	{
		this.glDeleteProgram(program.getId());
		
	}
	
	public void glDeleteProgram(int program) throws GLException;
	
	default void glDeleteShader(Shader shader) throws GLException
	{
		this.glDeleteShader(shader.getGLId());
		
	}
	
	public void glDeleteShader(int shader) throws GLException;
	
	default void glDetachShader(GLProgram program, Shader shader) throws GLException
	{
		this.glDetachShader(program.getId(), shader.getGLId());
		
	}
	
	public void glDetachShader(int program, int shader) throws GLException;
	
	public void glDisableVertexAttribArray(int index) throws GLException;
	
	public void glEnableVertexAttribArray(int index) throws GLException;
	
	public String glGetActiveAttrib(int program, int index, int maxLength) throws GLException;
	
	public String glGetActiveAttrib(int program, int index, int maxLength, IntBuffer sizeType) throws GLException;
	
	public int glGetActiveAttribSize(int program, int index) throws GLException;
	
	public int glGetActiveAttribType(int program, int index) throws GLException;
	
	public String glGetActiveUniform(int program, int index, int maxLength) throws GLException;
	
	public String glGetActiveUniform(int program, int index, int maxLength, IntBuffer sizeType) throws GLException;
	
	public void glGetActiveUniform(int program, int index, IntBuffer length, IntBuffer size, IntBuffer type, ByteBuffer name) throws GLException;
	
	public int glGetActiveUniformType(int program, int index) throws GLException;
	
	public void glGetAttachedShaders(int program, IntBuffer count, IntBuffer shaders) throws GLException;
	
	public int glGetAttribLocation(int program, String name) throws GLException;
	
	public void glGetProgram(int program, int pname, IntBuffer params) throws GLException;
	
	public int glGetProgrami(int program, int pname) throws GLException;
	
	public String glGetProgramInfoLog(int program, int maxLength) throws GLException;
	
	public void glGetProgramInfoLog(int program, IntBuffer length, ByteBuffer infoLog) throws GLException;
	
	public void glGetShader(int shader, int pname, IntBuffer params) throws GLException;
	
	public int glGetShaderi(int shader, int pname) throws GLException;
	
	public String glGetShaderInfoLog(int shader, int maxLength) throws GLException;
	
	public String glGetShaderSource(int shader, int maxLength) throws GLException;
	
	public void glGetUniform(int program, int location, FloatBuffer params) throws GLException;
	
	public void glGetUniform(int program, int location, IntBuffer params) throws GLException;
	
	public int glGetUniformLocation(int program, String name) throws GLException;
	
	public void glGetVertexAttrib(int index, int pname, FloatBuffer params) throws GLException;
	
	public void glGetVertexAttrib(int index, int pname, IntBuffer params) throws GLException;
	
	//public String glGetVertexAttribPointer(int index, int pname, long result_size);
	
	public boolean glIsProgram(int program);
	
	public boolean glIsShader(int shader);
	
	default void glLinkProgram(GLProgram program) throws GLException
	{
		this.glLinkProgram(program.getId());
		
	}
	
	public void glLinkProgram(int program) throws GLException;
	
	public void glShaderSource(int shader, String string) throws GLException;
	
	public void glStencilFuncSeparate(int face, int func, int ref, int mask) throws GLException;
	
	public void glStencilMaskSeparate(int face, int mask) throws GLException;
	
	public void glStencilOpSeparate(int face, int sfail, int dpfail, int dppass) throws GLException;
	
	public void glUniform1f(int location, float x) throws GLException;
	
	public void glUniform1fv(int location, int count, FloatBuffer v) throws GLException;
	
	public void glUniform1i(int location, int x) throws GLException;
	
	public void glUniform1iv(int location, int count, IntBuffer v) throws GLException;
	
	public void glUniform2f(int location, float x, float y) throws GLException;
	
	public void glUniform2fv(int location, int count, FloatBuffer v) throws GLException;
	
	public void glUniform2i(int location, int x, int y) throws GLException;
	
	public void glUniform2iv(int location, int count, IntBuffer v) throws GLException;
	
	public void glUniform3f(int location, float x, float y, float z) throws GLException;
	
	public void glUniform3fv(int location, int count, FloatBuffer v) throws GLException;
	
	public void glUniform3i(int location, int x, int y, int z) throws GLException;
	
	public void glUniform3iv(int location, int count, IntBuffer v) throws GLException;
	
	public void glUniform4f(int location, float x, float y, float z, float w) throws GLException;
	
	public void glUniform4fv(int location, int count, FloatBuffer v) throws GLException;
	
	public void glUniform4i(int location, int x, int y, int z, int w) throws GLException;
	
	public void glUniform4iv(int location, int count, IntBuffer v) throws GLException;
	
	public void glUniformMatrix2fv(int location, int count, boolean transpose, FloatBuffer value) throws GLException;
	
	public void glUniformMatrix3fv(int location, int count, boolean transpose, FloatBuffer value) throws GLException;
	
	public void glUniformMatrix4fv(int location, int count, boolean transpose, FloatBuffer value) throws GLException;
	
	default void glUseProgram(GLProgram program) throws GLException
	{
		this.glUseProgram(program.getId());
		
	}
	
	public void glUseProgram(int program) throws GLException;
	
	default void glValidateProgram(GLProgram program) throws GLException
	{
		this.glValidateProgram(program.getId());
		
	}
	
	public void glValidateProgram(int program) throws GLException;
	
	public void glVertexAttrib1f(int index, float x) throws GLException;
	
	public void glVertexAttrib2f(int index, float x, float y) throws GLException;
	
	public void glVertexAttrib3f(int index, float x, float y, float z) throws GLException;
	
	public void glVertexAttrib4f(int index, float x, float y, float z, float w) throws GLException;
	
	default void glVertexAttribPointer(int index, int size, int type, boolean normalized, int stride, java.nio.Buffer buf) throws GLException
	{
		this.glVertexAttribPointer(index, size, type, false, normalized, stride, buf);
		
	}
	
	public void glVertexAttribPointer(int index, int size, int type, boolean unsigned, boolean normalized, int stride, java.nio.Buffer buf) throws GLException;
	
}
