
package com.elusivehawk.engine.android;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import android.annotation.TargetApi;
import android.opengl.GLES10;
import android.opengl.GLES30;
import android.os.Build;
import com.elusivehawk.engine.render.opengl.IGL1;
import com.elusivehawk.engine.render.opengl.ITexture;
import com.elusivehawk.engine.render2.Color;
import com.elusivehawk.engine.render2.EnumColorFilter;
import com.elusivehawk.engine.util.BufferHelper;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class GLES1 implements IGL1
{
	@Override
	public void glActiveTexture(int texture)
	{
		GLES10.glActiveTexture(texture);
		
	}
	
	@Override
	public void glActiveTexture(ITexture texture)
	{
		this.glActiveTexture(texture == null ? 0 : texture.getTexture(true));
		
	}
	
	@Override
	public void glBindTexture(int target, int texture)
	{
		GLES10.glBindTexture(target, texture);
		
	}
	
	@Override
	public void glBindTexture(int target, ITexture texture)
	{
		this.glBindTexture(target, texture == null ? 0 : texture.getTexture(true));
		
	}
	
	@Override
	public void glBlendFunc(int sfactor, int dfactor)
	{
		GLES10.glBlendFunc(sfactor, dfactor);
		
	}
	
	@Override
	public void glClear(int mask)
	{
		GLES10.glClear(mask);
		
	}
	
	@Override
	public void glClearColor(Color col)
	{
		this.glClearColor(col.getColorFloat(EnumColorFilter.RED), col.getColorFloat(EnumColorFilter.GREEN), col.getColorFloat(EnumColorFilter.BLUE), col.getColorFloat(EnumColorFilter.ALPHA));
		
	}
	
	@Override
	public void glClearColor(float r, float g, float b, float a)
	{
		GLES10.glClearColor(r, g, b, a);
		
	}
	
	@Override
	public void glCopyTexImage1D(int target, int level, int internalFormat,
			int x, int y, int width, int border)
	{
		//XXX Unsupported
	}
	
	@Override
	public void glCopyTexImage2D(int target, int level, int internalformat,
			int x, int y, int width, int height, int border)
	{
		GLES10.glCopyTexImage2D(target, level, internalformat, x, y, width, height, border);
		
	}
	
	@Override
	public void glCopyTexSubImage1D(int target, int level, int xoffset, int x,
			int y, int width)
	{
		//XXX Unsupported
		
	}
	
	@Override
	public void glCopyTexSubImage2D(int target, int level, int xoffset,
			int yoffset, int x, int y, int width, int height)
	{
		GLES10.glCopyTexSubImage2D(target, level, xoffset, yoffset, x, y, width, height);
		
	}
	
	@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
	@Override
	public void glCopyTexSubImage3D(int target, int level, int xoffset,
			int yoffset, int zoffset, int x, int y, int width, int height)
	{
		GLES30.glCopyTexSubImage3D(target, level, xoffset, yoffset, zoffset, x, y, width, height);
		
	}
	
	@Override
	public void glCullFace(int mode)
	{
		GLES10.glCullFace(mode);
		
	}
	
	@Override
	public void glDeleteTextures(int length, int... textures)
	{
		GLES10.glDeleteTextures(length, BufferHelper.makeIntBuffer(textures));
		
	}
	
	@Override
	public void glDepthFunc(int func)
	{
		GLES10.glDepthFunc(func);
		
	}
	
	@Override
	public void glDepthMask(boolean flag)
	{
		GLES10.glDepthMask(flag);
		
	}
	
	@Override
	public void glDepthRange(float zNear, float zFar)
	{
		GLES10.glDepthRangef(zNear, zFar);
		
	}
	
	@Override
	public void glDisable(int cap)
	{
		GLES10.glDisable(cap);
		
	}
	
	@Override
	public void glDrawArrays(int mode, int first, int count)
	{
		GLES10.glDrawArrays(mode, first, count);
		
	}
	
	@Override
	public void glDrawElements(int mode, int count, int type, IntBuffer indices)
	{
		GLES10.glDrawElements(mode, count, type, indices);
		
	}
	
	@Override
	public void glEnable(int cap)
	{
		GLES10.glEnable(cap);
		
	}
	
	@Override
	public void glFinish()
	{
		GLES10.glFinish();
		
	}
	
	@Override
	public void glFlush()
	{
		GLES10.glFlush();
		
	}
	
	@Override
	public void glFrontFace(int mode)
	{
		GLES10.glFrontFace(mode);
		
	}
	
	@Override
	public int glGenTextures()
	{
		IntBuffer buf = BufferHelper.createIntBuffer(1);
		
		this.glGenTextures(buf);
		
		return buf.get(0);
	}
	
	@Override
	public void glGenTextures(int n, int[] textures, int offset)
	{
		GLES10.glGenTextures(n, textures, offset);
		
	}
	
	@Override
	public void glGenTextures(IntBuffer textures)
	{
		GLES10.glGenTextures(textures.remaining(), textures);
	}
	
	@Override
	public int glGetError()
	{
		return GLES10.glGetError();
	}
	
	@Override
	public int glGetInteger(int pname)
	{
		int[] result = new int[1];
		
		this.glGetIntegerv(pname, result, 0);
		
		return result[0];
	}
	
	@Override
	public void glGetIntegerv(int pname, int[] params, int offset)
	{
		GLES10.glGetIntegerv(pname, params, offset);
		
	}
	
	@Override
	public void glGetIntegerv(int pname, IntBuffer params)
	{
		GLES10.glGetIntegerv(pname, params);
		
	}
	
	@Override
	public String glGetString(int name)
	{
		return GLES10.glGetString(name);
	}
	
	@Override
	public void glHint(int target, int mode)
	{
		GLES10.glHint(target, mode);
		
	}
	
	@Override
	public void glLogicOp(int op)
	{
		GLES10.glLogicOp(op);
		
	}
	
	@Override
	public void glPixelStorei(int pname, int param)
	{
		GLES10.glPixelStorei(pname, param);
		
	}
	
	@Override
	public void glPointSize(float size)
	{
		GLES10.glPointSize(size);
		
	}
	
	@Override
	public void glReadPixels(int x, int y, int width, int height, int format,
			int type, ByteBuffer pixels)
	{
		GLES10.glReadPixels(x, y, width, height, format, type, pixels);
		
	}
	
	@Override
	public void glScissor(int x, int y, int width, int height)
	{
		GLES10.glScissor(x, y, width, height);
		
	}
	
	@Override
	public void glStencilFunc(int func, int ref, int mask)
	{
		GLES10.glStencilFunc(func, ref, mask);
		
	}
	
	@Override
	public void glStencilMask(int mask)
	{
		GLES10.glStencilMask(mask);
		
	}
	
	@Override
	public void glStencilOp(int fail, int zfail, int zpass)
	{
		GLES10.glStencilOp(fail, zfail, zpass);
		
	}
	
	@Override
	public void glTexImage2D(int target, int level, int internalformat,
			int width, int height, int border, int format, int type,
			IntBuffer pixels)
	{
		GLES10.glTexImage2D(target, level, internalformat, width, height, border, format, type, pixels);
		
	}
	
	@Override
	public void glTexParameterf(int target, int pname, float param)
	{
		GLES10.glTexParameterf(target, pname, param);
		
	}
	
	@Override
	public void glTexParameterx(int target, int pname, int param)
	{
		GLES10.glTexParameterx(target, pname, param);
		
	}
	
	@Override
	public void glTexSubImage2D(int target, int level, int xoffset,
			int yoffset, int width, int height, int format, int type,
			ByteBuffer pixels)
	{
		GLES10.glTexSubImage2D(target, level, xoffset, yoffset, width, height, format, type, pixels);
		
	}
	
	@Override
	public void glViewport(int x, int y, int width, int height)
	{
		GLES10.glViewport(x, y, width, height);
		
	}
	
}
