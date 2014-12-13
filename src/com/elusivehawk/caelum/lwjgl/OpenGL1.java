
package com.elusivehawk.caelum.lwjgl;

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
import com.elusivehawk.caelum.render.gl.GLConst;
import com.elusivehawk.caelum.render.gl.IGL1Impl;
import com.elusivehawk.util.storage.BufferHelper;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class OpenGL1 implements IGL1Impl
{
	@Override
	public void glActiveTexture(int texture)
	{
		GL13.glActiveTexture(texture);
		
	}
	
	@Override
	public void glBindBuffer(int target, int buffer)
	{
		GL15.glBindBuffer(target, buffer);
		
	}
	
	@Override
	public void glBindTexture(int target, int texture)
	{
		GL11.glBindTexture(target, texture);
		
	}
	
	@Override
	public void glBlendFunc(int sfactor, int dfactor)
	{
		GL11.glBlendFunc(sfactor, dfactor);
		
	}
	
	@Override
	public void glBufferData(int target, int type, Buffer data, int usage)
	{
		switch (type)
		{
			case GLConst.GL_BYTE: GL15.glBufferData(target, (ByteBuffer)data, usage); break;
			case GLConst.GL_SHORT: GL15.glBufferData(target, (ShortBuffer)data, usage); break;
			case GLConst.GL_INT: GL15.glBufferData(target, (IntBuffer)data, usage); break;
			case GLConst.GL_FLOAT: GL15.glBufferData(target, (FloatBuffer)data, usage); break;
			case GLConst.GL_DOUBLE: GL15.glBufferData(target, (DoubleBuffer)data, usage); break;
			
		}
		
	}
	
	@Override
	public void glBufferSubData(int target, int offset, int type, Buffer data)
	{
		switch (type)
		{
			case GLConst.GL_BYTE: GL15.glBufferSubData(target, offset, (ByteBuffer)data); break;
			case GLConst.GL_SHORT: GL15.glBufferSubData(target, offset, (ShortBuffer)data); break;
			case GLConst.GL_INT: GL15.glBufferSubData(target, offset, (IntBuffer)data); break;
			case GLConst.GL_FLOAT: GL15.glBufferSubData(target, offset, (FloatBuffer)data); break;
			case GLConst.GL_DOUBLE: GL15.glBufferSubData(target, offset, (DoubleBuffer)data); break;
			
		}
		
	}
	
	@Override
	public void glClear(int mask)
	{
		GL11.glClear(mask);
		
	}
	
	@Override
	public void glClearColor(float r, float g, float b, float a)
	{
		GL11.glClearColor(r, g, b, a);
		
	}
	
	@Override
	public void glCopyTexImage1D(int target, int level, int internalFormat,
			int x, int y, int width, int border)
	{
		GL11.glCopyTexImage1D(target, level, internalFormat, x, y, width, border);
		
	}
	
	@Override
	public void glCopyTexImage2D(int target, int level, int internalFormat,
			int x, int y, int width, int height, int border)
	{
		GL11.glCopyTexImage2D(target, level, internalFormat, x, y, width, height, border);
		
	}
	
	@Override
	public void glCopyTexSubImage1D(int target, int level, int xoffset, int x,
			int y, int width)
	{
		GL11.glCopyTexSubImage1D(target, level, xoffset, x, y, width);
		
	}
	
	@Override
	public void glCopyTexSubImage2D(int target, int level, int xoffset,
			int yoffset, int x, int y, int width, int height)
	{
		GL11.glCopyTexSubImage2D(target, level, xoffset, yoffset, x, y, width, height);
		
	}
	
	@Override
	public void glCopyTexSubImage3D(int target, int level, int xoffset,
			int yoffset, int zoffset, int x, int y, int width, int height)
	{
		GL12.glCopyTexSubImage3D(target, level, xoffset, yoffset, zoffset, x, y, width, height);
		
	}
	
	@Override
	public void glCullFace(int mode)
	{
		GL11.glCullFace(mode);
		
	}
	
	@Override
	public void glDeleteBuffers(IntBuffer buffers)
	{
		GL15.glDeleteBuffers(buffers);
		
	}
	
	@Override
	public void glDeleteTextures(IntBuffer textures)
	{
		GL11.glDeleteTextures(textures);
		
	}
	
	@Override
	public void glDepthFunc(int func)
	{
		GL11.glDepthFunc(func);
		
	}
	
	@Override
	public void glDepthMask(boolean flag)
	{
		GL11.glDepthMask(flag);
		
	}
	
	@Override
	public void glDepthRange(float zNear, float zFar)
	{
		GL11.glDepthRange(zNear, zFar);
		
	}
	
	@Override
	public void glDisable(int cap)
	{
		GL11.glDisable(cap);
		
	}
	
	@Override
	public void glDrawArrays(int mode, int first, int count)
	{
		GL11.glDrawArrays(mode, first, count);
		
	}
	
	@Override
	public void glDrawElements(int mode, int count, int type, IntBuffer indices)
	{
		GL11.glDrawElements(mode, indices);//TODO Check usage
		
	}
	
	@Override
	public void glDrawElements(int mode, int count, int type, int offset)
	{
		GL11.glDrawElements(mode, count, type, offset);
		
	}
	
	@Override
	public void glEnable(int cap)
	{
		GL11.glEnable(cap);
		
	}
	
	@Override
	public void glFinish()
	{
		GL11.glFinish();
		
	}
	
	@Override
	public void glFlush()
	{
		GL11.glFlush();
		
	}
	
	@Override
	public void glFrontFace(int mode)
	{
		GL11.glFrontFace(mode);
		
	}
	
	@Override
	public int glGenBuffers()
	{
		return GL15.glGenBuffers();
	}
	
	@Override
	public void glGenBuffers(IntBuffer buffers)
	{
		GL15.glGenBuffers(buffers);
		
	}
	
	@Override
	public int glGenTextures()
	{
		return GL11.glGenTextures();
	}
	
	@Override
	public void glGenTextures(int n, int[] textures, int offset)
	{
		IntBuffer buf = BufferHelper.createIntBuffer(n);
		
		GL11.glGenTextures(buf);
		
		for (int c = 0; c < n; c++)
		{
			textures[c + offset] = buf.get();
			
		}
		
	}
	
	@Override
	public void glGenTextures(IntBuffer textures)
	{
		GL11.glGenTextures(textures);
		
	}
	
	@Override
	public int glGetError()
	{
		return GL11.glGetError();
	}
	
	@Override
	public int glGetInteger(int pname)
	{
		return GL11.glGetInteger(pname);
	}
	
	@Override
	public void glGetIntegerv(int pname, int[] params, int offset)
	{
		IntBuffer buf = BufferHelper.createIntBuffer(params.length - offset);
		
		GL11.glGetInteger(pname, buf);
		
		for (int c = 0; c < buf.capacity(); c++)
		{
			params[c + offset] = buf.get();
			
		}
		
	}
	
	@Override
	public void glGetIntegerv(int pname, IntBuffer params)
	{
		GL11.glGetInteger(pname, params);
		
	}
	
	@Override
	public String glGetString(int name)
	{
		return GL11.glGetString(name);
	}
	
	@Override
	public void glHint(int target, int mode)
	{
		GL11.glHint(target, mode);
		
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
	public void glLogicOp(int opcode)
	{
		GL11.glLogicOp(opcode);
		
	}
	
	@Override
	public void glPixelStoref(int pname, float param)
	{
		GL11.glPixelStoref(pname, Float.floatToRawIntBits(param));
		
	}
	
	@Override
	public void glPixelStorei(int pname, int param)
	{
		GL11.glPixelStorei(pname, param);
		
	}
	
	@Override
	public void glPointSize(float size)
	{
		GL11.glPointSize(size);
		
	}
	
	@Override
	public void glReadPixels(int x, int y, int width, int height, int format,
			int type, ByteBuffer pixels)
	{
		GL11.glReadPixels(x, y, width, height, format, type, pixels);
		
	}
	
	@Override
	public void glScissor(int x, int y, int width, int height)
	{
		GL11.glScissor(x, y, width, height);
		
	}
	
	@Override
	public void glStencilFunc(int func, int ref, int mask)
	{
		GL11.glStencilFunc(func, ref, mask);
		
	}
	
	@Override
	public void glStencilMask(int mask)
	{
		GL11.glStencilMask(mask);
		
	}
	
	@Override
	public void glStencilOp(int fail, int zfail, int zpass)
	{
		GL11.glStencilOp(fail, zfail, zpass);
		
	}
	
	@Override
	public void glTexImage2D(int target, int level, int internalformat,
			int width, int height, int border, int format, int type,
			ByteBuffer pixels)
	{
		GL11.glTexImage2D(target, level, internalformat, width, height, border, format, type, pixels);
		
	}
	
	@Override
	public void glTexImage3D(int target, int level, int internalFormat, int width, int height, int depth, int border, int format, int type, ByteBuffer pixels)
	{
		GL12.glTexImage3D(target, level, internalFormat, width, height, depth, border, format, type, pixels);
		
	}
	
	@Override
	public void glTexParameterf(int target, int pname, float param)
	{
		GL11.glTexParameterf(target, pname, param);
		
	}
	
	@Override
	public void glTexParameterx(int target, int pname, int param)
	{
		GL11.glTexParameteri(target, pname, param);
		
	}
	
	@Override
	public void glTexSubImage2D(int target, int level, int xoffset,
			int yoffset, int width, int height, int format, int type,
			ByteBuffer pixels)
	{
		GL11.glTexSubImage2D(target, level, xoffset, yoffset, width, height, format, type, pixels);
		
	}
	
	@Override
	public void glViewport(int x, int y, int width, int height)
	{
		GL11.glViewport(x, y, width, height);
		
	}
	
}
