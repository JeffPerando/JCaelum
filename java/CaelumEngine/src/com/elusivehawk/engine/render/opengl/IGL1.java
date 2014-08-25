
package com.elusivehawk.engine.render.opengl;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import com.elusivehawk.engine.render.Color;
import com.elusivehawk.engine.render.ColorFilter;
import com.elusivehawk.engine.render.Texture;
import com.elusivehawk.util.BufferHelper;

/**
 * 
 * Supports OpenGL versions 1.0/1 to 1.5.
 * 
 * @author Elusivehawk
 */
public interface IGL1
{
	public void glActiveTexture(int texture) throws GLException;
	
	default void glBindBuffer(VertexBuffer vbo)
	{
		this.glBindBuffer(vbo.getTarget(), vbo.getId());
		
	}
	
	default void glBindBuffer(GLEnumBufferTarget target, int buffer) throws GLException
	{
		this.glBindBuffer(target.getGLId(), buffer);
		
	}
	
	public void glBindBuffer(int target, int buffer) throws GLException;
	
	default void glBindTexture(GLEnumTexture target, Texture texture)
	{
		this.glBindTexture(target, texture.getTexId(0));
		
	}
	
	public void glBindTexture(GLEnumTexture target, int texture) throws GLException;
	
	public void glBlendFunc(int sfactor, int dfactor) throws GLException;
	
	default void glBufferData(GLEnumBufferTarget target, GLEnumDataType type, java.nio.Buffer data, GLEnumDataUsage usage) throws GLException
	{
		this.glBufferData(target.getGLId(), type.getGLId(), data, usage.getGLId());
		
	}
	
	public void glBufferData(int target, int type, java.nio.Buffer data, int usage) throws GLException;
	
	default void glBufferSubData(GLEnumBufferTarget target, int offset, GLEnumDataType type, java.nio.Buffer data) throws GLException
	{
		this.glBufferSubData(target.getGLId(), offset, type.getGLId(), data);
		
	}
	
	public void glBufferSubData(int target, int offset, int type, java.nio.Buffer data) throws GLException;
	
	public void glClear(int mask) throws GLException;
	
	default void glClearColor(Color col)
	{
		this.glClearColor(col.getColorf(ColorFilter.RED), col.getColorf(ColorFilter.GREEN), col.getColorf(ColorFilter.BLUE), col.getColorf(ColorFilter.ALPHA));
		
	}
	
	public void glClearColor(float r, float g, float b, float a);
	
	public void glCopyTexImage1D(int target, int level, int internalFormat, int x, int y, int width, int border) throws GLException;
	
	public void glCopyTexImage2D(int target, int level, int internalFormat, int x, int y, int width, int height, int border) throws GLException;
	
	public void glCopyTexSubImage1D(int target, int level, int xoffset, int x, int y, int width) throws GLException;
	
	public void glCopyTexSubImage2D(int target, int level, int xoffset, int yoffset, int x, int y, int width, int height) throws GLException;
	
	public void glCopyTexSubImage3D(int target, int level, int xoffset, int yoffset, int zoffset, int x, int y, int width, int height) throws GLException;
	
	public void glCullFace(int mode) throws GLException;
	
	default void glDeleteBuffers(VertexBuffer... buffers)
	{
		IntBuffer bufs = BufferHelper.createIntBuffer(buffers.length);
		
		for (VertexBuffer vb : buffers)
		{
			bufs.put(vb.getId());
			
		}
		
		this.glDeleteBuffers(bufs);
		
	}
	
	public void glDeleteBuffers(int... buffer) throws GLException;
	
	public void glDeleteBuffers(IntBuffer buffers) throws GLException;
	
	default void glDeleteTextures(Texture... textures)
	{
		int texCount = 0;
		
		for (Texture tex : textures)
		{
			if (tex == null)
			{
				continue;
			}
			
			texCount += tex.getFrameCount();
			
		}
		
		int[] texs = new int[texCount];
		int curr = 0;
		
		for (Texture tex : textures)
		{
			for (int c = 0; c < tex.getFrameCount(); c++)
			{
				texs[curr++] = tex.getTexId(c);
				
			}
			
		}
		
		this.glDeleteBuffers(texs);
		
	}
	
	public void glDeleteTextures(int... textures) throws GLException;
	
	public void glDeleteTextures(IntBuffer textures) throws GLException;
	
	public void glDepthFunc(int func) throws GLException;
	
	public void glDepthMask(boolean flag) throws GLException;
	
	public void glDepthRange(float zNear, float zFar) throws GLException;
	
	public void glDisable(int cap) throws GLException;
	
	public void glDrawArrays(int mode, int first, int count) throws GLException;
	
	public void glDrawElements(int mode, int count, int type, IntBuffer indices) throws GLException;
	
	public void glDrawElements(int mode, int count, int type, int offset) throws GLException;
	
	public void glEnable(int cap) throws GLException;
	
	public void glFinish();
	
	public void glFlush();
	
	public void glFrontFace(int mode) throws GLException;
	
	public int glGenBuffers() throws GLException;
	
	public void glGenBuffers(IntBuffer buffers) throws GLException;
	
	public int glGenTextures() throws GLException;
	
	public void glGenTextures(int n, int[] textures, int offset) throws GLException;
	
	public void glGenTextures(IntBuffer textures) throws GLException;
	
	public GLEnumError glGetError();
	
	public int glGetInteger(int pname) throws GLException;
	
	public void glGetIntegerv(int pname, int[] params, int offset) throws GLException;
	
	public void glGetIntegerv(int pname, IntBuffer params) throws GLException;
	
	public String glGetString(int name) throws GLException;
	
	public void glHint(int target, int mode) throws GLException;
	
	public boolean glIsBuffer(int buffer);
	
	public boolean glIsTexture(int texture);
	
	public void glLogicOp(int op) throws GLException;
	
	public void glPixelStoref(int pname, float param) throws GLException;
	
	public void glPixelStorei(int pname, int param) throws GLException;
	
	public void glPointSize(float size) throws GLException;
	
	public void glReadPixels(int x, int y, int width, int height, int format, int type, ByteBuffer pixels) throws GLException;
	
	public void glScissor(int x, int y, int width, int height) throws GLException;
	
	public void glStencilFunc(int func, int ref, int mask) throws GLException;
	
	public void glStencilMask(int mask) throws GLException;
	
	public void glStencilOp(int fail, int zfail, int zpass) throws GLException;
	
	public void glTexImage2D(int target, int level, int internalformat, int width, int height, int border, int format, int type, ByteBuffer pixels) throws GLException;
	
	public void glTexImage3D(int target, int level, int internalFormat, int width, int height, int depth, int border, int format, int type, ByteBuffer pixels) throws GLException;
	
	public void glTexParameterf(int target, int pname, float param) throws GLException;
	
	public void glTexParameterx(int target, int pname, int param) throws GLException;
	
	public void glTexSubImage2D(int target, int level, int xoffset, int yoffset, int width, int height, int format, int type, ByteBuffer pixels) throws GLException;
	
	public void glViewport(int x, int y, int width, int height) throws GLException;
	
}
