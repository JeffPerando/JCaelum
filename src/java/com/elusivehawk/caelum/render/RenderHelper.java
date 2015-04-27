
package com.elusivehawk.caelum.render;

import java.nio.ByteBuffer;
import com.elusivehawk.caelum.render.gl.GL1;
import com.elusivehawk.caelum.render.gl.GL2;
import com.elusivehawk.caelum.render.gl.GL3;
import com.elusivehawk.caelum.render.gl.GLConst;
import com.elusivehawk.caelum.render.gl.GLEnumError;
import com.elusivehawk.caelum.render.gl.GLEnumTexture;
import com.elusivehawk.caelum.render.gl.GLException;
import com.elusivehawk.caelum.render.glsl.GLSLEnumSStatus;
import com.elusivehawk.caelum.render.glsl.IShader;
import com.elusivehawk.caelum.render.tex.ColorFormat;
import com.elusivehawk.caelum.render.tex.ILegibleImage;
import com.elusivehawk.caelum.window.Window;
import com.elusivehawk.util.CompInfo;
import com.elusivehawk.util.Logger;

/**
 * 
 * Helps with rendering stuff.
 * 
 * @author Elusivehawk
 */
public final class RenderHelper
{
	private RenderHelper(){}
	
	public static int genTexture(ILegibleImage img)
	{
		return genTexture(img, true);
	}
	
	public static int genTexture(ILegibleImage img, boolean mipmap)
	{
		return genTexture(GLEnumTexture.GL_TEXTURE_2D, img, mipmap);
	}
	
	public static int genTexture(GLEnumTexture type, ILegibleImage img)
	{
		return genTexture(type, img, true);
	}
	
	public static int genTexture(GLEnumTexture type, ILegibleImage img, boolean mipmap)
	{
		return genTexture(type, img.getFormat() == ColorFormat.RGBA ? img.toBytes() : img.toBytes(ColorFormat.RGBA), img.getWidth(), img.getHeight(), mipmap);
	}
	
	public static int genTexture(GLEnumTexture type, Window window)
	{
		return genTexture(type, window.getWidth(), window.getHeight());
	}
	
	public static int genTexture(GLEnumTexture type, int width, int height)
	{
		return genTexture(type, null, width, height, true);
	}
	
	public static int genTexture(GLEnumTexture type, Window window, boolean mipmap)
	{
		return genTexture(type, window.getWidth(), window.getHeight(), mipmap);
	}
	
	public static int genTexture(GLEnumTexture type, int width, int height, boolean mipmap)
	{
		return genTexture(type, null, width, height, mipmap);
	}
	
	public static int genTexture(GLEnumTexture type, ByteBuffer texture, int width, int height)
	{
		return genTexture(type, texture, width, height, true);
	}
	
	public static int genTexture(GLEnumTexture type, ByteBuffer texture, int width, int height, boolean mipmap)
	{
		assert width > 0 && height > 0;
		
		int tex = 0;
		
		try
		{
			tex = GL1.glGenTexture();
			
			GL1.glActiveTexture(GLConst.GL_TEXTURE0);
			GL1.glBindTexture(type, tex);
			
			GL1.glPixelStorei(GLConst.GL_UNPACK_ALIGNMENT, 1);
			GL1.glTexImage2D(type, 0, GLConst.GL_RGBA, width, height, 0, GLConst.GL_RGBA, GLConst.GL_UNSIGNED_BYTE, texture);
			
			if (mipmap)
			{
				GL3.glGenerateMipmap(type);
				
			}
			
			GL1.glTexParameteri(GLConst.GL_TEXTURE_2D, GLConst.GL_TEXTURE_WRAP_S, GLConst.GL_REPEAT);
			GL1.glTexParameteri(GLConst.GL_TEXTURE_2D, GLConst.GL_TEXTURE_WRAP_T, GLConst.GL_REPEAT);
			
			GL1.glTexParameteri(GLConst.GL_TEXTURE_2D, GLConst.GL_TEXTURE_MAG_FILTER, GLConst.GL_NEAREST);
			GL1.glTexParameteri(GLConst.GL_TEXTURE_2D, GLConst.GL_TEXTURE_MIN_FILTER, GLConst.GL_LINEAR_MIPMAP_LINEAR);
			
	        GL1.glBindTexture(type, 0);
			
			return tex;
		}
		catch (GLException e)
		{
			if (tex != 0)
			{
				GL1.glDeleteTextures(tex);
				
			}
			
			throw e;
		}
		
	}
	
	public static void checkForGLError() throws GLException
	{
		GLEnumError err = GL1.glGetErrorEnum();
		
		if (err != GLEnumError.GL_NO_ERROR)
		{
			throw new GLException(err);
		}
		
	}
	
	public static int compileShader(IShader shader) throws GLException
	{
		if (shader.isCompiled())
		{
			return shader.getShaderId();
		}
		
		String src = shader.getSource();
		
		if (src == null || src.equals(""))
		{
			return 0;
		}
		
		int id = GL2.glCreateShader(shader.getType());
		
		if (id == 0)
		{
			throw new GLException("Cannot load shader: Out of shader IDs");
		}
		
		GL2.glShaderSource(id, src);
		GL2.glCompileShader(id);
		
		int status = GL2.glGetShader(id, GLSLEnumSStatus.GL_COMPILE_STATUS);
		
		if (status == GLConst.GL_FALSE)
		{
			if (CompInfo.DEBUG)
			{
				Logger.warn("Cannot compile shader %s", shader);
				Logger.info("Shader log for shader %s (ID %s) of type %s:\n%s", shader, id, shader.getType(), GL2.glGetShaderInfoLog(id, GL2.glGetShader(id, GLSLEnumSStatus.GL_INFO_LOG_LENGTH)));
				
			}
			
			GL2.glDeleteShader(id);
			
		}
		
		Logger.debug("Successfully compiled shader %s", shader);
		
		return id;
	}
	
}
