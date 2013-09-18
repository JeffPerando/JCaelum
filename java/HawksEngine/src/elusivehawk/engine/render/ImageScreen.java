
package elusivehawk.engine.render;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.Display;
import elusivehawk.engine.math.Vector2f;

/**
 * 
 * The new system for 2D rendering.
 * 
 * @author Elusivehawk
 */
public class ImageScreen implements Iterable<ImageData>
{
	public final GLProgram p;
	public final VertexBufferObject vbo;
	private final FloatBuffer buf;
	private final List<ImageData> data = new ArrayList<ImageData>();
	
	public ImageScreen(int maxImgs)
	{
		this(new GLProgram(), maxImgs);
		
	}
	
	public ImageScreen(GLProgram program, int maxImgs)
	{
		p = program;
		buf = BufferUtils.createFloatBuffer(maxImgs * 24);
		
		vbo = new VertexBufferObject();
		
		p.attachVBOs(vbo);
		
	}
	
	public int addImage(ITexture tex, int x, int y, int w, int h)
	{
		if (this.buf.limit() - this.buf.capacity() == 0)
		{
			throw new ArrayIndexOutOfBoundsException("Image limit hit!");
		}
		
		this.buf.put(this.generateImgBuffer(x, y, w, h));
		
		ImageData info = new ImageData(tex, w, h);
		
		info.pos.x = x;
		info.pos.y = y;
		
		this.data.add(info);
		
		return this.data.size() - 1;
	}
	
	private FloatBuffer generateImgBuffer(int x, int y, int w, int h)
	{
		FloatBuffer ret = BufferUtils.createFloatBuffer(24);
		
		float a = x / Display.getWidth();
		float b = y / Display.getHeight();
		float c = (x + w) / Display.getWidth();
		float d = (y + h) / Display.getHeight();
		
		ret.put(a).put(b).put(0).put(1);
		ret.put(c).put(b).put(0).put(1);
		ret.put(a).put(d).put(0).put(1);
		
		ret.put(c).put(d).put(0).put(1);
		ret.put(c).put(b).put(0).put(1);
		ret.put(a).put(d).put(0).put(1);
		
		ret.flip();
		
		return ret;
	}
	
	public void moveImg(int index, Vector2f pos)
	{
		ImageData info = this.data.get(index);
		
		if (info != null)
		{
			info.pos.x = pos.x;
			info.pos.y = pos.y;
			info.requiresUpdating = true;
			
		}
		
	}
	
	public void removeImg(int index)
	{
		
	}
	
	public int getTexture(int index)
	{
		return this.data.get(index).tex.getTexture();
	}
	
	public void updateImages()
	{
		for (int c = 0; c < this.data.size(); c++)
		{
			ImageData info = this.data.get(c);
			
			if (info.requiresUpdating)
			{
				
				
			}
			
		}
		
	}
	
	@Override
	public Iterator<ImageData> iterator()
	{
		return this.data.iterator();
	}
	
}
