
package com.elusivehawk.caelum.render.gl;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import com.elusivehawk.caelum.render.RenderHelper;
import com.elusivehawk.caelum.render.tex.Color;
import com.elusivehawk.caelum.render.tex.ColorFilter;
import com.elusivehawk.caelum.render.tex.ITexture;
import com.elusivehawk.util.storage.BufferHelper;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public final class GL1
{
	private static IGL1Impl impl = null;
	
	private GL1(){}
	
	public static void setImpl(IGL1Impl gl)
	{
		assert impl == null;
		assert gl != null;
		
		impl = gl;
		
	}
	
	public static void glActiveTexture(int texture) throws GLException
	{
		impl.glActiveTexture(texture);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glBindBuffer(VertexBuffer vbo)
	{
		glBindBuffer(vbo.getTarget(), vbo.getId());
		
	}
	
	public static void glBindBuffer(GLEnumBufferTarget target, int buffer) throws GLException
	{
		glBindBuffer(target.getGLId(), buffer);
		
	}
	
	public static void glBindBuffer(int target, int buffer) throws GLException
	{
		impl.glBindBuffer(target, buffer);
		
	}
	
	public static void glBindTexture(GLEnumTexture target, ITexture texture)
	{
		glBindTexture(target.gl, texture.getTexId());
		
	}
	
	public static void glBindTexture(GLEnumTexture target, int texture) throws GLException
	{
		glBindTexture(target.gl, texture);
		
	}
	
	public static void glBindTexture(int target, ITexture texture)
	{
		glBindTexture(target, texture.getTexId());
		
	}
	
	public static void glBindTexture(int target, int texture) throws GLException
	{
		impl.glBindTexture(target, texture);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glBlendFunc(int sfactor, int dfactor) throws GLException
	{
		impl.glBlendFunc(sfactor, dfactor);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glBufferData(GLEnumBufferTarget target, GLEnumDataType type, Buffer data, GLEnumDataUsage usage) throws GLException
	{
		glBufferData(target.getGLId(), type.getGLId(), data, usage.getGLId());
		
	}
	
	public static void glBufferData(int target, int type, Buffer data, int usage) throws GLException
	{
		impl.glBufferData(target, type, data, usage);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glBufferSubData(VertexBuffer buffer, int offset, Buffer data) throws GLException
	{
		glBufferSubData(buffer.getTarget(), offset, buffer.getDataType(), data);
	}
	
	public static void glBufferSubData(GLEnumBufferTarget target, int offset, GLEnumDataType type, Buffer data) throws GLException
	{
		glBufferSubData(target.getGLId() * type.getByteCount(), offset, type.getGLId(), data);
		
	}
	
	public static void glBufferSubData(int target, int offset, int type, Buffer data) throws GLException
	{
		impl.glBufferSubData(target, offset, type, data);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glClear(int mask) throws GLException
	{
		impl.glClear(mask);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glClearColor(Color col)
	{
		if (col == null)
		{
			col = Color.BLACK;
			
		}
		
		glClearColor(col.getColorf(ColorFilter.RED), col.getColorf(ColorFilter.GREEN), col.getColorf(ColorFilter.BLUE), col.getColorf(ColorFilter.ALPHA));
		
	}
	
	public static void glClearColor(float r, float g, float b, float a)
	{
		impl.glClearColor(r, g, b, a);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glCopyTexImage1D(int target, int level, int internalFormat, int x, int y, int width, int border) throws GLException
	{
		impl.glCopyTexImage1D(target, level, internalFormat, x, y, width, border);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glCopyTexImage2D(int target, int level, int internalFormat, int x, int y, int width, int height, int border) throws GLException
	{
		impl.glCopyTexImage2D(target, level, internalFormat, x, y, width, height, border);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glCopyTexSubImage1D(int target, int level, int xoffset, int x, int y, int width) throws GLException
	{
		impl.glCopyTexSubImage1D(target, level, xoffset, x, y, width);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glCopyTexSubImage2D(int target, int level, int xoffset, int yoffset, int x, int y, int width, int height) throws GLException
	{
		impl.glCopyTexSubImage2D(target, level, xoffset, yoffset, x, y, width, height);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glCopyTexSubImage3D(int target, int level, int xoffset, int yoffset, int zoffset, int x, int y, int width, int height) throws GLException
	{
		impl.glCopyTexSubImage3D(target, level, xoffset, yoffset, zoffset, x, y, width, height);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glCullFace(int mode) throws GLException
	{
		impl.glCullFace(mode);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glDeleteBuffers(VertexBuffer... buffers)
	{
		IntBuffer bufs = BufferHelper.createIntBuffer(buffers.length);
		
		for (VertexBuffer vb : buffers)
		{
			bufs.put(vb.getId());
			
		}
		
		glDeleteBuffers(bufs);
		
	}
	
	public static void glDeleteBuffers(int... buffer) throws GLException
	{
		glDeleteBuffers(BufferHelper.createWrapper(buffer));
		
	}
	
	public static void glDeleteBuffers(IntBuffer buffers) throws GLException
	{
		impl.glDeleteBuffers(buffers);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glDeleteTextures(int... textures) throws GLException
	{
		glDeleteBuffers(BufferHelper.createWrapper(textures));
		
	}
	
	public static void glDeleteTextures(IntBuffer textures) throws GLException
	{
		impl.glDeleteTextures(textures);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glDepthFunc(int func) throws GLException
	{
		impl.glDepthFunc(func);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glDepthMask(boolean flag) throws GLException
	{
		impl.glDepthMask(flag);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glDepthRange(float zNear, float zFar) throws GLException
	{
		impl.glDepthRange(zNear, zFar);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glDisable(int cap) throws GLException
	{
		impl.glDisable(cap);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glDrawArrays(int mode, int first, int count) throws GLException
	{
		impl.glDrawArrays(mode, first, count);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glDrawElements(GLEnumDrawType mode, int count, int type, IntBuffer indices) throws GLException
	{
		glDrawElements(mode.getGLId(), count, type, indices);
		
	}
	
	public static void glDrawElements(int mode, int count, int type, IntBuffer indices) throws GLException
	{
		impl.glDrawElements(mode, count, type, indices);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glDrawElements(GLEnumDrawType mode, int count, int type, int offset) throws GLException
	{
		glDrawElements(mode.getGLId(), count, type, offset);
		
	}
	
	public static void glDrawElements(int mode, int count, int type, int offset) throws GLException
	{
		impl.glDrawElements(mode, count, type, offset);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glEnable(int cap) throws GLException
	{
		impl.glEnable(cap);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glFinish()
	{
		impl.glFinish();
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glFlush()
	{
		impl.glFlush();
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glFrontFace(int mode) throws GLException
	{
		impl.glFrontFace(mode);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static int glGenBuffers() throws GLException
	{
		int ret = impl.glGenBuffers();
		
		RenderHelper.checkForGLError();
		
		return ret;
	}
	
	public static IntBuffer glGenBuffers(int count) throws GLException
	{
		IntBuffer ret = BufferHelper.createIntBuffer(count);
		
		impl.glGenBuffers(ret);
		
		RenderHelper.checkForGLError();
		
		return ret;
	}
	
	public static int glGenTextures() throws GLException
	{
		int ret = impl.glGenTextures();
		
		RenderHelper.checkForGLError();
		
		return ret;
	}
	
	public static IntBuffer glGenTextures(int count) throws GLException
	{
		IntBuffer ret = BufferHelper.createIntBuffer(count);
		
		impl.glGenTextures(ret);
		
		RenderHelper.checkForGLError();
		
		return ret;
	}
	
	public static GLEnumError glGetError()
	{
		return GLEnumError.get(impl.glGetError());
	}
	
	public static int glGetInteger(int pname) throws GLException
	{
		int ret = impl.glGetInteger(pname);
		
		RenderHelper.checkForGLError();
		
		return ret;
	}
	
	public static void glGetIntegerv(int pname, int[] params, int offset) throws GLException
	{
		impl.glGetIntegerv(pname, params, offset);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glGetIntegerv(int pname, IntBuffer params) throws GLException
	{
		impl.glGetIntegerv(pname, params);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static String glGetString(int name) throws GLException
	{
		String ret = impl.glGetString(name);
		
		RenderHelper.checkForGLError();
		
		return ret;
	}
	
	public static void glHint(int target, int mode) throws GLException
	{
		impl.glHint(target, mode);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static boolean glIsBuffer(int buffer)
	{
		return impl.glIsBuffer(buffer);
	}
	
	public static boolean glIsTexture(int texture)
	{
		return impl.glIsTexture(texture);
	}
	
	public static void glLogicOp(int op) throws GLException
	{
		impl.glLogicOp(op);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glPixelStoref(int pname, float param) throws GLException
	{
		impl.glPixelStoref(pname, param);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glPixelStorei(int pname, int param) throws GLException
	{
		impl.glPixelStorei(pname, param);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glPointSize(float size) throws GLException
	{
		impl.glPointSize(size);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glReadPixels(int x, int y, int width, int height, int format, int type, ByteBuffer pixels) throws GLException
	{
		impl.glReadPixels(x, y, width, height, format, type, pixels);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glScissor(int x, int y, int width, int height) throws GLException
	{
		impl.glScissor(x, y, width, height);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glStencilFunc(int func, int ref, int mask) throws GLException
	{
		impl.glStencilFunc(func, ref, mask);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glStencilMask(int mask) throws GLException
	{
		impl.glStencilMask(mask);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glStencilOp(int fail, int zfail, int zpass) throws GLException
	{
		impl.glStencilOp(fail, zfail, zpass);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glTexImage2D(GLEnumTexture target, int level, int internalFormat, int width, int height, int border, int format, int type, ByteBuffer pixels) throws GLException
	{
		glTexImage2D(target.gl, level, internalFormat, width, height, border, format, type, pixels);
		
	}
	
	public static void glTexImage2D(int target, int level, int internalFormat, int width, int height, int border, int format, int type, ByteBuffer pixels) throws GLException
	{
		impl.glTexImage2D(target, level, internalFormat, width, height, border, format, type, pixels);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glTexImage3D(GLEnumTexture target, int level, int internalFormat, int width, int height, int depth, int border, int format, int type, ByteBuffer pixels) throws GLException
	{
		glTexImage3D(target.gl, level, internalFormat, width, height, depth, border, format, type, pixels);
		
	}
	
	public static void glTexImage3D(int target, int level, int internalFormat, int width, int height, int depth, int border, int format, int type, ByteBuffer pixels) throws GLException
	{
		impl.glTexImage3D(target, level, internalFormat, width, height, depth, border, format, type, pixels);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glTexParameterf(GLEnumTexture target, int pname, float param) throws GLException
	{
		glTexParameterf(target.gl, pname, param);
		
	}
	
	public static void glTexParameterf(int target, int pname, float param) throws GLException
	{
		impl.glTexParameterf(target, pname, param);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glTexParameterx(GLEnumTexture target, int pname, int param) throws GLException
	{
		glTexParameterx(target.gl, pname, param);
		
	}
	
	public static void glTexParameterx(int target, int pname, int param) throws GLException
	{
		impl.glTexParameterx(target, pname, param);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glTexSubImage2D(GLEnumTexture target, int level, int xoffset, int yoffset, int width, int height, int format, int type, ByteBuffer pixels) throws GLException
	{
		glTexSubImage2D(target.gl, level, xoffset, yoffset, width, height, format, type, pixels);
		
	}
	
	public static void glTexSubImage2D(int target, int level, int xoffset, int yoffset, int width, int height, int format, int type, ByteBuffer pixels) throws GLException
	{
		impl.glTexSubImage2D(target, level, xoffset, yoffset, width, height, format, type, pixels);
		
		RenderHelper.checkForGLError();
		
	}
	
	public static void glViewport(int x, int y, int width, int height) throws GLException
	{
		impl.glViewport(x, y, width, height);
		
		RenderHelper.checkForGLError();
		
	}
	
}
