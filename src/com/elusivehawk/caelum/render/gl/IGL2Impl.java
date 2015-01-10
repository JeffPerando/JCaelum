
package com.elusivehawk.caelum.render.gl;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

/**
 * 
 * OpenGL v2.x wrapper interface
 * 
 * @author Elusivehawk
 */
public interface IGL2Impl
{
	void glAttachShader(int program, int shader) throws GLException;
	
	void glBindAttribLocation(int program, int index, String name) throws GLException;
	
	void glBlendEquationSeparate(int modeRGB, int modeAlpha) throws GLException;
	
	void glCompileShader(int shader) throws GLException;
	
	int glCreateProgram() throws GLException;
	
	int glCreateShader(int type) throws GLException;
	
	void glDeleteProgram(int program) throws GLException;
	
	void glDeleteShader(int shader) throws GLException;
	
	void glDetachShader(int program, int shader) throws GLException;
	
	void glDisableVertexAttribArray(int index) throws GLException;
	
	void glDrawBuffer(int buffer) throws GLException;
	
	void glDrawBuffers(int[] buffers) throws GLException;
	
	void glEnableVertexAttribArray(int index) throws GLException;
	
	String glGetActiveAttrib(int program, int index, int maxLength) throws GLException;
	
	String glGetActiveAttrib(int program, int index, int maxLength, IntBuffer sizeType) throws GLException;
	
	int glGetActiveAttribSize(int program, int index) throws GLException;
	
	int glGetActiveAttribType(int program, int index) throws GLException;
	
	String glGetActiveUniform(int program, int index, int maxLength) throws GLException;
	
	String glGetActiveUniform(int program, int index, int maxLength, IntBuffer sizeType) throws GLException;
	
	void glGetActiveUniform(int program, int index, IntBuffer length, IntBuffer size, IntBuffer type, ByteBuffer name) throws GLException;
	
	int glGetActiveUniformType(int program, int index) throws GLException;
	
	void glGetAttachedShaders(int program, IntBuffer count, IntBuffer shaders) throws GLException;
	
	int glGetAttribLocation(int program, String name) throws GLException;
	
	void glGetProgram(int program, int pname, IntBuffer params) throws GLException;
	
	int glGetProgrami(int program, int pname) throws GLException;
	
	String glGetProgramInfoLog(int program, int maxLength) throws GLException;
	
	void glGetProgramInfoLog(int program, IntBuffer length, ByteBuffer infoLog) throws GLException;
	
	void glGetShader(int shader, int pname, IntBuffer params) throws GLException;
	
	int glGetShaderi(int shader, int pname) throws GLException;
	
	String glGetShaderInfoLog(int shader, int maxLength) throws GLException;
	
	String glGetShaderSource(int shader, int maxLength) throws GLException;
	
	void glGetUniform(int program, int location, FloatBuffer params) throws GLException;
	
	void glGetUniform(int program, int location, IntBuffer params) throws GLException;
	
	int glGetUniformLocation(int program, String name) throws GLException;
	
	void glGetVertexAttrib(int index, int pname, FloatBuffer params) throws GLException;
	
	void glGetVertexAttrib(int index, int pname, IntBuffer params) throws GLException;
	
	//String glGetVertexAttribPointer(int index, int pname, long result_size);
	
	boolean glIsProgram(int program);
	
	boolean glIsShader(int shader);
	
	void glLinkProgram(int program) throws GLException;
	
	void glShaderSource(int shader, String string) throws GLException;
	
	void glStencilFuncSeparate(int face, int func, int ref, int mask) throws GLException;
	
	void glStencilMaskSeparate(int face, int mask) throws GLException;
	
	void glStencilOpSeparate(int face, int sfail, int dpfail, int dppass) throws GLException;
	
	void glUniform1f(int location, float x) throws GLException;
	
	void glUniform1fv(int location, int count, FloatBuffer v) throws GLException;
	
	void glUniform1i(int location, int x) throws GLException;
	
	void glUniform1iv(int location, int count, IntBuffer v) throws GLException;
	
	void glUniform2f(int location, float x, float y) throws GLException;
	
	void glUniform2fv(int location, int count, FloatBuffer v) throws GLException;
	
	void glUniform2i(int location, int x, int y) throws GLException;
	
	void glUniform2iv(int location, int count, IntBuffer v) throws GLException;
	
	void glUniform3f(int location, float x, float y, float z) throws GLException;
	
	void glUniform3fv(int location, int count, FloatBuffer v) throws GLException;
	
	void glUniform3i(int location, int x, int y, int z) throws GLException;
	
	void glUniform3iv(int location, int count, IntBuffer v) throws GLException;
	
	void glUniform4f(int location, float x, float y, float z, float w) throws GLException;
	
	void glUniform4fv(int location, int count, FloatBuffer v) throws GLException;
	
	void glUniform4i(int location, int x, int y, int z, int w) throws GLException;
	
	void glUniform4iv(int location, int count, IntBuffer v) throws GLException;
	
	void glUniformMatrix2fv(int location, int count, boolean transpose, FloatBuffer value) throws GLException;
	
	void glUniformMatrix3fv(int location, int count, boolean transpose, FloatBuffer value) throws GLException;
	
	void glUniformMatrix4fv(int location, int count, boolean transpose, FloatBuffer value) throws GLException;
	
	void glUseProgram(int program) throws GLException;
	
	void glValidateProgram(int program) throws GLException;
	
	void glVertexAttrib1f(int index, float x) throws GLException;
	
	void glVertexAttrib2f(int index, float x, float y) throws GLException;
	
	void glVertexAttrib3f(int index, float x, float y, float z) throws GLException;
	
	void glVertexAttrib4f(int index, float x, float y, float z, float w) throws GLException;
	
	void glVertexAttribPointer(int index, int size, int type, boolean unsigned, boolean normalized, int stride, long first) throws GLException;
	
}
