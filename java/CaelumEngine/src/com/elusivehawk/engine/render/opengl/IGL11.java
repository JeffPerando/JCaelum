
package com.elusivehawk.engine.render.opengl;

import java.nio.IntBuffer;
import com.elusivehawk.engine.render.Color;

/**
 * 
 * Supports OpenGL v1.1.
 * 
 * @author Elusivehawk
 */
public interface IGL11
{
	public void glActiveTexture(int texture);
	
	public void glActiveTexture(ITexture texture);
	
	public void glBindTexture(int target, int texture);
	
	public void glBindTexture(int target, ITexture texture);
	
	public void glBlendFunc(int sfactor, int dfactor);
	
	public void glClear(int mask);
	
	public void glClearColor(Color col);
	
	public void glClearColor(float r, float g, float b, float a);
	
	public void glCopyTexImage1D(int target, int level, int internalFormat, int x, int y, int width, int border);
	
	public void glCopyTexImage2D(int target, int level, int internalFormat, int x, int y, int width, int height, int border);
	
	public void glCopyTexSubImage1D(int target, int level, int xoffset, int x, int y, int width);
	
	public void glCopyTexSubImage2D(int target, int level, int xoffset, int yoffset, int x, int y, int width, int height);
	
	public void glCopyTexSubImage3D(int target, int level, int xoffset, int yoffset, int zoffset, int x, int y, int width, int height);
	
	public void glCullFace(int mode);
	
	public void glDeleteTextures(int length, int... textures);
	
	public void glDepthFunc(int func);
	
	public void glDepthMask(boolean flag);
	
	public void glDepthRange(float zNear, float zFar);
	
	public void glDisable(int cap);
	
	public void glDrawArrays(int mode, int first, int count);
	
	public void glDrawElements(int mode, int count, int type, java.nio.Buffer indices);
	
	public void glEnable(int cap);
	
	public void glFinish();
	
	public void glFlush();
	
	public void glFrontFace(int mode);
	
	public int glGenTextures();
	
	public void glGenTextures(int n, int[] textures, int offset);
	
	public void glGenTextures(int n, IntBuffer textures);
	
	public int glGetError();
	
	public void glGetIntegerv(int pname, int[] params, int offset);
	
	public void glGetIntegerv(int pname, IntBuffer params);
	
	public String glGetString(int name);
	
	public void glHint(int target, int mode);
	
	public void glLogicOp(int op);
	
	public void glPixelStorei(int pname, int param);
	
	public void glPointSize(float size);
	
	public void glReadPixels(int x, int y, int width, int height, int format, int type, java.nio.Buffer pixels);
	
	public void glScissor(int x, int y, int width, int height);
	
	public void glStencilFunc(int func, int ref, int mask);
	
	public void glStencilMask(int mask);
	
	public void glStencilOp(int fail, int zfail, int zpass);
	
	public void glTexImage2D(int target, int level, int internalformat, int width, int height, int border, int format, int type, java.nio.Buffer pixels);
	
	public void glTexParameterf(int target, int pname, float param);
	
	public void glTexParameterx(int target, int pname, int param);
	
	public void glTexSubImage2D(int target, int level, int xoffset, int yoffset, int width, int height, int format, int type, java.nio.Buffer pixels);
	
	public void glViewport(int x, int y, int width, int height);
	
}
