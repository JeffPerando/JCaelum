
package com.elusivehawk.caelum.render.gl;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import com.elusivehawk.caelum.render.RenderHelper;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public final class GL2
{
	private static IGL2Impl impl = null;
	
	private GL2(){}
	
	public static void setImpl(IGL2Impl gl)
	{
		assert impl == null;
		assert gl != null;
		
		impl = gl;
		
	}
	
	public static void glAttachShader(GLProgram program, Shader shader) throws GLException
	{
		glAttachShader(program.getId(), shader.getShaderId());
		
	}
	
	public static void glAttachShader(int program, int shader) throws GLException
	{
		impl.glAttachShader(program, shader);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glBindAttribLocation(int program, int index, String name) throws GLException
	{
		impl.glBindAttribLocation(program, index, name);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glBlendEquationSeparate(int modeRGB, int modeAlpha) throws GLException
	{
		impl.glBlendEquationSeparate(modeRGB, modeAlpha);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glCompileShader(Shader shader) throws GLException
	{
		glCompileShader(shader.getShaderId());
		
	}
	
	public static void glCompileShader(int shader) throws GLException
	{
		impl.glCompileShader(shader);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static int glCreateProgram() throws GLException
	{
		int ret = impl.glCreateProgram();
		
		RenderHelper.checkForGLError();
		
		return ret;
	}
	
	public static int glCreateShader(GLEnumShader type) throws GLException
	{
		return glCreateShader(type.getGLId());
	}
	
	public static int glCreateShader(int type) throws GLException
	{
		int ret = impl.glCreateShader(type);
		
		RenderHelper.checkForGLError();
		
		return ret;
	}
	
	public static void glDeleteProgram(GLProgram program) throws GLException
	{
		glDeleteProgram(program.getId());
		
	}
	
	public static void glDeleteProgram(int program) throws GLException
	{
		impl.glDeleteProgram(program);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glDeleteShader(Shader shader) throws GLException
	{
		glDeleteShader(shader.getShaderId());
		
	}
	
	public static void glDeleteShader(int shader) throws GLException
	{
		impl.glDeleteShader(shader);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glDetachShader(GLProgram program, Shader shader) throws GLException
	{
		glDetachShader(program.getId(), shader.getShaderId());
		
	}
	
	public static void glDetachShader(int program, int shader) throws GLException
	{
		impl.glDetachShader(program, shader);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glDisableVertexAttribArray(int index) throws GLException
	{
		impl.glDisableVertexAttribArray(index);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glDrawBuffer(int buffer) throws GLException
	{
		impl.glDrawBuffer(buffer);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glDrawBuffers(int[] buffers) throws GLException
	{
		impl.glDrawBuffers(buffers);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glEnableVertexAttribArray(int index) throws GLException
	{
		impl.glEnableVertexAttribArray(index);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static String glGetActiveAttrib(int program, int index, int maxLength) throws GLException
	{
		String ret = impl.glGetActiveAttrib(program, index, maxLength);
		
		RenderHelper.checkForGLError();
		
		return ret;
	}
	
	public static String glGetActiveAttrib(int program, int index, int maxLength, IntBuffer sizeType) throws GLException
	{
		String ret = impl.glGetActiveAttrib(program, index, maxLength, sizeType);
		
		RenderHelper.checkForGLError();
		
		return ret;
	}
	
	public static int glGetActiveAttribSize(int program, int index) throws GLException
	{
		int ret = impl.glGetActiveAttribSize(program, index);
		
		RenderHelper.checkForGLError();
		
		return ret;
	}
	
	public static int glGetActiveAttribType(int program, int index) throws GLException
	{
		int ret = impl.glGetActiveAttribType(program, index);
		
		RenderHelper.checkForGLError();
		
		return ret;
	}
	
	public static String glGetActiveUniform(int program, int index, int maxLength) throws GLException
	{
		String ret = impl.glGetActiveUniform(program, index, maxLength);
		
		RenderHelper.checkForGLError();
		
		return ret;
	}
	
	public static String glGetActiveUniform(int program, int index, int maxLength, IntBuffer sizeType) throws GLException
	{
		String ret = impl.glGetActiveUniform(program, index, maxLength, sizeType);
		
		RenderHelper.checkForGLError();
		
		return ret;
	}
	
	public static void glGetActiveUniform(int program, int index, IntBuffer length, IntBuffer size, IntBuffer type, ByteBuffer name) throws GLException
	{
		impl.glGetActiveUniform(program, index, length, size, type, name);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static int glGetActiveUniformType(int program, int index) throws GLException
	{
		int ret = impl.glGetActiveUniformType(program, index);
		
		RenderHelper.checkForGLError();
		
		return ret;
	}
	
	public static void glGetAttachedShaders(int program, IntBuffer count, IntBuffer shaders) throws GLException
	{
		impl.glGetAttachedShaders(program, count, shaders);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static int glGetAttribLocation(int program, String name) throws GLException
	{
		int ret = impl.glGetAttribLocation(program, name);
		
		RenderHelper.checkForGLError();
		
		return ret;
	}
	
	public static void glGetProgram(int program, int pname, IntBuffer params) throws GLException
	{
		impl.glGetProgram(program, pname, params);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static int glGetProgrami(GLProgram program, GLEnumPStatus status) throws GLException
	{
		return glGetProgrami(program.getId(), status.gl);
	}
	
	public static int glGetProgrami(int program, int pname) throws GLException
	{
		int ret = impl.glGetProgrami(program, pname);
		
		RenderHelper.checkForGLError();
		
		return ret;
	}
	
	public static String glGetProgramInfoLog(int program, int maxLength) throws GLException
	{
		String ret = impl.glGetProgramInfoLog(program, maxLength);
		
		RenderHelper.checkForGLError();
		
		return ret;
	}
	
	public static void glGetProgramInfoLog(int program, IntBuffer length, ByteBuffer infoLog) throws GLException
	{
		impl.glGetProgramInfoLog(program, length, infoLog);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glGetShader(int shader, int pname, IntBuffer params) throws GLException
	{
		impl.glGetShader(shader, pname, params);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static int glGetShaderi(int shader, GLEnumSStatus status) throws GLException
	{
		return glGetShaderi(shader, status.gl);
	}
	
	public static int glGetShaderi(Shader shader, GLEnumSStatus status) throws GLException
	{
		return glGetShaderi(shader.getShaderId(), status.gl);
	}
	
	public static int glGetShaderi(int shader, int pname) throws GLException
	{
		int ret = impl.glGetShaderi(shader, pname);
		
		RenderHelper.checkForGLError();
		
		return ret;
	}
	
	public static String glGetShaderInfoLog(int shader, int maxLength) throws GLException
	{
		String ret = impl.glGetShaderInfoLog(shader, maxLength);
		
		RenderHelper.checkForGLError();
		
		return ret;
	}
	
	public static String glGetShaderSource(int shader, int maxLength) throws GLException
	{
		String ret = impl.glGetShaderSource(shader, maxLength);
		
		RenderHelper.checkForGLError();
		
		return ret;
	}
	
	public static void glGetUniform(int program, int location, FloatBuffer params) throws GLException
	{
		impl.glGetUniform(program, location, params);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glGetUniform(int program, int location, IntBuffer params) throws GLException
	{
		impl.glGetUniform(program, location, params);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static int glGetUniformLocation(int program, String name) throws GLException
	{
		int ret = impl.glGetUniformLocation(program, name);
		
		RenderHelper.checkForGLError();
		
		return ret;
	}
	
	public static void glGetVertexAttrib(int index, int pname, FloatBuffer params) throws GLException
	{
		impl.glGetVertexAttrib(index, pname, params);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glGetVertexAttrib(int index, int pname, IntBuffer params) throws GLException
	{
		impl.glGetVertexAttrib(index, pname, params);
		
		RenderHelper.checkForGLError();
		
	}
	
	//String glGetVertexAttribPointer(int index, int pname, long result_size);
	
	public static boolean glIsEnabled(int cap)
	{
		return impl.glIsEnabled(cap);
	}
	
	public static boolean glIsProgram(int program)
	{
		return impl.glIsProgram(program);
	}
	
	public static boolean glIsShader(Shader shader)
	{
		return glIsShader(shader.getShaderId());
	}
	
	public static boolean glIsShader(int shader)
	{
		return impl.glIsShader(shader);
	}
	
	public static void glLinkProgram(GLProgram program) throws GLException
	{
		glLinkProgram(program.getId());
		
	}
	
	public static void glLinkProgram(int program) throws GLException
	{
		impl.glLinkProgram(program);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glShaderSource(int shader, String string) throws GLException
	{
		impl.glShaderSource(shader, string);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glStencilFuncSeparate(int face, int func, int ref, int mask) throws GLException
	{
		impl.glStencilFuncSeparate(face, func, ref, mask);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glStencilMaskSeparate(int face, int mask) throws GLException
	{
		impl.glStencilMaskSeparate(face, mask);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glStencilOpSeparate(int face, int sfail, int dpfail, int dppass) throws GLException
	{
		impl.glStencilOpSeparate(face, sfail, dpfail, dppass);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glUniform1f(int location, float x) throws GLException
	{
		impl.glUniform1f(location, x);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glUniform1fv(int location, int count, FloatBuffer v) throws GLException
	{
		impl.glUniform1fv(location, count, v);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glUniform1i(int location, int x) throws GLException
	{
		impl.glUniform1i(location, x);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glUniform1iv(int location, int count, IntBuffer v) throws GLException
	{
		impl.glUniform1iv(location, count, v);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glUniform2f(int location, float x, float y) throws GLException
	{
		impl.glUniform2f(location, x, y);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glUniform2fv(int location, int count, FloatBuffer v) throws GLException
	{
		impl.glUniform2fv(location, count, v);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glUniform2i(int location, int x, int y) throws GLException
	{
		impl.glUniform2i(location, x, y);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glUniform2iv(int location, int count, IntBuffer v) throws GLException
	{
		impl.glUniform2iv(location, count, v);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glUniform3f(int location, float x, float y, float z) throws GLException
	{
		impl.glUniform3f(location, x, y, z);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glUniform3fv(int location, int count, FloatBuffer v) throws GLException
	{
		impl.glUniform3fv(location, count, v);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glUniform3i(int location, int x, int y, int z) throws GLException
	{
		impl.glUniform3i(location, x, y, z);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glUniform3iv(int location, int count, IntBuffer v) throws GLException
	{
		impl.glUniform3iv(location, count, v);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glUniform4f(int location, float x, float y, float z, float w) throws GLException
	{
		impl.glUniform4f(location, x, y, z, w);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glUniform4fv(int location, int count, FloatBuffer v) throws GLException
	{
		impl.glUniform4fv(location, count, v);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glUniform4i(int location, int x, int y, int z, int w) throws GLException
	{
		impl.glUniform4i(location, x, y, z, w);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glUniform4iv(int location, int count, IntBuffer v) throws GLException
	{
		impl.glUniform4iv(location, count, v);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glUniformMatrix2fv(int location, int count, boolean transpose, FloatBuffer value) throws GLException
	{
		impl.glUniformMatrix2fv(location, count, transpose, value);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glUniformMatrix3fv(int location, int count, boolean transpose, FloatBuffer value) throws GLException
	{
		impl.glUniformMatrix3fv(location, count, transpose, value);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glUniformMatrix4fv(int location, int count, boolean transpose, FloatBuffer value) throws GLException
	{
		impl.glUniformMatrix4fv(location, count, transpose, value);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glUseProgram(GLProgram program) throws GLException
	{
		glUseProgram(program.getId());
		
	}
	
	public static void glUseProgram(int program) throws GLException
	{
		impl.glUseProgram(program);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glValidateProgram(GLProgram program) throws GLException
	{
		glValidateProgram(program.getId());
		
	}
	
	public static void glValidateProgram(int program) throws GLException
	{
		impl.glValidateProgram(program);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glVertexAttrib1f(int index, float x) throws GLException
	{
		impl.glVertexAttrib1f(index, x);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glVertexAttrib2f(int index, float x, float y) throws GLException
	{
		impl.glVertexAttrib2f(index, x, y);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glVertexAttrib3f(int index, float x, float y, float z) throws GLException
	{
		impl.glVertexAttrib3f(index, x, y, z);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glVertexAttrib4f(int index, float x, float y, float z, float w) throws GLException
	{
		impl.glVertexAttrib4f(index, x, y, z, w);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glVertexAttribPointer(GLProgram program, VertexAttrib attrib)
	{
		glVertexAttribPointer(program.getId(), attrib);
		
	}
	
	public static void glVertexAttribPointer(int program, VertexAttrib attrib)
	{
		glVertexAttribPointer(attrib.index, attrib.size, attrib.type, attrib.unsigned, attrib.normalized, attrib.stride, attrib.first);
		
	}
	
	public static void glVertexAttribPointer(int index, int size, int type, boolean normalized, int stride, long first) throws GLException
	{
		glVertexAttribPointer(index, size, type, false, normalized, stride, first);
		
	}
	
	public static void glVertexAttribPointer(int index, int size, int type, boolean unsigned, boolean normalized, int stride, long first) throws GLException
	{
		impl.glVertexAttribPointer(index, size, type, unsigned, normalized, stride, first);
		
		RenderHelper.checkForGLError();
		
	}
	
}
