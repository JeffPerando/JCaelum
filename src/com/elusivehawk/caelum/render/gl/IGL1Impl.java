
package com.elusivehawk.caelum.render.gl;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

/**
 * 
 * OpenGL v1.x wrapper interface
 * 
 * @author Elusivehawk
 */
public interface IGL1Impl
{
	void glActiveTexture(int texture);
	
	void glBindBuffer(int target, int buffer);
	
	void glBindTexture(int target, int texture);
	
	void glBlendFunc(int sfactor, int dfactor);
	
	void glBufferData(int target, int type, Buffer data, int usage);
	
	void glBufferSubData(int target, int offset, int type, Buffer data);
	
	void glClear(int mask);
	
	void glClearColor(float r, float g, float b, float a);
	
	void glCopyTexImage1D(int target, int level, int internalFormat, int x, int y, int width, int border);
	
	void glCopyTexImage2D(int target, int level, int internalFormat, int x, int y, int width, int height, int border);
	
	void glCopyTexSubImage1D(int target, int level, int xoffset, int x, int y, int width);
	
	void glCopyTexSubImage2D(int target, int level, int xoffset, int yoffset, int x, int y, int width, int height);
	
	void glCopyTexSubImage3D(int target, int level, int xoffset, int yoffset, int zoffset, int x, int y, int width, int height);
	
	void glCullFace(int mode);
	
	void glDeleteBuffers(IntBuffer buffers);
	
	void glDeleteTextures(IntBuffer textures);
	
	void glDepthFunc(int func);
	
	void glDepthMask(boolean flag);
	
	void glDepthRange(float zNear, float zFar);
	
	void glDisable(int cap);
	
	void glDrawArrays(int mode, int first, int count);
	
	void glDrawElements(int mode, int count, int type, IntBuffer indices);
	
	void glDrawElements(int mode, int count, int type, int offset);
	
	void glEnable(int cap);
	
	void glFinish();
	
	void glFlush();
	
	void glFrontFace(int mode);
	
	int glGenBuffers();
	
	void glGenBuffers(IntBuffer buffers);
	
	int glGenTextures();
	
	void glGenTextures(int n, int[] textures, int offset);
	
	void glGenTextures(IntBuffer textures);
	
	int glGetError();
	
	int glGetInteger(int pname);
	
	void glGetIntegerv(int pname, int[] params, int offset);
	
	void glGetIntegerv(int pname, IntBuffer params);
	
	String glGetString(int name);
	
	void glHint(int target, int mode);
	
	boolean glIsBuffer(int buffer);
	
	boolean glIsEnabled(int cap);
	
	boolean glIsTexture(int texture);
	
	void glLogicOp(int op);
	
	void glPixelStoref(int pname, float param);
	
	void glPixelStorei(int pname, int param);
	
	void glPointSize(float size);
	
	void glReadPixels(int x, int y, int width, int height, int format, int type, ByteBuffer pixels);
	
	void glScissor(int x, int y, int width, int height);
	
	void glStencilFunc(int func, int ref, int mask);
	
	void glStencilMask(int mask);
	
	void glStencilOp(int fail, int zfail, int zpass);
	
	void glTexImage2D(int target, int level, int internalFormat, int width, int height, int border, int format, int type, Buffer pixels);
	
	void glTexImage3D(int target, int level, int internalFormat, int width, int height, int depth, int border, int format, int type, Buffer pixels);
	
	void glTexParameterf(int target, int pname, float param);
	
	void glTexParameteri(int target, int pname, int param);
	
	void glTexSubImage2D(int target, int level, int xoffset, int yoffset, int width, int height, int format, int type, Buffer pixels);
	
	void glViewport(int x, int y, int width, int height);
	
}
