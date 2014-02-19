
package com.elusivehawk.engine.render2;

import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import com.elusivehawk.engine.core.CaelumEngine;
import com.elusivehawk.engine.core.EnumLogType;
import com.elusivehawk.engine.render.opengl.GLConst;
import com.elusivehawk.engine.render.opengl.IGLCleanable;
import com.elusivehawk.engine.util.Buffer;
import com.elusivehawk.engine.util.BufferHelper;
import com.elusivehawk.engine.util.FileHelper;
import com.elusivehawk.engine.util.TextParser;
import com.elusivehawk.engine.util.io.ByteBuf;

/**
 * 
 * Helps with rendering stuff.
 * 
 * @author Elusivehawk
 */
public final class RenderHelper
{
	public static final int VERTEX_SHADER_3D = loadShader(FileHelper.createFile("/vertex.glsl"), GLConst.GL_VERTEX_SHADER);
	public static final int FRAGMENT_SHADER_3D = loadShader(FileHelper.createFile("/fragment.glsl"), GLConst.GL_FRAGMENT_SHADER);
	
	public static final int VERTEX_OFFSET = 0;
	public static final int COLOR_OFFSET = 3;
	public static final int TEXCOORD_OFFSET = 7;
	
	private static final List<IGLCleanable> OBJECTS = new ArrayList<IGLCleanable>();
	
	private RenderHelper(){}
	
	public static void register(IGLCleanable gl)
	{
		OBJECTS.add(gl);
		
	}
	
	public static void cleanup()
	{
		if (!OBJECTS.isEmpty())
		{
			return;
		}
		
		for (IGLCleanable gl : OBJECTS)
		{
			gl.glDelete();
			
		}
		
		OBJECTS.clear();
		
	}
	
	public static Buffer<Integer> processGifFile(File gif, EnumRenderMode mode, EnumColorFormat format)
	{
		if (!mode.isValidImageMode())
		{
			return null;
		}
		
		if (gif.getName().endsWith(".gif"))
		{
			try
			{
				ImageReader reader = ImageIO.getImageReadersByFormatName("gif").next();
				ImageInputStream in = ImageIO.createImageInputStream(gif);
				reader.setInput(in, false);
				
				int max = reader.getNumImages(true);
				
				Buffer<Integer> ret = new Buffer<Integer>();
				
				for (int c = 0; c < max; c++)
				{
					LegibleBufferedImage img = new LegibleBufferedImage(reader.read(c));
					
					ret.add(processImage(img, mode, format));
					
				}
				
				in.close();
				
				return ret;
			}
			catch (Exception e)
			{
				CaelumEngine.instance().getLog().log(EnumLogType.ERROR, null, e);
				
			}
			
		}
		
		return null;
	}
	
	public static int processImage(File img, EnumRenderMode mode, EnumColorFormat format)
	{
		ILegibleImage leimg = null;
		
		try
		{
			leimg = new LegibleBufferedImage(ImageIO.read(img));
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
			
		}
		
		return leimg == null ? 0 : processImage(img, mode, format);
	}
	
	public static int processImage(ILegibleImage img, EnumRenderMode mode, EnumColorFormat format)
	{
		return processImage(readImage(img, format), img.getWidth(), img.getHeight(), mode);
	}
	
	public static int processImage(ByteBuffer buf, int w, int h, EnumRenderMode mode)
	{
		if (!isContextCurrent() || !mode.isValidImageMode())
		{
			return 0;
		}
		
		int glId = GL.glGenTextures();
		
		GL.glActiveTexture(glId);
		GL.glPixelStorei(GLConst.GL_UNPACK_ALIGNMENT, 1);
		
		GL.glTexParameteri(mode.getOpenGLMode(), GLConst.GL_TEXTURE_MIN_FILTER, GLConst.GL_LINEAR);
		GL.glTexParameteri(mode.getOpenGLMode(), GLConst.GL_TEXTURE_MAG_FILTER, GLConst.GL_LINEAR);
		
		if (mode.is3D())
		{
			GL.glTexImage3D(GLConst.GL_TEXTURE_3D, 0, GLConst.GL_RGBA8, w, h, 0, 0, GLConst.GL_RGBA, GLConst.GL_UNSIGNED_BYTE, buf);
			
		}
		else
		{
			GL.glTexImage2D(GLConst.GL_TEXTURE_2D, 0, GLConst.GL_RGBA8, w, h, 0, GLConst.GL_RGBA, GLConst.GL_UNSIGNED_BYTE, buf);
			
		}
		
		GL.glActiveTexture(0);
		
		return glId;
	}
	
	public static ByteBuffer readImage(ILegibleImage img, EnumColorFormat format)
	{
		ByteBuffer buf = BufferHelper.createByteBuffer(img.getHeight() * img.getWidth() * 4);
		Color col = new Color(format);
		
		for (int x = 0; x < img.getWidth(); ++x)
		{
			for (int y = 0; y < img.getHeight(); ++y)
			{
				col.setColor(img.getPixel(x, y));
				
				for (EnumColorFilter filter : format.filters)
				{
					buf.put(col.getColor(filter));
					
				}
				
			}
			
		}
		
		buf.flip();
		
		return buf;
	}
	
	public static int loadShader(File shader, int type)
	{
		if (!shader.exists() || !shader.getName().endsWith(".glsl"))
		{
			return 0;
		}
		
		String program = TextParser.concat(TextParser.read(shader), "\n", "", null);
		
		int id = GL.glCreateShader(type);
		GL.glShaderSource(id, program);
		GL.glCompileShader(type);
		
		try
		{
			checkForGLError();
			
			return id;
		}
		catch (Exception e)
		{
			CaelumEngine.instance().getLog().log(EnumLogType.ERROR, null, e);
			
		}
		
		return 0;
	}
	
	@Deprecated
	public static synchronized BufferedImage captureScreen(IDisplay win)
	{
		ByteBuffer buf = BufferHelper.createByteBuffer(win.getHeight() * win.getWidth() * 4);
		
		GL.glReadPixels(0, 0, win.getWidth(), win.getHeight(), GLConst.GL_RGBA, GLConst.GL_BYTE, buf);
		
		BufferedImage ret = new BufferedImage(win.getWidth(), win.getHeight(), BufferedImage.TYPE_INT_ARGB);
		ByteBuf io = new ByteBuf(buf);
		
		for (int x = 0; x < ret.getWidth(); x++)
		{
			for (int y = 0; y < ret.getHeight(); y++)
			{
				ret.setRGB(x, y, EnumColorFormat.ARGB.convert(new Color(EnumColorFormat.RGBA, io)).getColor());
				
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
			default: return 0;
		}
		
	}
	
	public static void checkForGLError() throws RuntimeException
	{
		if (!isContextCurrent())
		{
			return;
		}
		
		int err = GL.glGetError();
		
		if (err == GLConst.GL_NO_ERROR)
		{
			return;
		}
		
		throw new RuntimeException("Caught OpenGL error: " + GLU.gluErrorString(err));
		
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
		int[] ret = new int[count];
		
		for (int c = 0; c < count; c++)
		{
			ret[c] = GL.glGenBuffers();
			
		}
		
		return ret;
	}
	
}
