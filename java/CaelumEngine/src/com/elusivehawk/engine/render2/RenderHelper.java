
package com.elusivehawk.engine.render2;

import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
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
	public static final int VERTEX_OFFSET = 0;
	public static final int COLOR_OFFSET = 3;
	public static final int TEXCOORD_OFFSET = 7;
	
	private static final List<IGLCleanable> OBJECTS = new ArrayList<IGLCleanable>();
	
	private RenderHelper(){}
	
	public static void register(IGLCleanable gl)
	{
		OBJECTS.add(gl);
		
	}
	
	public static void cleanup(RenderContext context)
	{
		if (!OBJECTS.isEmpty())
		{
			return;
		}
		
		for (IGLCleanable gl : OBJECTS)
		{
			gl.glDelete(context);
			
		}
		
		OBJECTS.clear();
		
	}
	
	public static Buffer<Integer> processGifFile(File gif, RenderContext context, EnumRenderMode mode, EnumColorFormat format)
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
					
					ret.add(processImage(img, mode, format, context));
					
				}
				
				in.close();
				
				return ret;
			}
			catch (Exception e)
			{
				CaelumEngine.log().log(EnumLogType.ERROR, null, e);
				
			}
			
		}
		
		return null;
	}
	
	public static int processImage(File img, EnumRenderMode mode, EnumColorFormat format, RenderContext context)
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
		
		return leimg == null ? 0 : processImage(leimg, mode, format, context);
	}
	
	public static int processImage(ILegibleImage img, EnumRenderMode mode, EnumColorFormat format, RenderContext context)
	{
		return processImage(readImage(img, format), img.getWidth(), img.getHeight(), mode, context);
	}
	
	public static int processImage(IntBuffer buf, int w, int h, EnumRenderMode mode, RenderContext context)
	{
		if (!mode.isValidImageMode())
		{
			return 0;
		}
		
		int glId = context.getGL1().glGenTextures();
		
		context.getGL1().glActiveTexture(glId);
		context.getGL1().glPixelStorei(GLConst.GL_UNPACK_ALIGNMENT, 1);
		
		context.getGL1().glTexParameterx(mode.getOpenGLMode(), GLConst.GL_TEXTURE_MIN_FILTER, GLConst.GL_LINEAR);
		context.getGL1().glTexParameterx(mode.getOpenGLMode(), GLConst.GL_TEXTURE_MAG_FILTER, GLConst.GL_LINEAR);
		
		if (mode.is3D())
		{
			context.getGL1().glTexImage3D(GLConst.GL_TEXTURE_3D, 0, GLConst.GL_RGBA8, w, h, 0, 0, GLConst.GL_RGBA, GLConst.GL_UNSIGNED_BYTE, buf);
			
		}
		else
		{
			context.getGL1().glTexImage2D(GLConst.GL_TEXTURE_2D, 0, GLConst.GL_RGBA8, w, h, 0, GLConst.GL_RGBA, GLConst.GL_UNSIGNED_BYTE, buf);
			
		}
		
		context.getGL1().glActiveTexture(0);
		
		return glId;
	}
	
	public static IntBuffer readImage(ILegibleImage img, EnumColorFormat format)
	{
		IntBuffer buf = BufferHelper.createIntBuffer(img.getHeight() * img.getWidth());
		Color col = new Color(img.getFormat());
		
		for (int x = 0; x < img.getWidth(); ++x)
		{
			for (int y = 0; y < img.getHeight(); ++y)
			{
				col.setColor(img.getPixel(x, y));
				buf.put(format.convert(col).getColor());
				
			}
			
		}
		
		buf.flip();
		
		return buf;
	}
	
	public static int loadShader(File shader, int type, RenderContext context)
	{
		if (!shader.exists() || !shader.getName().endsWith(".glsl"))
		{
			return 0;
		}
		
		String program = TextParser.concat(TextParser.read(shader), "\n", "", null);
		
		int id = context.getGL2().glCreateShader(type);
		context.getGL2().glShaderSource(id, program);
		context.getGL2().glCompileShader(type);
		
		try
		{
			checkForGLError(context);
			
			return id;
		}
		catch (Exception e)
		{
			CaelumEngine.log().log(EnumLogType.ERROR, null, e);
			
		}
		
		return 0;
	}
	
	@Deprecated
	public static synchronized BufferedImage captureScreen(IDisplay win, RenderContext context)
	{
		ByteBuffer buf = BufferHelper.createByteBuffer(win.getHeight() * win.getWidth() * 4);
		
		context.getGL1().glReadPixels(0, 0, win.getWidth(), win.getHeight(), GLConst.GL_RGBA, GLConst.GL_BYTE, buf);
		
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
	
	public static void checkForGLError(RenderContext context) throws RuntimeException
	{
		int err = context.getGL1().glGetError();
		
		if (err == GLConst.GL_NO_ERROR)
		{
			return;
		}
		
		throw new RuntimeException("Caught OpenGL error: " + err/*GLU.gluErrorString(err)*/);//TODO Fix after conversion to enums.
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
	
	public static int[] createVBOs(int count, RenderContext context)
	{
		IntBuffer buf = BufferHelper.createIntBuffer(count);
		
		context.getGL1().glGenBuffers(buf);
		
		buf.rewind();
		
		int[] ret = new int[count];
		
		for (int c = 0; c < count; c++)
		{
			ret[c] = buf.get();
			
		}
		
		return ret;
	}
	
}
