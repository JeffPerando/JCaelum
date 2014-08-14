
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
	default void glActiveTexture(Texture texture)
	{
		this.glActiveTexture(texture.getTexId(0));
		
	}
	
	public void glActiveTexture(int texture);
	
	default void glBindBuffer(VertexBuffer vbo)
	{
		this.glBindBuffer(vbo.t, vbo.id);
		
	}
	
	public void glBindBuffer(int target, int buffer);
	
	default void glBindTexture(int target, Texture texture)
	{
		this.glBindTexture(target, texture.getTexId(0));
		
	}
	
	public void glBindTexture(int target, int texture);
	
	public void glBlendFunc(int sfactor, int dfactor);
	
	public void glBufferData(int target, int type, java.nio.Buffer data, int usage);
	
	public void glBufferSubData(int target, int offset, int type, java.nio.Buffer data);
	
	public void glClear(int mask);
	
	default void glClearColor(Color col)
	{
		this.glClearColor(col.getColorf(ColorFilter.RED), col.getColorf(ColorFilter.GREEN), col.getColorf(ColorFilter.BLUE), col.getColorf(ColorFilter.ALPHA));
		
	}
	
	public void glClearColor(float r, float g, float b, float a);
	
	public void glCopyTexImage1D(int target, int level, int internalFormat, int x, int y, int width, int border);
	
	public void glCopyTexImage2D(int target, int level, int internalFormat, int x, int y, int width, int height, int border);
	
	public void glCopyTexSubImage1D(int target, int level, int xoffset, int x, int y, int width);
	
	public void glCopyTexSubImage2D(int target, int level, int xoffset, int yoffset, int x, int y, int width, int height);
	
	public void glCopyTexSubImage3D(int target, int level, int xoffset, int yoffset, int zoffset, int x, int y, int width, int height);
	
	public void glCullFace(int mode);
	
	default void glDeleteBuffers(VertexBuffer... buffers)
	{
		IntBuffer bufs = BufferHelper.createIntBuffer(buffers.length);
		
		for (VertexBuffer vb : buffers)
		{
			bufs.put(vb.id);
			
		}
		
		this.glDeleteBuffers(bufs);
		
	}
	
	public void glDeleteBuffers(int... buffer);
	
	public void glDeleteBuffers(IntBuffer buffers);
	
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
	
	public void glDeleteTextures(int... textures);
	
	public void glDeleteTextures(IntBuffer textures);
	
	public void glDepthFunc(int func);
	
	public void glDepthMask(boolean flag);
	
	public void glDepthRange(float zNear, float zFar);
	
	public void glDisable(int cap);
	
	public void glDrawArrays(int mode, int first, int count);
	
	public void glDrawElements(int mode, int count, int type, IntBuffer indices);
	
	public void glDrawElements(int mode, int count, int type, int offset);
	
	public void glEnable(int cap);
	
	public void glFinish();
	
	public void glFlush();
	
	public void glFrontFace(int mode);
	
	public int glGenBuffers();
	
	public void glGenBuffers(IntBuffer buffers);
	
	public int glGenTextures();
	
	public void glGenTextures(int n, int[] textures, int offset);
	
	public void glGenTextures(IntBuffer textures);
	
	public int glGetError();
	
	public int glGetInteger(int pname);
	
	public void glGetIntegerv(int pname, int[] params, int offset);
	
	public void glGetIntegerv(int pname, IntBuffer params);
	
	public String glGetString(int name);
	
	public void glHint(int target, int mode);
	
	public boolean glIsBuffer(int buffer);
	
	public boolean glIsTexture(int texture);
	
	public void glLogicOp(int op);
	
	public void glPixelStorei(int pname, int param);
	
	public void glPointSize(float size);
	
	public void glReadPixels(int x, int y, int width, int height, int format, int type, ByteBuffer pixels);
	
	public void glScissor(int x, int y, int width, int height);
	
	public void glStencilFunc(int func, int ref, int mask);
	
	public void glStencilMask(int mask);
	
	public void glStencilOp(int fail, int zfail, int zpass);
	
	public void glTexImage2D(int target, int level, int internalformat, int width, int height, int border, int format, int type, IntBuffer pixels);
	
	public void glTexImage3D(int target, int level, int internalFormat, int width, int height, int depth, int border, int format, int type, IntBuffer pixels);
	
	public void glTexParameterf(int target, int pname, float param);
	
	public void glTexParameterx(int target, int pname, int param);
	
	public void glTexSubImage2D(int target, int level, int xoffset, int yoffset, int width, int height, int format, int type, ByteBuffer pixels);
	
	public void glViewport(int x, int y, int width, int height);
	
}
