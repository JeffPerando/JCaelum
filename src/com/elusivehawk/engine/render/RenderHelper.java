
package com.elusivehawk.engine.render;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.List;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import com.elusivehawk.engine.CaelumEngine;
import com.elusivehawk.engine.render.opengl.GLConst;
import com.elusivehawk.engine.render.opengl.GLEnumError;
import com.elusivehawk.engine.render.opengl.GLEnumPolyType;
import com.elusivehawk.engine.render.opengl.GLEnumTexture;
import com.elusivehawk.engine.render.opengl.GLException;
import com.elusivehawk.engine.render.opengl.IGL1;
import com.elusivehawk.engine.render.opengl.IGL2;
import com.elusivehawk.engine.render.opengl.IGL3;
import com.elusivehawk.util.BufferHelper;
import com.elusivehawk.util.EnumLogType;
import com.elusivehawk.util.FileHelper;
import com.elusivehawk.util.Logger;
import com.elusivehawk.util.StringHelper;
import com.elusivehawk.util.io.ByteBuffers;
import com.elusivehawk.util.storage.Buffer;
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
		return CaelumEngine.renderContext().getGL4();
	}*/
	
	public static List<ILegibleImage> readImg(File img)
	{
		return readImg(new BufferedInputStream(FileHelper.createInStream(img)), img.getName().endsWith(".gif"));
	}
	
	public static List<ILegibleImage> readImg(InputStream is, boolean isGif)
	{
		List<ILegibleImage> ret = null;
		
		if (isGif)
		{
			ret = readGifFile(is);
			
		}
		else
		{
			try
			{
				PNGDecoder dec = new PNGDecoder(is);
				ByteBuffer buf = BufferHelper.createByteBuffer(dec.getWidth() * dec.getHeight() * 4);
				
				dec.decode(buf, dec.getWidth() * 4, PNGDecoder.Format.RGBA);
				
				ret = Lists.newArrayList();
				
				ret.add(new LegibleByteImage(buf, dec.getWidth(), dec.getHeight()));
				
			}
			catch (Exception e)
			{
				Logger.log().err(e);
				
			}
			
		}
		
		return ret;
	}
	
	private static List<ILegibleImage> readGifFile(InputStream is)
	{
		try
		{
			ImageReader r = ImageIO.getImageReadersByFormatName("gif").next();
			ImageInputStream in = ImageIO.createImageInputStream(is);
			r.setInput(in, false);
			
			int imgs = r.getNumImages(true);
			
			List<ILegibleImage> ret = Lists.newArrayListWithCapacity(imgs);
			
			for (int c = 0; c < imgs; c++)
			{
				ret.add(new LegibleBufferedImage(r.read(c)));
				
			}
			
			in.close();
			
			return ret;
		}
		catch (Exception e)
		{
			Logger.log().err(e);
			
		}
		
		return null;
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
			gl1.glDeleteTextures(tex);
			
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
	
	@Deprecated
	public static synchronized BufferedImage captureScreen(IDisplay win)
	{
		ByteBuffer buf = BufferHelper.createByteBuffer(win.getHeight() * win.getWidth() * 4);
		
		gl1().glReadPixels(0, 0, win.getWidth(), win.getHeight(), GLConst.GL_RGBA, GLConst.GL_BYTE, buf);
		
		BufferedImage ret = new BufferedImage(win.getWidth(), win.getHeight(), BufferedImage.TYPE_INT_ARGB);
		ByteBuffers io = new ByteBuffers(buf, null);
		
		for (int x = 0; x < ret.getWidth(); x++)
		{
			for (int y = 0; y < ret.getHeight(); y++)
			{
				ret.setRGB(x, y, ColorFormat.ARGB.convert(new Color(ColorFormat.RGBA, io)).getColor());
				
			}
			
		}
		
		return ret;
	}
	
	public static int getPolygonCount(int points, GLEnumPolyType poly)
	{
		return poly == GLEnumPolyType.GL_TRIANGLE_STRIP ? points - 2 : points / poly.getPointCount();
	}
	
	public static int getPointCount(int polycount, GLEnumPolyType poly)
	{
		return poly == GLEnumPolyType.GL_TRIANGLE_STRIP ? polycount + 2 : polycount * poly.getPointCount();
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
