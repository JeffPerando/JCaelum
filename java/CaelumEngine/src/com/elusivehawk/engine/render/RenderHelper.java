
package com.elusivehawk.engine.render;

import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.List;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import com.elusivehawk.engine.core.CaelumEngine;
import com.elusivehawk.engine.core.EnumLogType;
import com.elusivehawk.engine.render.opengl.GLConst;
import com.elusivehawk.engine.render.opengl.IGL1;
import com.elusivehawk.engine.render.opengl.IGL2;
import com.elusivehawk.engine.render.opengl.IGL3;
import com.elusivehawk.engine.util.Buffer;
import com.elusivehawk.engine.util.BufferHelper;
import com.elusivehawk.engine.util.SimpleList;
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
	
	private RenderHelper(){}
	
	public static IGL1 gl1()
	{
		return CaelumEngine.renderContext().getGL1();
	}
	
	public static IGL2 gl2()
	{
		return CaelumEngine.renderContext().getGL2();
	}
	
	public static IGL3 gl3()
	{
		return CaelumEngine.renderContext().getGL3();
	}
	
	/*public static IGL4 gl4()
	{
		return CaelumEngine.renderContext().getGL4();
	}*/
	
	public static List<ILegibleImage> readGifFile(File gif)
	{
		if (gif.getName().endsWith(".gif"))
		{
			try
			{
				ImageReader reader = ImageIO.getImageReadersByFormatName("gif").next();
				ImageInputStream in = ImageIO.createImageInputStream(gif);
				reader.setInput(in, false);
				
				int max = reader.getNumImages(true);
				
				List<ILegibleImage> ret = SimpleList.newList(max);
				
				for (int c = 0; c < max; c++)
				{
					ret.add(new LegibleBufferedImage(reader.read(c)));
					
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
	
	public static int processImage(File img)
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
		
		return leimg == null ? 0 : processImage(leimg);
	}
	
	public static int processImage(ILegibleImage img)
	{
		return processImage(img, img.getFormat());
	}
	
	public static int processImage(ILegibleImage img, EnumColorFormat format)
	{
		return processImage(readImage(img, format), img.getWidth(), img.getHeight());
	}
	
	public static int processImage(IntBuffer buf, int w, int h)
	{
		IGL1 gl1 = gl1();
		
		int glId = gl1.glGenTextures();
		
		gl1.glActiveTexture(glId);
		gl1.glPixelStorei(GLConst.GL_UNPACK_ALIGNMENT, 1);
		
		gl1.glTexParameterx(GLConst.GL_TEXTURE_2D, GLConst.GL_TEXTURE_MIN_FILTER, GLConst.GL_LINEAR);
		gl1.glTexParameterx(GLConst.GL_TEXTURE_2D, GLConst.GL_TEXTURE_MAG_FILTER, GLConst.GL_LINEAR);
		
		gl1.glTexImage2D(GLConst.GL_TEXTURE_2D, 0, GLConst.GL_RGBA8, w, h, 0, GLConst.GL_RGBA, GLConst.GL_UNSIGNED_BYTE, buf);
		
		gl1.glActiveTexture(0);
		
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
	
	public static int loadShader(File file, int type)
	{
		return loadShader(TextParser.read(file), type);
	}
	
	public static int loadShader(List<String> s, int type)
	{
		if (s == null || s.isEmpty())
		{
			return 0;
		}
		
		String program = TextParser.concat(s, "\n", "", null);
		
		if (program == null)
		{
			return 0;
		}
		
		RenderContext context = CaelumEngine.renderContext();
		
		IGL2 gl2 = context.getGL2();
		int id = gl2.glCreateShader(type);
		gl2.glShaderSource(id, program);
		gl2.glCompileShader(type);
		
		try
		{
			checkForGLError(context);
			
		}
		catch (Exception e)
		{
			CaelumEngine.log().log(EnumLogType.ERROR, null, e);
			
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
		checkForGLError(CaelumEngine.renderContext());
		
	}
	
	public static void checkForGLError(RenderContext context) throws RuntimeException
	{
		int err = context.getGL1().glGetError();
		
		if (err == GLConst.GL_NO_ERROR)
		{
			return;
		}
		
		throw new RuntimeException(String.format("Caught OpenGL error: %s",err)/*GLU.gluErrorString(err)*/);//TODO Fix after conversion to enums.
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
	
}
