
package com.elusivehawk.caelum.render.gl;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import org.lwjgl.opengl.GL20;
import com.elusivehawk.caelum.render.RenderHelper;
import com.elusivehawk.caelum.render.glsl.GLSLEnumSStatus;
import com.elusivehawk.caelum.render.glsl.GLSLEnumShaderType;
import com.elusivehawk.caelum.render.glsl.IShader;
import com.elusivehawk.util.math.MatrixF;
import com.elusivehawk.util.math.VectorF;
import com.elusivehawk.util.storage.BufferHelper;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public final class GL2
{
	private GL2(){}
	
	public static void glAttachShader(GLProgram program, IShader shader) throws GLException
	{
		glAttachShader(program.getId(), shader.getShaderId());
		
	}
	
	public static void glAttachShader(int program, int shader) throws GLException
	{
		GL20.glAttachShader(program, shader);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glBindAttribLocation(int program, int index, String name) throws GLException
	{
		GL20.glBindAttribLocation(program, index, name);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glBlendEquationSeparate(int modeRGB, int modeAlpha) throws GLException
	{
		GL20.glBlendEquationSeparate(modeRGB, modeAlpha);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glCompileShader(IShader shader) throws GLException
	{
		glCompileShader(shader.getShaderId());
		
	}
	
	public static void glCompileShader(int shader) throws GLException
	{
		GL20.glCompileShader(shader);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static int glCreateProgram() throws GLException
	{
		int ret = GL20.glCreateProgram();
		
		RenderHelper.checkForGLError();
		
		return ret;
	}
	
	public static int glCreateShader(GLSLEnumShaderType type) throws GLException
	{
		return glCreateShader(type.getGLId());
	}
	
	public static int glCreateShader(int type) throws GLException
	{
		int ret = GL20.glCreateShader(type);
		
		RenderHelper.checkForGLError();
		
		return ret;
	}
	
	public static void glDeleteProgram(GLProgram program) throws GLException
	{
		glDeleteProgram(program.getId());
		
	}
	
	public static void glDeleteProgram(int program) throws GLException
	{
		GL20.glDeleteProgram(program);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glDeleteShader(IShader shader) throws GLException
	{
		glDeleteShader(shader.getShaderId());
		
	}
	
	public static void glDeleteShader(int shader) throws GLException
	{
		GL20.glDeleteShader(shader);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glDetachShader(GLProgram program, IShader shader) throws GLException
	{
		glDetachShader(program.getId(), shader.getShaderId());
		
	}
	
	public static void glDetachShader(int program, int shader) throws GLException
	{
		GL20.glDetachShader(program, shader);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glDisableVertexAttribArrays(Iterable<Integer> indices) throws GLException
	{
		indices.forEach(((i) -> {glDisableVertexAttribArray(i);}));
		
	}
	
	public static void glDisableVertexAttribArray(int index) throws GLException
	{
		GL20.glDisableVertexAttribArray(index);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glDrawBuffer(int buffer) throws GLException
	{
		GL20.glDrawBuffers(buffer);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glDrawBuffers(IntBuffer buffers) throws GLException
	{
		GL20.glDrawBuffers(buffers);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glEnableVertexAttribArrays(Iterable<Integer> indices) throws GLException
	{
		indices.forEach(((i) -> {glEnableVertexAttribArray(i);}));
		
	}
	
	public static void glEnableVertexAttribArray(int index) throws GLException
	{
		GL20.glEnableVertexAttribArray(index);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glGetAttachedShaders(int program, IntBuffer count, IntBuffer shaders) throws GLException
	{
		GL20.glGetAttachedShaders(program, count, shaders);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static int glGetAttribLocation(int program, String name) throws GLException
	{
		int ret = GL20.glGetAttribLocation(program, name);
		
		RenderHelper.checkForGLError();
		
		return ret;
	}
	
	public static int glGetProgram(GLProgram program, GLEnumPStatus status) throws GLException
	{
		return glGetProgram(program.getId(), status.gl);
	}
	
	public static int glGetProgram(int program, int pname) throws GLException
	{
		int ret = GL20.glGetProgrami(program, pname);
		
		RenderHelper.checkForGLError();
		
		return ret;
	}
	
	public static String glGetProgramInfoLog(int program, int maxLength) throws GLException
	{
		String ret = GL20.glGetProgramInfoLog(program, maxLength);
		
		RenderHelper.checkForGLError();
		
		return ret;
	}
	
	public static int glGetShader(int shader, GLSLEnumSStatus status) throws GLException
	{
		return glGetShader(shader, status.gl);
	}
	
	public static int glGetShader(IShader shader, GLSLEnumSStatus status) throws GLException
	{
		return glGetShader(shader.getShaderId(), status.gl);
	}
	
	public static int glGetShader(int shader, int pname) throws GLException
	{
		int ret = GL20.glGetShaderi(shader, pname);
		
		RenderHelper.checkForGLError();
		
		return ret;
	}
	
	public static String glGetShaderInfoLog(int shader, int maxLength) throws GLException
	{
		String ret = GL20.glGetShaderInfoLog(shader, maxLength);
		
		RenderHelper.checkForGLError();
		
		return ret;
	}
	
	public static String glGetShaderSource(int shader, int maxLength) throws GLException
	{
		String ret = GL20.glGetShaderSource(shader, maxLength);
		
		RenderHelper.checkForGLError();
		
		return ret;
	}
	
	public static void glGetUniform(String location, FloatBuffer params)
	{
		int p = GL1.glGetInteger(GLConst.GL_CURRENT_PROGRAM);
		
		glGetUniform(p, GL2.glGetUniformLocation(p, location), params);
		
	}
	
	public static void glGetUniform(int location, FloatBuffer params)
	{
		glGetUniform(GL1.glGetInteger(GLConst.GL_CURRENT_PROGRAM), location, params);
		
	}
	
	public static void glGetUniform(int program, int location, FloatBuffer params) throws GLException
	{
		GL20.glGetUniform(program, location, params);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glGetUniform(String location, IntBuffer params)
	{
		int p = GL1.glGetInteger(GLConst.GL_CURRENT_PROGRAM);
		
		glGetUniform(p, GL2.glGetUniformLocation(p, location), params);
		
	}
	
	public static void glGetUniform(int location, IntBuffer params)
	{
		glGetUniform(GL1.glGetInteger(GLConst.GL_CURRENT_PROGRAM), location, params);
		
	}
	
	public static void glGetUniform(int program, int location, IntBuffer params) throws GLException
	{
		GL20.glGetUniform(program, location, params);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static int glGetUniformLocation(String name) throws GLException
	{
		return glGetUniformLocation(GL1.glGetInteger(GLConst.GL_CURRENT_PROGRAM), name);
	}
	
	public static int glGetUniformLocation(int program, String name) throws GLException
	{
		int ret = GL20.glGetUniformLocation(program, name);
		
		RenderHelper.checkForGLError();
		
		return ret;
	}
	
	public static void glGetVertexAttrib(int index, int pname, FloatBuffer params) throws GLException
	{
		GL20.glGetVertexAttrib(index, pname, params);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glGetVertexAttrib(int index, int pname, IntBuffer params) throws GLException
	{
		GL20.glGetVertexAttrib(index, pname, params);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static long glGetVertexAttribPointer(int index, int pname)
	{
		return GL20.glGetVertexAttribPointer(index, pname);
	}
	
	public static boolean glIsProgram(int program)
	{
		return GL20.glIsProgram(program);
	}
	
	public static boolean glIsShader(IShader shader)
	{
		return glIsShader(shader.getShaderId());
	}
	
	public static boolean glIsShader(int shader)
	{
		return GL20.glIsShader(shader);
	}
	
	public static void glLinkProgram(GLProgram program) throws GLException
	{
		glLinkProgram(program.getId());
		
	}
	
	public static void glLinkProgram(int program) throws GLException
	{
		GL20.glLinkProgram(program);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glShaderSource(int shader, String string) throws GLException
	{
		GL20.glShaderSource(shader, string);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glStencilFuncSeparate(int face, int func, int ref, int mask) throws GLException
	{
		GL20.glStencilFuncSeparate(face, func, ref, mask);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glStencilMaskSeparate(int face, int mask) throws GLException
	{
		GL20.glStencilMaskSeparate(face, mask);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glStencilOpSeparate(int face, int sfail, int dpfail, int dppass) throws GLException
	{
		GL20.glStencilOpSeparate(face, sfail, dpfail, dppass);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glUniformf(GLEnumUniformType type, String location, float... data) throws GLException
	{
		glUniformf(type, location, BufferHelper.makeFloatBuffer(data));
		
	}
	
	public static void glUniformf(GLEnumUniformType type, String location, FloatBuffer data) throws GLException
	{
		glUniformf(type, glGetUniformLocation(location), data);
		
	}
	
	public static void glUniformf(GLEnumUniformType type, int location, float... data) throws GLException
	{
		glUniformf(type, location, BufferHelper.makeFloatBuffer(data));
		
	}
	
	public static void glUniformf(GLEnumUniformType type, int location, FloatBuffer data) throws GLException
	{
		glUniformf(type, location, data, false);
		
	}
	
	public static void glUniformf(GLEnumUniformType type, String location, FloatBuffer data, boolean transpose) throws GLException
	{
		glUniformf(type, glGetUniformLocation(location), data, transpose);
		
	}
	
	public static void glUniformf(GLEnumUniformType type, int location, FloatBuffer data, boolean transpose) throws GLException
	{
		switch (type)
		{
			case ONE: glUniform1(location, data); break;
			case TWO: glUniform2(location, data); break;
			case THREE: glUniform3(location, data); break;
			case FOUR: glUniform4(location, data); break;
			case M_TWO: glUniformMatrix2(location, transpose, data); break;
			case M_THREE: glUniformMatrix3(location, transpose, data); break;
			case M_FOUR: glUniformMatrix4(location, transpose, data); break;
			default: throw new GLException("Weird uniform enum: %s", type);
		}
		
	}
	
	public static void glUniformi(GLEnumUniformType type, String location, int... data) throws GLException
	{
		glUniformi(type, location, BufferHelper.makeIntBuffer(data));
		
	}
	
	public static void glUniformi(GLEnumUniformType type, String location, IntBuffer data) throws GLException
	{
		glUniformi(type, glGetUniformLocation(location), data);
		
	}
	
	public static void glUniformi(GLEnumUniformType type, int location, int... data) throws GLException
	{
		glUniformi(type, location, BufferHelper.makeIntBuffer(data));
		
	}
	
	public static void glUniformi(GLEnumUniformType type, int location, IntBuffer data) throws GLException
	{
		switch (type)
		{
			case ONE: glUniform1(location, data.get()); break;
			case TWO: glUniform2(location, data.get(), data.get()); break;
			case THREE: glUniform3(location, data.get(), data.get(), data.get()); break;
			case FOUR: glUniform4(location, data.get(), data.get(), data.get(), data.get()); break;
			default: throw new GLException("Weird uniform enum: %s", type);
		}
		
	}
	
	public static void glUniform1(String location, float x) throws GLException
	{
		glUniform1(glGetUniformLocation(location), x);
		
	}
	
	public static void glUniform1(int location, float x) throws GLException
	{
		GL20.glUniform1f(location, x);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glUniform1(int location, FloatBuffer value) throws GLException
	{
		GL20.glUniform1(location, value);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glUniform1(String location, boolean x) throws GLException
	{
		glUniform1(glGetUniformLocation(location), x);
		
	}
	
	public static void glUniform1(int location, boolean x) throws GLException
	{
		glUniform1(location, x ? 1 : 0);
		
	}
	
	public static void glUniform1(String location, int x) throws GLException
	{
		glUniform1(glGetUniformLocation(location), x);
		
	}
	
	public static void glUniform1(int location, int x) throws GLException
	{
		GL20.glUniform1i(location, x);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glUniform1(int location, IntBuffer v) throws GLException
	{
		GL20.glUniform1(location, v);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glUniform2(String location, VectorF vec) throws GLException
	{
		glUniform2(glGetUniformLocation(location), vec);
		
	}
	
	public static void glUniform2(int location, VectorF vec) throws GLException
	{
		glUniform2(location, vec.get(0), vec.get(1));
		
	}
	
	public static void glUniform2(String location, float x, float y) throws GLException
	{
		glUniform2(glGetUniformLocation(location), x, y);
		
	}
	
	public static void glUniform2(int location, float x, float y) throws GLException
	{
		GL20.glUniform2f(location, x, y);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glUniform2(int location, FloatBuffer v) throws GLException
	{
		GL20.glUniform2(location, v);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glUniform2(String location, int x, int y) throws GLException
	{
		glUniform2(glGetUniformLocation(location), x, y);
		
	}
	
	public static void glUniform2(int location, int x, int y) throws GLException
	{
		GL20.glUniform2i(location, x, y);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glUniform2(int location, IntBuffer v) throws GLException
	{
		GL20.glUniform2(location, v);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glUniform3(String location, VectorF vec) throws GLException
	{
		glUniform3(glGetUniformLocation(location), vec);
		
	}
	
	public static void glUniform3(int location, VectorF vec) throws GLException
	{
		glUniform3(location, vec.get(0), vec.get(1), vec.get(2));
		
	}
	
	public static void glUniform3(String location, float... data) throws GLException
	{
		glUniform3(location, data[0], data[1], data[2]);
		
	}
	
	public static void glUniform3(String location, float x, float y, float z) throws GLException
	{
		glUniform3(glGetUniformLocation(location), x, y, z);
		
	}
	
	public static void glUniform3(int location, float x, float y, float z) throws GLException
	{
		GL20.glUniform3f(location, x, y, z);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glUniform3(int location, FloatBuffer v) throws GLException
	{
		GL20.glUniform3(location, v);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glUniform3(String location, int x, int y, int z) throws GLException
	{
		glUniform3(glGetUniformLocation(location), x, y, z);
		
	}
	
	public static void glUniform3(int location, int x, int y, int z) throws GLException
	{
		GL20.glUniform3i(location, x, y, z);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glUniform3(int location, IntBuffer v) throws GLException
	{
		GL20.glUniform3(location, v);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glUniform4(String location, VectorF vec) throws GLException
	{
		glUniform4(glGetUniformLocation(location), vec);
		
	}
	
	public static void glUniform4(int location, VectorF vec) throws GLException
	{
		glUniform4(location, vec.get(0), vec.get(1), vec.get(2), vec.get(3));
		
	}
	
	public static void glUniform4(String location, float[] data)
	{
		glUniform4(location, data[0], data[1], data[2], data[3]);
		
	}
	
	public static void glUniform4(String location, float x, float y, float z, float w) throws GLException
	{
		glUniform4(glGetUniformLocation(location), x, y, z, w);
		
	}
	
	public static void glUniform4(int location, float x, float y, float z, float w) throws GLException
	{
		GL20.glUniform4f(location, x, y, z, w);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glUniform4(int location, FloatBuffer v) throws GLException
	{
		GL20.glUniform4(location, v);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glUniform4(String location, int x, int y, int z, int w) throws GLException
	{
		glUniform4(glGetUniformLocation(location), x, y, z, w);
		
	}
	
	public static void glUniform4(int location, int x, int y, int z, int w) throws GLException
	{
		GL20.glUniform4i(location, x, y, z, w);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glUniform4(int location, IntBuffer v) throws GLException
	{
		GL20.glUniform4(location, v);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glUniformMatrix2(int location, boolean transpose, FloatBuffer value) throws GLException
	{
		GL20.glUniformMatrix2(location, transpose, value);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glUniformMatrix3(int location, boolean transpose, FloatBuffer value) throws GLException
	{
		GL20.glUniformMatrix3(location, transpose, value);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glUniformMatrix4(String location, MatrixF m) throws GLException
	{
		glUniformMatrix4(location, m.asBuffer());
		
	}
	
	public static void glUniformMatrix4(String location, FloatBuffer value) throws GLException
	{
		glUniformMatrix4(location, false, value);
		
	}
	
	public static void glUniformMatrix4(String location, boolean transpose, FloatBuffer value) throws GLException
	{
		glUniformMatrix4(location, transpose, value);
		
	}
	
	public static void glUniformMatrix4(int location, MatrixF m) throws GLException
	{
		glUniformMatrix4(location, m.asBuffer());
		
	}
	public static void glUniformMatrix4(int location, FloatBuffer value) throws GLException
	{
		glUniformMatrix4(location, false, value);
		
	}
	
	public static void glUniformMatrix4(int location, boolean transpose, FloatBuffer value) throws GLException
	{
		GL20.glUniformMatrix4(location, transpose, value);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glUseProgram(GLProgram program) throws GLException
	{
		glUseProgram(program.getId());
		
	}
	
	public static void glUseProgram(int program) throws GLException
	{
		GL20.glUseProgram(program);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glValidateProgram(GLProgram program) throws GLException
	{
		glValidateProgram(program.getId());
		
	}
	
	public static void glValidateProgram(int program) throws GLException
	{
		GL20.glValidateProgram(program);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glVertexAttrib1f(int index, float x) throws GLException
	{
		GL20.glVertexAttrib1f(index, x);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glVertexAttrib2f(int index, float x, float y) throws GLException
	{
		GL20.glVertexAttrib2f(index, x, y);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glVertexAttrib3f(int index, float x, float y, float z) throws GLException
	{
		GL20.glVertexAttrib3f(index, x, y, z);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glVertexAttrib4f(int index, float x, float y, float z, float w) throws GLException
	{
		GL20.glVertexAttrib4f(index, x, y, z, w);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glVertexAttribPointer(VertexAttrib attrib)
	{
		glVertexAttribPointer(attrib.index, attrib.size, attrib.type, attrib.normalized, attrib.stride, attrib.first);
		
	}
	
	public static void glVertexAttribPointer(int index, int size, int type, boolean normalized, int stride, long first) throws GLException
	{
		GL20.glVertexAttribPointer(index, size, type, normalized, stride, first);
		
		RenderHelper.checkForGLError();
		
	}
	
}
