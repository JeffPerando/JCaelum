
package com.elusivehawk.engine.render.two;

import com.elusivehawk.engine.render.Filterable;
import com.elusivehawk.engine.render.ILogicalRender;
import com.elusivehawk.engine.render.RenderContext;
import com.elusivehawk.engine.render.opengl.GLEnumBufferTarget;
import com.elusivehawk.engine.render.opengl.GLEnumDataUsage;
import com.elusivehawk.engine.render.opengl.GLEnumPolyType;
import com.elusivehawk.engine.render.opengl.GLProgram;
import com.elusivehawk.engine.render.opengl.VertexBuffer;
import com.elusivehawk.util.FloatBufferer;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class Canvas extends Filterable implements ILogicalRender
{
	private final GLProgram p = new GLProgram();
	private final VertexBuffer floatbuf = new VertexBuffer(GLEnumBufferTarget.GL_ARRAY_BUFFER, GLEnumDataUsage.GL_DYNAMIC_DRAW);
	private final VertexBuffer indbuf = new VertexBuffer(GLEnumBufferTarget.GL_ELEMENT_ARRAY_BUFFER, GLEnumDataUsage.GL_DYNAMIC_DRAW);
	
	private final FloatBufferer buffer;
	private final int imgLimit;
	
	private SubCanvas sub = null;
	private int images = 0;
	
	@SuppressWarnings("unqualified-field-access")
	public Canvas(int imgs)
	{
		imgLimit = imgs;
		buffer = new FloatBufferer(4, imgLimit * 6);
		
		p.attachVBO(floatbuf, 0, 1);
		p.attachVBO(indbuf, null);
		
	}
	
	@Override
	public boolean updateBeforeRender(RenderContext rcon, double delta)
	{
		return true;
	}
	
	@Override
	public GLProgram getProgram()
	{
		return this.p;
	}
	
	@Override
	public GLEnumPolyType getPolygonType()
	{
		return GLEnumPolyType.GL_TRIANGLES;
	}
	
	@Override
	public int getPolyCount()
	{
		return this.images * 2;
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
				{
					{x, y, icon.getCorner(0)[0], icon.getCorner(0)[1]},
					{w, y, icon.getCorner(1)[0], icon.getCorner(1)[1]},
					{x, h, icon.getCorner(2)[0], icon.getCorner(2)[1]},
					{w, h, icon.getCorner(3)[0], icon.getCorner(3)[2]}
				};
		
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
