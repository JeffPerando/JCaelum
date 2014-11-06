
package com.elusivehawk.caelum.render;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.List;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import com.elusivehawk.caelum.CaelumEngine;
import com.elusivehawk.caelum.render.gl.GLConst;
import com.elusivehawk.caelum.render.gl.GLEnumDrawType;
import com.elusivehawk.caelum.render.gl.GLEnumError;
import com.elusivehawk.caelum.render.gl.GLEnumTexture;
import com.elusivehawk.caelum.render.gl.GLException;
import com.elusivehawk.caelum.render.gl.IGL1;
import com.elusivehawk.caelum.render.gl.IGL2;
import com.elusivehawk.caelum.render.gl.IGL3;
import com.elusivehawk.caelum.render.tex.Color;
import com.elusivehawk.caelum.render.tex.ColorFilter;
import com.elusivehawk.caelum.render.tex.ColorFormat;
import com.elusivehawk.caelum.render.tex.ILegibleImage;
import com.elusivehawk.caelum.render.tex.LegibleBufferedImage;
import com.elusivehawk.caelum.render.tex.LegibleByteImage;
import com.elusivehawk.util.EnumLogType;
import com.elusivehawk.util.FileHelper;
import com.elusivehawk.util.Logger;
import com.elusivehawk.util.storage.Buffer;
import com.elusivehawk.util.storage.BufferHelper;
import com.elusivehawk.util.string.StringHelper;
import com.google.common.collect.Lists;
import de.matthiasmann.twl.utils.PNGDecoder;

/**
 * 
 * Helps with rendering stuff.
 * 
 * @author Elusivehawk
 */
public final class RenderHelper
{
	private RenderHelper(){}
	
	@Deprecated
	public static RenderContext renderContext()
	{
		return CaelumEngine.renderContext();
	}
	
	@Deprecated
	public static IGL1 gl1()
	{
		return renderContext().getGL1();
	}
	
	@Deprecated
	public static IGL2 gl2()
	{
		return renderContext().getGL2();
	}
	
	@Deprecated
	public static IGL3 gl3()
	{
		return renderContext().getGL3();
	}
	
	/*public static IGL4 gl4()
	{
		return renderContext().getGL4();
	}*/
	
	@Deprecated
	public static List<ILegibleImage> readImg(File img)
	{
		return readImg(new BufferedInputStream(FileHelper.createInStream(img)), img.getName().endsWith(".gif"));
	}
	
	@Deprecated
	public static List<ILegibleImage> readImg(InputStream is, boolean isGif)
	{
		List<ILegibleImage> ret = Lists.newArrayList();
		
		if (isGif)
		{
			readGifFile(is, ret);
			
		}
		else
		{
			try
			{
				PNGDecoder dec = new PNGDecoder(is);
				ByteBuffer buf = BufferHelper.createByteBuffer(dec.getWidth() * dec.getHeight() * 4);
				
				dec.decode(buf, dec.getWidth() * 4, PNGDecoder.Format.RGBA);
				
				ret.add(new LegibleByteImage(buf, dec.getWidth(), dec.getHeight()));
				
			}
			catch (Exception e)
			{
				Logger.log().err(e);
				
			}
			
		}
		
		return ret;
	}
	
	@Deprecated
	private static void readGifFile(InputStream is, List<ILegibleImage> ret)
	{
		try
		{
			ImageReader r = ImageIO.getImageReadersByFormatName("gif").next();
			ImageInputStream in = ImageIO.createImageInputStream(is);
			r.setInput(in, false);
			
			int imgs = r.getNumImages(true);
			
			for (int c = 0; c < imgs; c++)
			{
				ret.add(new LegibleBufferedImage(r.read(c)));
				
			}
			
			in.close();
			
		}
		catch (Exception e)
		{
			Logger.log().err(e);
			
		}
		
	}
	
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
		
		IGL1 gl1 = rcon.getGL1();
		
		int tex = 0;
		
		try
		{
			tex = gl1.glGenTextures();
			
			gl1.glBindTexture(type, tex);
			
			gl1.glPixelStorei(GLConst.GL_UNPACK_ALIGNMENT, 1);
			gl1.glTexImage2D(type, 0, alpha ? GLConst.GL_RGBA : GLConst.GL_RGB, width, height, 0, alpha ? GLConst.GL_RGBA : GLConst.GL_RGB, GLConst.GL_UNSIGNED_BYTE, texture);
			
			if (mipmap)
			{
				rcon.getGL3().glGenerateMipmap(type);
				
			}
			
			gl1.glBindTexture(type, 0);
			
			return tex;
		}
		catch (GLException e)
		{
			if (tex != 0)
			{
				gl1.glDeleteTextures(tex);
				
			}
			
			throw e;
		}
		
	}
	
	/**
	 * 
	 * Formats shader source code.
	 * 
	 * @param src
	 * @param parentDir
	 * @return
	 * 
	 * @deprecated To be removed once OpenGL NG comes out, since shaders will be pre-compiled.
	 */
	@Deprecated
	public static String formatShaderSource(String src, File parentDir)
	{
		List<String> inc = Lists.newArrayList();
		List<File> files = FileHelper.getFiles(parentDir);
		int in;
		
		while ((in = src.indexOf(RenderConst.INCLUDE)) != -1)
		{
			int newline = src.indexOf("\n", in);
			String include;
			
			if (newline == -1)
			{
				include = src.substring(in);
				
			}
			else
			{
				include = src.substring(in, newline);
				
			}
			
			String[] split = include.split(" ");
			
			String rep = "";
			
			if (split != null && split.length == 2)
			{
				Logger.log().log(EnumLogType.DEBUG, "#include found: %s", include);
				
				String loc = split[1];
				
				if (!inc.contains(loc))
				{
					rep = StringHelper.readToOneLine(FileHelper.getChild(loc, files));
					
					inc.add(loc);
					
				}
				
			}
			
			src.replaceFirst(include, rep);
			
		}
		
		return src;
	}
	
	public static int getPolygonCount(int points, GLEnumDrawType poly)
	{
		return poly == GLEnumDrawType.GL_TRIANGLE_STRIP ? points - 2 : points / poly.getPointCount();
	}
	
	public static int getPointCount(int polycount, GLEnumDrawType poly)
	{
		return poly == GLEnumDrawType.GL_TRIANGLE_STRIP ? polycount + 2 : polycount * poly.getPointCount();
	}
	
	public static void checkForGLError() throws GLException
	{
		checkForGLError(gl1());
		
	}
	
	public static void checkForGLError(RenderContext rcon) throws GLException
	{
		checkForGLError(rcon.getGL1());
		
	}
	
	public static void checkForGLError(IGL1 gl) throws GLException
	{
		GLEnumError err = gl.glGetError();
		
		if (err != GLEnumError.GL_NO_ERROR)
		{
			throw new GLException(err);
		}
		
	}
	
	public static Buffer<Float> mixColors(Color a, Color b)
	{
		Buffer<Float> ret = new Buffer<Float>();
		
		for (ColorFilter col : ColorFilter.values())
		{
			ret.add((a.getColorf(col) + b.getColorf(col)) % 1f);
			
		}
		
		ret.rewind();
		
		return ret;
	}
	
}
