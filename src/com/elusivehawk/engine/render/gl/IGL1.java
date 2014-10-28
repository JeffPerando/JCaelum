
package com.elusivehawk.engine.render.gl;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import com.elusivehawk.engine.render.tex.Color;
import com.elusivehawk.engine.render.tex.ColorFilter;
import com.elusivehawk.engine.render.tex.ITexture;
import com.elusivehawk.util.storage.BufferHelper;

/**
 * 
 * Supports OpenGL versions 1.0/1.1 to 1.5.
 * 
 * @author Elusivehawk
 */
public interface IGL1
{
	void glActiveTexture(int texture) throws GLException;
	
	default void glBindBuffer(VertexBuffer vbo)
	{
		this.glBindBuffer(vbo.getTarget(), vbo.getId());
		
	}
	
	default void glBindBuffer(GLEnumBufferTarget target, int buffer) throws GLException
	{
		this.glBindBuffer(target.getGLId(), buffer);
		
	}
	
	void glBindBuffer(int target, int buffer) throws GLException;
	
	default void glBindTexture(GLEnumTexture target, ITexture texture)
	{
		this.glBindTexture(target, texture.getTexId());
		
	}
	
	void glBindTexture(GLEnumTexture target, int texture) throws GLException;
	
	void glBlendFunc(int sfactor, int dfactor) throws GLException;
	
	default void glBufferData(GLEnumBufferTarget target, GLEnumDataType type, Buffer data, GLEnumDataUsage usage) throws GLException
	{
		this.glBufferData(target.getGLId(), type.getGLId(), data, usage.getGLId());
		
	}
	
	void glBufferData(int target, int type, Buffer data, int usage) throws GLException;
	
	default void glBufferSubData(VertexBuffer buffer, int offset, Buffer data) throws GLException
	{
		this.glBufferSubData(buffer.getTarget(), offset, buffer.getDataType(), data);
	}
	
	default void glBufferSubData(GLEnumBufferTarget target, int offset, GLEnumDataType type, Buffer data) throws GLException
	{
		this.glBufferSubData(target.getGLId() * type.getByteCount(), offset, type.getGLId(), data);
		
	}
	
	void glBufferSubData(int target, int offset, int type, Buffer data) throws GLException;
	
	void glClear(int mask) throws GLException;
	
	default void glClearColor(Color col)
	{
		this.glClearColor(col.getColorf(ColorFilter.RED), col.getColorf(ColorFilter.GREEN), col.getColorf(ColorFilter.BLUE), col.getColorf(ColorFilter.ALPHA));
		
	}
	
	void glClearColor(float r, float g, float b, float a);
	
	void glCopyTexImage1D(int target, int level, int internalFormat, int x, int y, int width, int border) throws GLException;
	
	void glCopyTexImage2D(int target, int level, int internalFormat, int x, int y, int width, int height, int border) throws GLException;
	
	void glCopyTexSubImage1D(int target, int level, int xoffset, int x, int y, int width) throws GLException;
	
	void glCopyTexSubImage2D(int target, int level, int xoffset, int yoffset, int x, int y, int width, int height) throws GLException;
	
	void glCopyTexSubImage3D(int target, int level, int xoffset, int yoffset, int zoffset, int x, int y, int width, int height) throws GLException;
	
	void glCullFace(int mode) throws GLException;
	
	default void glDeleteBuffers(VertexBuffer... buffers)
	{
		IntBuffer bufs = BufferHelper.createIntBuffer(buffers.length);
		
		for (VertexBuffer vb : buffers)
		{
			bufs.put(vb.getId());
			
		}
		
		this.glDeleteBuffers(bufs);
		
	}
	
	void glDeleteBuffers(int... buffer) throws GLException;
	
	void glDeleteBuffers(IntBuffer buffers) throws GLException;
	
	void glDeleteTextures(int... textures) throws GLException;
	
	void glDeleteTextures(IntBuffer textures) throws GLException;
	
	void glDepthFunc(int func) throws GLException;
	
	void glDepthMask(boolean flag) throws GLException;
	
	void glDepthRange(float zNear, float zFar) throws GLException;
	
	void glDisable(int cap) throws GLException;
	
	void glDrawArrays(int mode, int first, int count) throws GLException;
	
	default void glDrawElements(GLEnumDrawType mode, int count, int type, IntBuffer indices) throws GLException
	{
		this.glDrawElements(mode.getGLId(), count, type, indices);
		
	}
	
	void glDrawElements(int mode, int count, int type, IntBuffer indices) throws GLException;
	
	default void glDrawElements(GLEnumDrawType mode, int count, int type, int offset) throws GLException
	{
		this.glDrawElements(mode.getGLId(), count, type, offset);
		
	}
	
	void glDrawElements(int mode, int count, int type, int offset) throws GLException;
	
	void glEnable(int cap) throws GLException;
	
	void glFinish();
	
	void glFlush();
	
	void glFrontFace(int mode) throws GLException;
	
	int glGenBuffers() throws GLException;
	
	void glGenBuffers(IntBuffer buffers) throws GLException;
	
	int glGenTextures() throws GLException;
	
	void glGenTextures(int n, int[] textures, int offset) throws GLException;
	
	void glGenTextures(IntBuffer textures) throws GLException;
	
	GLEnumError glGetError();
	
	int glGetInteger(int pname) throws GLException;
	
	void glGetIntegerv(int pname, int[] params, int offset) throws GLException;
	
	void glGetIntegerv(int pname, IntBuffer params) throws GLException;
	
	String glGetString(int name) throws GLException;
	
	void glHint(int target, int mode) throws GLException;
	
	boolean glIsBuffer(int buffer);
	
	boolean glIsTexture(int texture);
	
	void glLogicOp(int op) throws GLException;
	
	void glPixelStoref(int pname, float param) throws GLException;
	
	void glPixelStorei(int pname, int param) throws GLException;
	
	void glPointSize(float size) throws GLException;
	
	void glReadPixels(int x, int y, int width, int height, int format, int type, ByteBuffer pixels) throws GLException;
	
	void glScissor(int x, int y, int width, int height) throws GLException;
	
	void glStencilFunc(int func, int ref, int mask) throws GLException;
	
	void glStencilMask(int mask) throws GLException;
	
	void glStencilOp(int fail, int zfail, int zpass) throws GLException;
	
	default void glTexImage2D(GLEnumTexture target, int level, int internalFormat, int width, int height, int border, int format, int type, ByteBuffer pixels) throws GLException
	{
		this.glTexImage2D(target.gl, level, internalFormat, width, height, border, format, type, pixels);
		
	}
	
	void glTexImage2D(int target, int level, int internalFormat, int width, int height, int border, int format, int type, ByteBuffer pixels) throws GLException;
	
	default void glTexImage3D(GLEnumTexture target, int level, int internalFormat, int width, int height, int depth, int border, int format, int type, ByteBuffer pixels) throws GLException
	{
		this.glTexImage3D(target.gl, level, internalFormat, width, height, depth, border, format, type, pixels);
		
	}
	
	void glTexImage3D(int target, int level, int internalFormat, int width, int height, int depth, int border, int format, int type, ByteBuffer pixels) throws GLException;
	
	default void glTexParameterf(GLEnumTexture target, int pname, float param) throws GLException
	{
		this.glTexParameterf(target.gl, pname, param);
		
	}
	
	void glTexParameterf(int target, int pname, float param) throws GLException;
	
	default void glTexParameterx(GLEnumTexture target, int pname, int param) throws GLException
	{
		this.glTexParameterx(target.gl, pname, param);
		
	}
	
	void glTexParameterx(int target, int pname, int param) throws GLException;
	
	default void glTexSubImage2D(GLEnumTexture target, int level, int xoffset, int yoffset, int width, int height, int format, int type, ByteBuffer pixels) throws GLException
	{
		this.glTexSubImage2D(target.gl, level, xoffset, yoffset, width, height, format, type, pixels);
		
	}
	
	void glTexSubImage2D(int target, int level, int xoffset, int yoffset, int width, int height, int format, int type, ByteBuffer pixels) throws GLException;
	
	void glViewport(int x, int y, int width, int height) throws GLException;
	
}
