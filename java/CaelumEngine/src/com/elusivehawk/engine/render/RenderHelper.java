
package com.elusivehawk.engine.render;

import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.ByteBuffer;
import java.util.List;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import com.elusivehawk.engine.CaelumEngine;
import com.elusivehawk.engine.EnumLogType;
import com.elusivehawk.engine.render.opengl.GLConst;
import com.elusivehawk.engine.render.opengl.GLEnumError;
import com.elusivehawk.engine.render.opengl.GLEnumPolyType;
import com.elusivehawk.engine.render.opengl.GLEnumShader;
import com.elusivehawk.engine.render.opengl.GLEnumTexture;
import com.elusivehawk.engine.render.opengl.GLException;
import com.elusivehawk.engine.render.opengl.GLProgram;
import com.elusivehawk.engine.render.opengl.IGL1;
import com.elusivehawk.engine.render.opengl.IGL2;
import com.elusivehawk.engine.render.opengl.IGL3;
import com.elusivehawk.util.BufferHelper;
import com.elusivehawk.util.FileHelper;
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
	
	public static RenderContext renderContext()
	{
		return renderContext(true);
	}
	
	public static RenderContext renderContext(boolean safe)
	{
		return (RenderContext)CaelumEngine.getContext(safe);
	}
	
	public static IGL1 gl1()
	{
		return renderContext().getGL1();
	}
	
	public static IGL2 gl2()
	{
		return renderContext().getGL2();
	}
	
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
		List<ILegibleImage> ret = null;
		
		if (img.getName().endsWith(".gif"))
		{
			ret = readGifFile(img);
		}
		else
		{
			try
			{
				PNGDecoder dec = new PNGDecoder(FileHelper.createInStream(img));
				ByteBuffer buf = BufferHelper.createByteBuffer(dec.getWidth() * dec.getHeight() * 4);
				
				dec.decode(buf, dec.getWidth() * 4, PNGDecoder.Format.RGBA);
				
				ret = Lists.newArrayList();
				
				ret.add(new LegibleByteImage(buf, dec.getWidth(), dec.getHeight()));
				
			}
			catch (Exception e)
			{
				CaelumEngine.log().err(null, e);
				
			}
			
		}
		
		return ret;
	}
	
	private static List<ILegibleImage> readGifFile(File gif)
	{
		try
		{
			ImageReader r = ImageIO.getImageReadersByFormatName("gif").next();
			ImageInputStream in = ImageIO.createImageInputStream(gif);
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
			CaelumEngine.log().err(null, e);
			
		}
		
		return null;
	}
	
	public static int processImage(ILegibleImage img) throws GLException
	{
		return processImage(img, img.getFormat());
	}
	
	public static int processImage(RenderContext rcon, ILegibleImage img) throws GLException
	{
		return processImage(rcon, img.toBytes(), img.getHeight(), img.getWidth());
	}
	
	public static int processImage(ILegibleImage img, ColorFormat format) throws GLException
	{
		return processImage(img.toBytes(format), img.getWidth(), img.getHeight());
	}
	
	public static int processImage(ByteBuffer buf, int w, int h) throws GLException
	{
		return processImage(renderContext(), buf, w, h);
	}
	
	public static int processImage(RenderContext rcon, ByteBuffer buf, int w, int h) throws GLException
	{
		IGL1 gl1 = rcon.getGL1();
		IGL3 gl3 = rcon.getGL3();
		
		int tex = 0;
		
		try
		{
			tex = gl1.glGenTextures();
			
			gl1.glBindTexture(GLEnumTexture.GL_TEXTURE_2D, tex);
			
			gl1.glPixelStorei(GLConst.GL_UNPACK_ALIGNMENT, 1);
			gl1.glTexImage2D(GLConst.GL_TEXTURE_2D, 0, GLConst.GL_RGB, w, h, 0, GLConst.GL_RGBA, GLConst.GL_UNSIGNED_BYTE, buf);
			gl3.glGenerateMipmap(GLConst.GL_TEXTURE_2D);
			
			gl1.glTexParameterx(GLConst.GL_TEXTURE_2D, GLConst.GL_TEXTURE_MIN_FILTER, GLConst.GL_LINEAR);
			gl1.glTexParameterx(GLConst.GL_TEXTURE_2D, GLConst.GL_TEXTURE_MAG_FILTER, GLConst.GL_LINEAR);
			
			gl1.glBindTexture(GLEnumTexture.GL_TEXTURE_2D, 0);
			
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
				CaelumEngine.log().log(EnumLogType.DEBUG, "#include found: %s", include);
				
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
	
	public static int loadShader(String src, GLEnumShader type, RenderContext rcon)
	{
		IGL2 gl2 = rcon.getGL2();
		
		int id = gl2.glCreateShader(type);
		gl2.glShaderSource(id, src);
		gl2.glCompileShader(id);
		
		try
		{
			checkForGLError(rcon);
			
		}
		catch (Exception e)
		{
			CaelumEngine.log().err(null, e);
			
			return 0;
		}
		
		return id;
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
	
	public static boolean render(ILogicalRender lr, double delta)
	{
		return render(renderContext(), lr, delta);
	}
	
	public static boolean render(RenderContext rcon, ILogicalRender lr, double delta)
	{
		if (!lr.updateBeforeRender(rcon, delta))
		{
			return false;
		}
		
		GLProgram p = lr.getProgram();
		
		if (!p.bind(rcon))
		{
			return false;
		}
		
		IGL1 gl1 = rcon.getGL1();
		
		gl1.glDrawElements(lr.getPolygonType(), lr.getPolyCount(), GLConst.GL_UNSIGNED_INT, 0);
		
		p.unbind(rcon);
		
		return true;
	}
	
}
