
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
	private final FloatBuffer buf;
	private final List<ImageData> data = new ArrayList<ImageData>();
	
	public ImageScreen(int initialImgCount)
	{
		buf = BufferUtils.createFloatBuffer(initialImgCount * 24);
		
	}
	
	public int addImage(ITexture tex, int x, int y, int w, int h)
	{
		this.buf.put(this.generateImgBuffer(x, y, w, h));
		
		ImageData info = new ImageData(tex);
		
		info.pos.x = x;
		info.pos.y = y;
		info.width = w;
		info.height = h;
		
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
		
		if (this.buf.remaining() == 0)
		{
			this.buf.limit(this.buf.limit() + 24);
			
		}
		
		this.buf.put(a).put(b).put(0).put(1);
		this.buf.put(c).put(b).put(0).put(1);
		this.buf.put(a).put(d).put(0).put(1);
		
		this.buf.put(c).put(d).put(0).put(1);
		this.buf.put(c).put(b).put(0).put(1);
		this.buf.put(a).put(d).put(0).put(1);
		
		ret.flip();
		
		return ret;
	}
	
	public void moveImg(int index, Vector2f pos)
	{
		ImageData info = this.data.get(index);
		
		if (info != null)
		{
			info.pos = pos;
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
