
package com.elusivehawk.engine.render;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.Display;
import com.elusivehawk.engine.util.BufferHelper;

/**
 * 
 * The new system for 2D rendering.
 * 
 * @author Elusivehawk
 */
public class ImageScreen
{
	public static final int IMG_FLOAT_COUNT = 36;
	
	protected ITexture tex = null;
	protected final GLProgram p;
	protected final VertexBufferObject vbo, indices;
	protected final FloatBuffer buf;
	protected final IntBuffer indiceBuf;
	protected final List<ImageData> data = new ArrayList<ImageData>();
	
	public ImageScreen(int maxImgs, ITexture texture)
	{
		this(new GLProgram(), texture, maxImgs);
		
	}
	
	public ImageScreen(GLProgram program, ITexture texture, int maxImgs)
	{
		p = program;
		tex = texture;
		
		buf = BufferUtils.createFloatBuffer(maxImgs * IMG_FLOAT_COUNT);
		indiceBuf = BufferUtils.createIntBuffer(maxImgs * 6);
		
		vbo = new VertexBufferObject(GL.GL_ARRAY_BUFFER);
		indices = new VertexBufferObject(GL.GL_ELEMENT_ARRAY_BUFFER);
		
		p.attachVBOs(vbo, indices);
		p.createAttribPointers(buf);
		
	}
	
	public int addImage(ImageData info, int xPos, int yPos)
	{
		if (this.getImgCount() == this.buf.limit() / IMG_FLOAT_COUNT)
		{
			throw new ArrayIndexOutOfBoundsException("Image limit hit!");
		}
		
		int position = this.data.size();
		
		info.pos.one = xPos;
		info.pos.two = yPos;
		
		FloatBuffer img = this.generateImgBuffer(info);
		
		this.buf.position(position * IMG_FLOAT_COUNT);
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
	
	public void removeImg(int index)
	{
		if (index >= this.getImgCount())
		{
			return;
		}
		
		if (index + 1 != this.getImgCount())
		{
			FloatBuffer remains = BufferHelper.makeFloatBuffer(this.buf, (index + 1) * IMG_FLOAT_COUNT, (index - this.getImgCount()) * IMG_FLOAT_COUNT);
			
			this.buf.position(index * IMG_FLOAT_COUNT);
			this.buf.put(remains);
			
		}
		
		this.buf.position((this.getImgCount() - 1) * IMG_FLOAT_COUNT);
		
		for (int c = 0; c < IMG_FLOAT_COUNT; c++)
		{
			this.buf.put(0);
			
		}
		
		this.data.remove(index);
		
		FloatBuffer upd = BufferHelper.makeFloatBuffer(this.buf, index * IMG_FLOAT_COUNT, (this.getImgCount() + 1) * IMG_FLOAT_COUNT);
		
		this.vbo.updateVBO(upd, index * IMG_FLOAT_COUNT);
		
	}
	
	public void updateImages()
	{
		for (int c = 0; c < this.getImgCount(); c++)
		{
			ImageData info = this.getImg(c);
			IExtraImageData mgr = info.mgr;
			
			if (mgr.flaggedForDeletion())
			{
				this.removeImg(c);
				
				continue;
			}
			else if (mgr.updateImagePosition(c, info))
			{
				FloatBuffer img = this.generateImgBuffer(info);
				
				this.buf.position(c * IMG_FLOAT_COUNT);
				this.buf.put(img);
				
				this.vbo.updateVBO(img, c * IMG_FLOAT_COUNT);
				
			}
			
		}
		
	}
	
	public FloatBuffer generateImgBuffer(ImageData info)
	{
		FloatBuffer ret = BufferUtils.createFloatBuffer(IMG_FLOAT_COUNT);
		
		int x = info.pos.one;
		int y = info.pos.two;
		
		float a = x / Display.getWidth();
		float b = y / Display.getHeight();
		float c = (x + info.width) / Display.getWidth();
		float d = (y + info.height) / Display.getHeight();
		
		ret.put(a).put(b).put(0);
		info.mgr.getColor(0).store(ret);
		info.mgr.getTextureOffset(0).store(ret);
		
		ret.put(c).put(b).put(0);
		info.mgr.getColor(1).store(ret);
		info.mgr.getTextureOffset(1).store(ret);
		
		ret.put(a).put(d).put(0);
		info.mgr.getColor(2).store(ret);
		info.mgr.getTextureOffset(2).store(ret);
		
		ret.put(c).put(d).put(0);
		info.mgr.getColor(3).store(ret);
		info.mgr.getTextureOffset(3).store(ret);
		
		ret.flip();
		
		return ret;
	}
	
	public ImageData getImg(int index)
	{
		return this.data.get(index);
	}
	
	public int getImgCount()
	{
		return this.data.size();
	}

	public GLProgram getProgram()
	{
		return this.p;
	}
	
	public ITexture getTexture()
	{
		return this.tex;
	}
	
}
