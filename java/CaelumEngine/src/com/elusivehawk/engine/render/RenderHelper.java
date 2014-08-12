
package com.elusivehawk.engine.render;

import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.Arrays;
import java.util.List;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import com.elusivehawk.engine.CaelumEngine;
import com.elusivehawk.engine.EnumLogType;
import com.elusivehawk.engine.assets.Shader;
import com.elusivehawk.engine.render.opengl.GLConst;
import com.elusivehawk.engine.render.opengl.GLEnumShader;
import com.elusivehawk.engine.render.opengl.GLException;
import com.elusivehawk.engine.render.opengl.IGL1;
import com.elusivehawk.engine.render.opengl.IGL2;
import com.elusivehawk.engine.render.opengl.IGL3;
import com.elusivehawk.util.BufferHelper;
import com.elusivehawk.util.StringHelper;
import com.elusivehawk.util.io.ByteBuffers;
import com.elusivehawk.util.storage.Buffer;
import com.google.common.collect.Lists;

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
		if (img.getName().endsWith(".gif"))
		{
			return readGifFile(img);
		}
		
		BufferedImage bufimg = null;
		
		try
		{
			bufimg = ImageIO.read(img);
			
		}
		catch (Exception e)
		{
			CaelumEngine.log().err(null, e);
			
		}
		
		if (bufimg != null)
		{
			return Arrays.asList(new LegibleBufferedImage(bufimg));
		}
		
		return null;
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
	
	public static int processImage(ILegibleImage img)
	{
		return processImage(img, img.getFormat());
	}
	
	public static int processImage(RenderContext rcon, ILegibleImage img)
	{
		return processImage(rcon, img.toInts(), img.getHeight(), img.getWidth());
	}
	
	public static int processImage(ILegibleImage img, ColorFormat format)
	{
		return processImage(img.toInts(format), img.getWidth(), img.getHeight());
	}
	
	public static int processImage(IntBuffer buf, int w, int h)
	{
		return processImage(renderContext(), buf, w, h);
	}
	
	public static int processImage(RenderContext rcon, IntBuffer buf, int w, int h)
	{
		IGL1 gl1 = rcon.getGL1();
		
		int glId = gl1.glGenTextures();
		
		gl1.glActiveTexture(glId);
		gl1.glPixelStorei(GLConst.GL_UNPACK_ALIGNMENT, 1);
		
		gl1.glTexParameterx(GLConst.GL_TEXTURE_2D, GLConst.GL_TEXTURE_MIN_FILTER, GLConst.GL_LINEAR);
		gl1.glTexParameterx(GLConst.GL_TEXTURE_2D, GLConst.GL_TEXTURE_MAG_FILTER, GLConst.GL_LINEAR);
		
		gl1.glTexImage2D(GLConst.GL_TEXTURE_2D, 0, GLConst.GL_RGBA8, w, h, 0, GLConst.GL_RGBA, GLConst.GL_UNSIGNED_BYTE, buf);
		
		gl1.glActiveTexture(0);
		
		checkForGLError(gl1);
		
		return glId;
	}
	
	public static String formatShaderSource(String src, File parentDir)
	{
		List<String> inc = Lists.newArrayList();
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
					rep = StringHelper.readToOneLine(new File(parentDir, loc));
					
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
	
	public static int getPointCount(int gl)
	{
		switch (gl)
		{
			case GLConst.GL_POINTS: return 1;
			case GLConst.GL_LINES: return 2;
			case GLConst.GL_TRIANGLES: return 3;
			case GLConst.GL_QUADS: return 4;
			case GLConst.GL_TRIANGLE_FAN: return 5;
			
		}
		
		return 0;
	}
	
	public static int getPolygonCount(int points, int gl)
	{
		return gl == GLConst.GL_TRIANGLE_STRIP ? points - 2 : points / getPointCount(gl);
	}
	
	public static int getPointCount(int polycount, int gl)
	{
		return gl == GLConst.GL_TRIANGLE_STRIP ? polycount + 2 : polycount * getPointCount(gl);
	}
	
	public static void checkForGLError() throws GLException
	{
		checkForGLError(gl1());
		
	}
	
	public static void checkForGLError(RenderContext con) throws GLException
	{
		checkForGLError(con.getGL1());
		
	}
	
	public static void checkForGLError(IGL1 gl) throws GLException
	{
		int err = gl.glGetError();
		
		if (err == GLConst.GL_NO_ERROR)
		{
			return;
		}
		
		throw new GLException(err);//GLU.gluErrorString(err) TODO Fix after conversion to enums.
	}
	
	public static Buffer<Float> mixColors(Color a, Color b)
	{
		Buffer<Float> ret = new Buffer<Float>();
		
		for (EnumColorFilter col : EnumColorFilter.values())
		{
			ret.add((a.getColorFloat(col) + b.getColorFloat(col)) % 1f);
			
		}
		
		ret.rewind();
		
		return ret;
	}
	
	public static int[] createVBOs(int count)
	{
		IntBuffer buf = BufferHelper.createIntBuffer(count);
		
		gl1().glGenBuffers(buf);
		
		buf.rewind();
		
		int[] ret = new int[count];
		
		for (int c = 0; c < count; c++)
		{
			ret[c] = buf.get();
			
		}
		
		return ret;
	}
	
	public static Material[] createMaterials()
	{
		return new Material[RenderConst.MATERIAL_CAP];
	}
	
	public static Shader[] createShaders()
	{
		return new Shader[GLEnumShader.values().length];
	}
	
}
