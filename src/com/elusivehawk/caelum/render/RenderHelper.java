
package com.elusivehawk.caelum.render;

import java.nio.ByteBuffer;
import com.elusivehawk.caelum.render.gl.GL1;
import com.elusivehawk.caelum.render.gl.GL3;
import com.elusivehawk.caelum.render.gl.GLConst;
import com.elusivehawk.caelum.render.gl.GLEnumError;
import com.elusivehawk.caelum.render.gl.GLEnumTexture;
import com.elusivehawk.caelum.render.gl.GLException;
import com.elusivehawk.caelum.render.tex.ColorFilter;
import com.elusivehawk.caelum.render.tex.ColorFormat;
import com.elusivehawk.caelum.render.tex.ILegibleImage;

/**
 * 
 * Helps with rendering stuff.
 * 
 * @author Elusivehawk
 */
public final class RenderHelper
{
	private RenderHelper(){}
	
	public static int genTexture(RenderContext rcon, ILegibleImage img)
	{
		return genTexture(rcon, img, img.getFormat());
	}
	
	public static int genTexture(RenderContext rcon, ILegibleImage img, ColorFormat format)
	{
		return genTexture(rcon, img, format, true);
	}
	
	public static int genTexture(RenderContext rcon, ILegibleImage img, ColorFormat format, boolean mipmap)
	{
		return genTexture(rcon, GLEnumTexture.GL_TEXTURE_2D, img.toBytes(format), img.getWidth(), img.getHeight(), format.supports(ColorFilter.ALPHA), mipmap);
	}
	
	public static int genTexture(RenderContext rcon, GLEnumTexture type, int width, int height, boolean alpha)
	{
		return genTexture(rcon, type, null, width, height, alpha, true);
	}
	
	public static int genTexture(RenderContext rcon, GLEnumTexture type, int width, int height, boolean alpha, boolean mipmap)
	{
		return genTexture(rcon, type, null, width, height, alpha, mipmap);
	}
	
	public static int genTexture(RenderContext rcon, GLEnumTexture type, ByteBuffer texture, int width, int height, boolean alpha)
	{
		return genTexture(rcon, type, texture, width, height, alpha, true);
	}
	
	public static int genTexture(RenderContext rcon, GLEnumTexture type, ByteBuffer texture, int width, int height, boolean alpha, boolean mipmap)
	{
		assert width > 0 && height > 0;
		
		int tex = 0;
		
		try
		{
			tex = GL1.glGenTextures();
			
			GL1.glBindTexture(type, tex);
			
			GL1.glPixelStorei(GLConst.GL_UNPACK_ALIGNMENT, 1);
			GL1.glTexImage2D(type, 0, alpha ? GLConst.GL_RGBA : GLConst.GL_RGB, width, height, 0, alpha ? GLConst.GL_RGBA : GLConst.GL_RGB, GLConst.GL_UNSIGNED_BYTE, texture);
			
			if (mipmap)
			{
				GL3.glGenerateMipmap(type);
				
			}
			
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
		GLEnumError err = GL1.glGetError();
		
		if (err != GLEnumError.GL_NO_ERROR)
		{
			throw new GLException(err);
		}
		
	}
	
}
