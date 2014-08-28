
package com.elusivehawk.engine.render;

import java.util.UUID;
import com.elusivehawk.engine.assets.Asset;
import com.elusivehawk.engine.assets.IAssetReceiver;
import com.elusivehawk.engine.render.opengl.GLEnumBufferTarget;
import com.elusivehawk.engine.render.opengl.GLEnumDataType;
import com.elusivehawk.engine.render.opengl.GLEnumDataUsage;
import com.elusivehawk.engine.render.opengl.GLProgram;
import com.elusivehawk.engine.render.opengl.VertexBuffer;
import com.elusivehawk.util.FloatBufferer;
import com.elusivehawk.util.IDirty;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class Canvas implements IFilterable, IRenderable, IAssetReceiver, IDirty
{
	private final GLProgram p = new GLProgram();
	private final VertexBuffer floatbuf;
	private final VertexBuffer indbuf;
	
	private final FloatBufferer buffer;
	private final int imgLimit;
	
	private SubCanvas sub = null;
	private int images = 0;
	private volatile boolean dirty = true;
	
	private Filters filters = null;
	
	@SuppressWarnings("unqualified-field-access")
	public Canvas(int imgs)
	{
		imgLimit = imgs;
		buffer = new FloatBufferer(4, imgLimit * 6);
		
		floatbuf = new VertexBuffer(GLEnumBufferTarget.GL_ARRAY_BUFFER, GLEnumDataUsage.GL_DYNAMIC_DRAW, GLEnumDataType.GL_FLOAT, buffer.getBuffer());
		indbuf = new VertexBuffer(GLEnumBufferTarget.GL_ELEMENT_ARRAY_BUFFER, GLEnumDataUsage.GL_DYNAMIC_DRAW, GLEnumDataType.GL_INT, buffer.getIndices());
		
		p.attachVBO(floatbuf, 0, 1);
		p.attachVBO(indbuf, null);
		
	}
	
	@Override
	public boolean isDirty()
	{
		return this.dirty || (this.filters != null && this.filters.isDirty());
	}
	
	@Override
	public void setIsDirty(boolean b)
	{
		this.dirty = b;
		
		if (this.filters != null)
		{
			this.filters.setIsDirty(b);
			
		}
		
	}
	
	@Override
	public void onAssetLoaded(Asset a)
	{
		this.p.onAssetLoaded(a);
		
	}
	
	@Override
	public void render(RenderContext rcon, double delta) throws RenderException
	{
		if (!this.p.bind(rcon))
		{
			return;
		}
		
		if (this.isDirty())
		{
			if (this.filters != null)
			{
				this.filters.filter(rcon, this.p);
				
			}
			
			this.setIsDirty(false);
			
		}
		
		//TODO Finish
		
		this.p.unbind(rcon);
		
	}
	
	@Override
	public int addFilter(UUID type, IFilter f)
	{
		return 0;
	}
	
	@Override
	public void removeFilter(UUID type, IFilter f)
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void removeFilter(UUID type, int i)
	{
		// TODO Auto-generated method stub
		
	}
	
	public void createSubCanvas(float xmin, float ymin, float xmax, float ymax)
	{
		if (this.sub == null)
		{
			this.sub = new SubCanvas(xmin, ymin, xmax, ymax);
			
		}
		else
		{
			this.sub.createSubCanvas(xmin, ymin, xmax, ymax);
			
		}
		
	}
	
	public boolean destroySubCanvas()
	{
		if (this.sub == null)
		{
			return false;
		}
		
		if (!this.sub.destroySubCanvas())
		{
			this.sub = null;
			
		}
		
		return true;
	}
	
	public void drawImage(float x, float y, float w, float h, Icon icon)
	{
		if (this.images == this.imgLimit)
		{
			return;
		}
		
		if (this.sub != null)
		{
			x = this.sub.interpolateX(x);
			y = this.sub.interpolateY(y);
			w = this.sub.interpolateW(w);
			h = this.sub.interpolateH(h);
			
		}
		
		float[][] img = new float[][]
				{{x, y, 0, 0},
				{w, y, 0, 0},
				{x, h, 0, 0},
				{w, h, 0, 0}};
		
		if (icon != null)
		{
			for (int c = 0; c < 4; c++)
			{
				for (int i = 0; i < 2; i++)
				{
					img[c][i + 2] = icon.getCorner(c)[i];
					
				}
				
			}
			
		}
		
		int[] ind = this.buffer.getOrCreateIndices(img);
		
		this.buffer.addIndex(ind[0]);
		this.buffer.addIndex(ind[1]);
		this.buffer.addIndex(ind[2]);
		
		this.buffer.addIndex(ind[1]);
		this.buffer.addIndex(ind[2]);
		this.buffer.addIndex(ind[3]);
		
		this.images++;
		
	}
	
	public void clear()
	{
		this.buffer.reset();
		
	}
	
}
