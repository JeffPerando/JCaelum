
package elusivehawk.engine.render;

import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.glu.GLU;
import elusivehawk.engine.core.EnumRenderMode;
import elusivehawk.engine.util.GameLog;
import elusivehawk.engine.util.TextParser;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public final class RenderHelper
{
	private static final List<Model> MODELS = new ArrayList<Model>();
	private static final List<GLProgram> PROGRAMS = new ArrayList<GLProgram>();
	
	private RenderHelper(){}
	
	public static IntBuffer processGifFile(File gif, EnumRenderMode mode)
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
				
				IntBuffer ret = BufferUtils.createIntBuffer(max);
				
				for (int c = 0; c < max; c++)
				{
					BufferedImage img = reader.read(c);
					
					ret.put(processImage(img, mode, false));
					
				}
				
				return ret;
			}
			catch (Exception e)
			{
				GameLog.error(e);
				
			}
			
		}
		
		return null;
		
	}
	
	public static int processImage(BufferedImage img, int x, int y, int w, int h, EnumRenderMode mode, boolean alpha)
	{
		return processImage(img.getSubimage(x, y, w, h), mode, alpha);
	}
	
	public static int processImage(BufferedImage img, EnumRenderMode mode, boolean alpha)
	{
		return processImage(readImage(img, alpha), img.getWidth(), img.getHeight(), mode, alpha);
	}
	
	public static int processImage(ByteBuffer buf, int w, int h, EnumRenderMode mode, boolean alpha)
	{
		if (!mode.isValidImageMode())
		{
			return 0;
		}
		
		int glId = GL.glGenTextures();
		
		GL.glBindTexture(mode.is3D() ? GL.GL_TEXTURE_3D : GL.GL_TEXTURE_2D, glId);
		GL.glPixelStorei(GL.GL_UNPACK_ALIGNMENT, 1);
		
		if (mode.is3D())
		{
			GL.glTexParameteri(GL.GL_TEXTURE_3D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_LINEAR);
			GL.glTexParameteri(GL.GL_TEXTURE_3D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR);
			
			GL.glTexImage3D(GL.GL_TEXTURE_3D, 0, GL.GL_RGBA8, w, h, 0, 0, GL.GL_RGBA, GL.GL_UNSIGNED_BYTE, buf);
			
		}
		else
		{
			GL.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_NEAREST);
			GL.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_NEAREST);
			
			GL.glTexImage2D(GL.GL_TEXTURE_2D, 0, GL.GL_RGBA8, w, h, 0, GL.GL_RGBA, GL.GL_UNSIGNED_BYTE, buf);
			
		}
		
		GL.glBindTexture(mode.is3D() ? GL.GL_TEXTURE_3D : GL.GL_TEXTURE_2D, 0);
		
		return glId;
	}
	
	public static ByteBuffer readImage(BufferedImage img, boolean alpha)
	{
		ByteBuffer buf = BufferUtils.createByteBuffer(img.getHeight() * img.getWidth() * (alpha ? 4 : 3));
		
		for (int x = 0; x < img.getWidth(); ++x)
		{
			for (int y = 0; y < img.getHeight(); ++y)
			{
				Color col = new ColorRGBA(new ColorARGB(img.getRGB(x, y)));
				
				col.loadIntoBuffer(buf, alpha);
				
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
		catch (RenderException e)
		{
			GameLog.error(e);
			
		}
		
		return 0;
	}
	
	public static synchronized BufferedImage captureScreen()
	{
		ByteBuffer buf = BufferUtils.createByteBuffer(Display.getHeight() * Display.getWidth() * 4);
		
		GL.glReadPixels(0, 0, Display.getWidth(), Display.getHeight(), GL.GL_RGBA, GL.GL_BYTE, buf);
		
		BufferedImage ret = new BufferedImage(Display.getWidth(), Display.getHeight(), BufferedImage.TYPE_INT_ARGB);
		
		for (int x = 0; x < ret.getWidth(); x++)
		{
			for (int y = 0; y < ret.getHeight(); y++)
			{
				ret.setRGB(x, y, new ColorARGB(new ColorRGBA(buf)).color);
				
			}
			
		}
		
		return ret;
	}
	
	public static int registerModel(Model m)
	{
		if (!MODELS.isEmpty())
		{
			for (Model m0 : MODELS)
			{
				if (m == m0)
				{
					return -1;
				}
				
			}
			
		}
		
		MODELS.add(m);
		
		return MODELS.size() - 1;
	}
	
	public static Model getModel(int id)
	{
		return id >= 0 ? MODELS.get(id) : null;
	}
	
	public static void registerProgram(GLProgram p)
	{
		PROGRAMS.add(p);
		
	}
	
	public static void deletePrograms()
	{
		if (!PROGRAMS.isEmpty())
		{
			return;
		}
		
		for (GLProgram p : PROGRAMS)
		{
			p.delete();
			
		}
		
	}
	
	public static int getPointCount(int gl)
	{
		switch (gl)
		{
			case GL.GL_POINT: return 1;
			case GL.GL_LINE: return 2;
			case GL.GL_TRIANGLES : return 3;
			case GL.GL_QUADS: GameLog.warn("Someone is using outdated OpenGL techniques!"); return 4;
			case GL.GL_TRIANGLE_FAN: return 5;
			default: return 0;
		}
		
	}
	
	public static void checkForGLError()
	{
		int err = GL.glGetError();
		
		if (err != GL.GL_NO_ERROR)
		{
			throw new RenderException("Caught OpenGL error: " + GLU.gluErrorString(err));
			
		}
		
	}
	
	public static FloatBuffer mixColors(Color a, Color b)
	{
		FloatBuffer ret = BufferUtils.createFloatBuffer(4);
		
		for (EnumColor col : EnumColor.values())
		{
			ret.put((a.getColorFloat(col) + b.getColorFloat(col)) % 1f);
			
		}
		
		ret.flip();
		
		return ret;
	}
	
	public static IntBuffer createVBOs(int count)
	{
		IntBuffer ret = BufferUtils.createIntBuffer(count);
		GL.glGenBuffers(ret);
		ret.rewind(); //Just a safety precaution.
		return ret;
	}
	
}
