
package com.elusivehawk.engine.lwjgl;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL15;
import com.elusivehawk.engine.render.RenderHelper;
import com.elusivehawk.engine.render.gl.GLConst;
import com.elusivehawk.engine.render.gl.GLEnumError;
import com.elusivehawk.engine.render.gl.GLEnumTexture;
import com.elusivehawk.engine.render.gl.GLException;
import com.elusivehawk.engine.render.gl.IGL1;
import com.elusivehawk.util.storage.BufferHelper;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class OpenGL1 implements IGL1
{
	@Override
	public void glActiveTexture(int texture) throws GLException
	{
		GL13.glActiveTexture(texture);
		
		RenderHelper.checkForGLError(this);
		
	}
	
	@Override
	public void glBindBuffer(int target, int buffer) throws GLException
	{
		GL15.glBindBuffer(target, buffer);
		
		RenderHelper.checkForGLError(this);
		
	}
	
	@Override
	public void glBindTexture(GLEnumTexture target, int texture) throws GLException
	{
		GL11.glBindTexture(target.gl, texture);
		
		RenderHelper.checkForGLError(this);
		
	}
	
	@Override
	public void glBlendFunc(int sfactor, int dfactor) throws GLException
	{
		GL11.glBlendFunc(sfactor, dfactor);
		
		RenderHelper.checkForGLError(this);
		
	}
	
	@Override
	public void glBufferData(int target, int type, Buffer data, int usage) throws GLException
	{
		switch (type)
		{
			case GLConst.GL_BYTE: GL15.glBufferData(target, (ByteBuffer)data, usage); break;
			case GLConst.GL_SHORT: GL15.glBufferData(target, (ShortBuffer)data, usage); break;
			case GLConst.GL_INT: GL15.glBufferData(target, (IntBuffer)data, usage); break;
			case GLConst.GL_FLOAT: GL15.glBufferData(target, (FloatBuffer)data, usage); break;
			case GLConst.GL_DOUBLE: GL15.glBufferData(target, (DoubleBuffer)data, usage); break;
			
		}
		
		RenderHelper.checkForGLError(this);
		
	}
	
	@Override
	public void glBufferSubData(int target, int offset, int type, Buffer data) throws GLException
	{
		switch (type)
		{
			case GLConst.GL_BYTE: GL15.glBufferSubData(target, offset, (ByteBuffer)data); break;
			case GLConst.GL_SHORT: GL15.glBufferSubData(target, offset, (ShortBuffer)data); break;
			case GLConst.GL_INT: GL15.glBufferSubData(target, offset, (IntBuffer)data); break;
			case GLConst.GL_FLOAT: GL15.glBufferSubData(target, offset, (FloatBuffer)data); break;
			case GLConst.GL_DOUBLE: GL15.glBufferSubData(target, offset, (DoubleBuffer)data); break;
			
		}
		
		RenderHelper.checkForGLError(this);
		
	}
	
	@Override
	public void glClear(int mask) throws GLException
	{
		GL11.glClear(mask);
		
		RenderHelper.checkForGLError(this);
		
	}
	
	@Override
	public void glClearColor(float r, float g, float b, float a)
	{
		GL11.glClearColor(r, g, b, a);
		
		RenderHelper.checkForGLError(this);
		
	}
	
	@Override
	public void glCopyTexImage1D(int target, int level, int internalFormat,
			int x, int y, int width, int border) throws GLException
	{
		GL11.glCopyTexImage1D(target, level, internalFormat, x, y, width, border);
		
		RenderHelper.checkForGLError(this);
		
	}
	
	@Override
	public void glCopyTexImage2D(int target, int level, int internalFormat,
			int x, int y, int width, int height, int border) throws GLException
	{
		GL11.glCopyTexImage2D(target, level, internalFormat, x, y, width, height, border);
		
		RenderHelper.checkForGLError(this);
		
	}
	
	@Override
	public void glCopyTexSubImage1D(int target, int level, int xoffset, int x,
			int y, int width) throws GLException
	{
		GL11.glCopyTexSubImage1D(target, level, xoffset, x, y, width);
		
		RenderHelper.checkForGLError(this);
		
	}
	
	@Override
	public void glCopyTexSubImage2D(int target, int level, int xoffset,
			int yoffset, int x, int y, int width, int height) throws GLException
	{
		GL11.glCopyTexSubImage2D(target, level, xoffset, yoffset, x, y, width, height);
		
		RenderHelper.checkForGLError(this);
		
	}
	
	@Override
	public void glCopyTexSubImage3D(int target, int level, int xoffset,
			int yoffset, int zoffset, int x, int y, int width, int height) throws GLException
	{
		GL12.glCopyTexSubImage3D(target, level, xoffset, yoffset, zoffset, x, y, width, height);
		
		RenderHelper.checkForGLError(this);
		
	}
	
	@Override
	public void glCullFace(int mode) throws GLException
	{
		GL11.glCullFace(mode);
		
		RenderHelper.checkForGLError(this);
		
	}
	
	@Override
	public void glDeleteBuffers(int... buffers) throws GLException
	{
		this.glDeleteBuffers(BufferHelper.makeIntBuffer(buffers));
		
		RenderHelper.checkForGLError(this);
		
	}
	
	@Override
	public void glDeleteBuffers(IntBuffer buffers) throws GLException
	{
		GL15.glDeleteBuffers(buffers);
		
		RenderHelper.checkForGLError(this);
		
	}
	
	@Override
	public void glDeleteTextures(int... textures) throws GLException
	{
		this.glDeleteTextures(BufferHelper.createWrapper(textures));
		
		RenderHelper.checkForGLError(this);
		
	}
	
	@Override
	public void glDeleteTextures(IntBuffer textures) throws GLException
	{
		GL11.glDeleteTextures(textures);
		
		RenderHelper.checkForGLError(this);
		
	}
	
	@Override
	public void glDepthFunc(int func) throws GLException
	{
		GL11.glDepthFunc(func);
		
		RenderHelper.checkForGLError(this);
		
	}
	
	@Override
	public void glDepthMask(boolean flag) throws GLException
	{
		GL11.glDepthMask(flag);
		
		RenderHelper.checkForGLError(this);
		
	}
	
	@Override
	public void glDepthRange(float zNear, float zFar) throws GLException
	{
		GL11.glDepthRange(zNear, zFar);
		
		RenderHelper.checkForGLError(this);
		
	}
	
	@Override
	public void glDisable(int cap) throws GLException
	{
		GL11.glDisable(cap);
		
		RenderHelper.checkForGLError(this);
		
	}
	
	@Override
	public void glDrawArrays(int mode, int first, int count) throws GLException
	{
		GL11.glDrawArrays(mode, first, count);
		
		RenderHelper.checkForGLError(this);
		
	}
	
	@Override
	public void glDrawElements(int mode, int count, int type, IntBuffer indices) throws GLException
	{
		GL11.glDrawElements(mode, indices);//TODO Check usage
		
		RenderHelper.checkForGLError(this);
		
	}
	
	@Override
	public void glDrawElements(int mode, int count, int type, int offset) throws GLException
	{
		GL11.glDrawElements(mode, count, type, offset);
		
		RenderHelper.checkForGLError(this);
		
	}
	
	@Override
	public void glEnable(int cap) throws GLException
	{
		GL11.glEnable(cap);
		
		RenderHelper.checkForGLError(this);
		
	}
	
	@Override
	public void glFinish()
	{
		GL11.glFinish();
		
		RenderHelper.checkForGLError(this);
		
	}
	
	@Override
	public void glFlush()
	{
		GL11.glFlush();
		
		RenderHelper.checkForGLError(this);
		
	}
	
	@Override
	public void glFrontFace(int mode) throws GLException
	{
		GL11.glFrontFace(mode);
		
		RenderHelper.checkForGLError(this);
		
	}
	
	@Override
	public int glGenBuffers() throws GLException
	{
		int ret = GL15.glGenBuffers();
		
		RenderHelper.checkForGLError(this);
		
		return ret;
	}
	
	@Override
	public void glGenBuffers(IntBuffer buffers) throws GLException
	{
		GL15.glGenBuffers(buffers);
		
		RenderHelper.checkForGLError(this);
		
	}
	
	@Override
	public int glGenTextures() throws GLException
	{
		int ret = GL11.glGenTextures();
		
		RenderHelper.checkForGLError(this);
		
		return ret;
	}
	
	@Override
	public void glGenTextures(int n, int[] textures, int offset) throws GLException
	{
		IntBuffer buf = BufferHelper.createIntBuffer(n);
		
		GL11.glGenTextures(buf);
		
		for (int c = 0; c < n; c++)
		{
			textures[c + offset] = buf.get();
			
		}
		
		RenderHelper.checkForGLError(this);
		
	}
	
	@Override
	public void glGenTextures(IntBuffer textures) throws GLException
	{
		GL11.glGenTextures(textures);
		
		RenderHelper.checkForGLError(this);
		
	}
	
	@Override
	public GLEnumError glGetError()
	{
		return GLEnumError.get(GL11.glGetError());
	}
	
	@Override
	public int glGetInteger(int pname) throws GLException
	{
		int ret = GL11.glGetInteger(pname);
		
		RenderHelper.checkForGLError(this);
		
		return ret;
	}
	
	@Override
	public void glGetIntegerv(int pname, int[] params, int offset) throws GLException
	{
		IntBuffer buf = BufferHelper.createIntBuffer(params.length - offset);
		
		GL11.glGetInteger(pname, buf);
		
		RenderHelper.checkForGLError(this);
		
		for (int c = 0; c < buf.capacity(); c++)
		{
			params[c + offset] = buf.get();
			
		}
		
	}
	
	@Override
	public void glGetIntegerv(int pname, IntBuffer params) throws GLException
	{
		GL11.glGetInteger(pname, params);
		
		RenderHelper.checkForGLError(this);
		
	}
	
	@Override
	public String glGetString(int name) throws GLException
	{
		String ret = GL11.glGetString(name);
		
		RenderHelper.checkForGLError(this);
		
		return ret;
	}
	
	@Override
	public void glHint(int target, int mode) throws GLException
	{
		GL11.glHint(target, mode);
		
		RenderHelper.checkForGLError(this);
		
	}
	
	@Override
	public boolean glIsBuffer(int buffer)
	{
		return GL15.glIsBuffer(buffer);
	}
	
	@Override
	public boolean glIsTexture(int texture)
	{
		return GL11.glIsTexture(texture);
	}
	
	@Override
	public void glLogicOp(int opcode) throws GLException
	{
		GL11.glLogicOp(opcode);
		
		RenderHelper.checkForGLError(this);
		
	}
	
	@Override
	public void glPixelStoref(int pname, float param) throws GLException
	{
		GL11.glPixelStoref(pname, param);
		
		RenderHelper.checkForGLError(this);
		
	}
	
	@Override
	public void glPixelStorei(int pname, int param) throws GLException
	{
		GL11.glPixelStorei(pname, param);
		
		RenderHelper.checkForGLError(this);
		
	}
	
	@Override
	public void glPointSize(float size) throws GLException
	{
		GL11.glPointSize(size);
		
		RenderHelper.checkForGLError(this);
		
	}
	
	@Override
	public void glReadPixels(int x, int y, int width, int height, int format,
			int type, ByteBuffer pixels) throws GLException
	{
		GL11.glReadPixels(x, y, width, height, format, type, pixels);
		
		RenderHelper.checkForGLError(this);
		
	}
	
	@Override
	public void glScissor(int x, int y, int width, int height) throws GLException
	{
		GL11.glScissor(x, y, width, height);
		
		RenderHelper.checkForGLError(this);
		
	}
	
	@Override
	public void glStencilFunc(int func, int ref, int mask) throws GLException
	{
		GL11.glStencilFunc(func, ref, mask);
		
		RenderHelper.checkForGLError(this);
		
	}
	
	@Override
	public void glStencilMask(int mask) throws GLException
	{
		GL11.glStencilMask(mask);
		
		RenderHelper.checkForGLError(this);
		
	}
	
	@Override
	public void glStencilOp(int fail, int zfail, int zpass) throws GLException
	{
		GL11.glStencilOp(fail, zfail, zpass);
		
		RenderHelper.checkForGLError(this);
		
	}
	
	@Override
	public void glTexImage2D(int target, int level, int internalformat,
			int width, int height, int border, int format, int type,
			ByteBuffer pixels) throws GLException
	{
		GL11.glTexImage2D(target, level, internalformat, width, height, border, format, type, pixels);
		
		RenderHelper.checkForGLError(this);
		
	}
	
	@Override
	public void glTexImage3D(int target, int level, int internalFormat, int width, int height, int depth, int border, int format, int type, ByteBuffer pixels) throws GLException
	{
		GL12.glTexImage3D(target, level, internalFormat, width, height, depth, border, format, type, pixels);
		
		RenderHelper.checkForGLError(this);
		
	}
	
	@Override
	public void glTexParameterf(int target, int pname, float param) throws GLException
	{
		GL11.glTexParameterf(target, pname, param);
		
		RenderHelper.checkForGLError(this);
		
	}
	
	@Override
	public void glTexParameterx(int target, int pname, int param) throws GLException
	{
		GL11.glTexParameteri(target, pname, param);
		
		RenderHelper.checkForGLError(this);
		
	}
	
	@Override
	public void glTexSubImage2D(int target, int level, int xoffset,
			int yoffset, int width, int height, int format, int type,
			ByteBuffer pixels) throws GLException
	{
		GL11.glTexSubImage2D(target, level, xoffset, yoffset, width, height, format, type, pixels);
		
		RenderHelper.checkForGLError(this);
		
	}
	
	@Override
	public void glViewport(int x, int y, int width, int height) throws GLException
	{
		GL11.glViewport(x, y, width, height);
		
		RenderHelper.checkForGLError(this);
		
	}
	
}
