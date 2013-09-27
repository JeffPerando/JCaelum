
package elusivehawk.engine.render;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.Display;

/**
 * 
 * The new system for 2D rendering.
 * 
 * @author Elusivehawk
 */
public class ImageScreen implements Iterable<ImageData>
{
	public final GLProgram p;
	public final VertexBufferObject vbo, indices;
	private final FloatBuffer buf;
	private final IntBuffer indiceBuf;
	private final List<ImageData> data = new ArrayList<ImageData>();
	
	public ImageScreen(int maxImgs)
	{
		this(new GLProgram(), maxImgs);
		
	}
	
	public ImageScreen(GLProgram program, int maxImgs)
	{
		p = program;
		
		buf = BufferUtils.createFloatBuffer(maxImgs * 32);
		indiceBuf = BufferUtils.createIntBuffer(maxImgs * 6);
		
		vbo = new VertexBufferObject();
		indices = new VertexBufferObject(GL.GL_ELEMENT_ARRAY_BUFFER);
		
		p.attachVBOs(vbo, indices);
		
	}
	
	public int addImage(ImageData info, int xPos, int yPos)
	{
		if (this.buf.limit() - this.buf.capacity() == 0)
		{
			throw new ArrayIndexOutOfBoundsException("Image limit hit!");
		}
		
		int position = this.data.size();
		
		info.pos.one = xPos;
		info.pos.two = yPos;
		
		int x = info.pos.one;
		int y = info.pos.two;
		int w = info.width;
		int h = info.height;
		
		FloatBuffer img = BufferUtils.createFloatBuffer(32);
		
		float a = x / Display.getWidth();
		float b = y / Display.getHeight();
		float c = (x + w) / Display.getWidth();
		float d = (y + h) / Display.getHeight();
		
		img.put(a).put(b).put(0).put(1f);
		info.mgr.getColor(0).store(img);
		
		img.put(c).put(b).put(0).put(1f);
		info.mgr.getColor(1).store(img);
		
		img.put(a).put(d).put(0).put(1f);
		info.mgr.getColor(2).store(img);
		
		img.put(c).put(d).put(0).put(1f);
		info.mgr.getColor(3).store(img);
		
		img.flip();
		
		this.buf.position(position * 32);
		this.buf.put(img);
		
		IntBuffer ind = BufferUtils.createIntBuffer(6);
		
		int indiceOff = position * 4;
		
		ind.put(indiceOff).put(indiceOff + 1).put(indiceOff + 2);
		ind.put(indiceOff + 1).put(indiceOff + 2).put(indiceOff + 3);
		
		ind.flip();
		
		this.indiceBuf.position(position * 6);
		this.indiceBuf.put(ind);
		
		this.data.add(info);
		
		return position;
	}
	
	@Deprecated
	public void removeImg(int index)
	{
		
	}
	
	public void updateImages()
	{
		for (int c = 0; c < this.data.size(); c++)
		{
			ImageData info = this.data.get(c);
			IExtraImageData mgr = info.mgr;
			
			if (mgr.updateImagePosition(c, info))
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
