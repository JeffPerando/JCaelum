
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
	
	void glAttachShader(int program, int shader) throws GLException;
	
	void glBindAttribLocation(int program, int index, String name) throws GLException;
	
	void glBlendEquationSeparate(int modeRGB, int modeAlpha) throws GLException;
	
	default void glCompileShader(Shader shader) throws GLException
	{
		this.glCompileShader(shader.getGLId());
		
	}
	
	void glCompileShader(int shader) throws GLException;
	
	int glCreateProgram() throws GLException;
	
	default int glCreateShader(GLEnumShader type) throws GLException
	{
		return this.glCreateShader(type.getGLId());
	}
	
	int glCreateShader(int type) throws GLException;
	
	default void glDeleteProgram(GLProgram program) throws GLException
	{
		this.glDeleteProgram(program.getId());
		
	}
	
	void glDeleteProgram(int program) throws GLException;
	
	default void glDeleteShader(Shader shader) throws GLException
	{
		this.glDeleteShader(shader.getGLId());
		
	}
	
	void glDeleteShader(int shader) throws GLException;
	
	default void glDetachShader(GLProgram program, Shader shader) throws GLException
	{
		this.glDetachShader(program.getId(), shader.getGLId());
		
	}
	
	void glDetachShader(int program, int shader) throws GLException;
	
	void glDisableVertexAttribArray(int index) throws GLException;
	
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
	
	default int glGetProgrami(GLProgram program, GLEnumPStatus status) throws GLException
	{
		return this.glGetProgrami(program.getId(), status.gl);
	}
	
	int glGetProgrami(int program, int pname) throws GLException;
	
	String glGetProgramInfoLog(int program, int maxLength) throws GLException;
	
	void glGetProgramInfoLog(int program, IntBuffer length, ByteBuffer infoLog) throws GLException;
	
	void glGetShader(int shader, int pname, IntBuffer params) throws GLException;
	
	default int glGetShaderi(int shader, GLEnumSStatus status) throws GLException
	{
		return this.glGetShaderi(shader, status.gl);
	}
	
	default int glGetShaderi(Shader shader, GLEnumSStatus status) throws GLException
	{
		return this.glGetShaderi(shader.getGLId(), status.gl);
	}
	
	int glGetShaderi(int shader, int pname) throws GLException;
	
	String glGetShaderInfoLog(int shader, int maxLength) throws GLException;
	
	String glGetShaderSource(int shader, int maxLength) throws GLException;
	
	void glGetUniform(int program, int location, FloatBuffer params) throws GLException;
	
	void glGetUniform(int program, int location, IntBuffer params) throws GLException;
	
	int glGetUniformLocation(int program, String name) throws GLException;
	
	void glGetVertexAttrib(int index, int pname, FloatBuffer params) throws GLException;
	
	void glGetVertexAttrib(int index, int pname, IntBuffer params) throws GLException;
	
	//String glGetVertexAttribPointer(int index, int pname, long result_size);
	
	boolean glIsEnabled(int cap);
	
	boolean glIsProgram(int program);
	
	boolean glIsShader(int shader);
	
	default void glLinkProgram(GLProgram program) throws GLException
	{
		this.glLinkProgram(program.getId());
		
	}
	
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
	
	default void glUseProgram(GLProgram program) throws GLException
	{
		this.glUseProgram(program.getId());
		
	}
	
	void glUseProgram(int program) throws GLException;
	
	default void glValidateProgram(GLProgram program) throws GLException
	{
		this.glValidateProgram(program.getId());
		
	}
	
	void glValidateProgram(int program) throws GLException;
	
	void glVertexAttrib1f(int index, float x) throws GLException;
	
	void glVertexAttrib2f(int index, float x, float y) throws GLException;
	
	void glVertexAttrib3f(int index, float x, float y, float z) throws GLException;
	
	void glVertexAttrib4f(int index, float x, float y, float z, float w) throws GLException;
	
	default void glVertexAttribPointer(GLProgram program, VertexAttrib attrib)
	{
		this.glVertexAttribPointer(program.getId(), attrib);
		
	}
	
	default void glVertexAttribPointer(int program, VertexAttrib attrib)
	{
		this.glVertexAttribPointer(this.glGetAttribLocation(program, attrib.name), attrib.size, attrib.type, attrib.unsigned, attrib.normalized, attrib.stride, attrib.first);
		
	}
	
	default void glVertexAttribPointer(int index, int size, int type, boolean normalized, int stride, long first) throws GLException
	{
		this.glVertexAttribPointer(index, size, type, false, normalized, stride, first);
		
	}
	
	void glVertexAttribPointer(int index, int size, int type, boolean unsigned, boolean normalized, int stride, long first) throws GLException;
	
}
