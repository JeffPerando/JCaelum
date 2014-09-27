
package com.elusivehawk.engine.render;

import java.nio.FloatBuffer;
import java.util.List;
import com.elusivehawk.engine.render.opengl.GLConst;
import com.elusivehawk.engine.render.opengl.GLEnumBufferTarget;
import com.elusivehawk.engine.render.opengl.GLEnumDataType;
import com.elusivehawk.engine.render.opengl.GLEnumDataUsage;
import com.elusivehawk.engine.render.opengl.GLEnumPolyType;
import com.elusivehawk.engine.render.opengl.GLProgram;
import com.elusivehawk.engine.render.opengl.IGL1;
import com.elusivehawk.engine.render.opengl.VertexBuffer;
import com.elusivehawk.util.FloatBufferer;
import com.elusivehawk.util.IPopulator;
import com.elusivehawk.util.storage.IGettable;
import com.elusivehawk.util.storage.Tuple;
import com.google.common.collect.Lists;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class Canvas extends RenderableObj
{
	private final FloatBufferer buffer;
	
	private final VertexBuffer floatbuf;
	private final VertexBuffer indbuf;
	
	private List<IPopulator<Canvas>> populators = null;
	private SubCanvas sub = null;
	private int images = 0;
	
	public Canvas()
	{
		this(new GLProgram());
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public Canvas(GLProgram program)
	{
		super(program);
		
		buffer = new FloatBufferer(4, 12 * 6);
		
		floatbuf = new VertexBuffer(GLEnumBufferTarget.GL_ARRAY_BUFFER, GLEnumDataUsage.GL_DYNAMIC_DRAW, GLEnumDataType.GL_FLOAT, buffer.getBuffer());
		indbuf = new VertexBuffer(GLEnumBufferTarget.GL_ELEMENT_ARRAY_BUFFER, GLEnumDataUsage.GL_DYNAMIC_DRAW, GLEnumDataType.GL_INT, buffer.getIndices());
		
		vao.attachVBO(floatbuf, 0, 1);
		vao.attachVBO(indbuf, null);
		
	}
	
	@Override
	protected boolean initiate(RenderContext rcon)
	{
		return true;
	}
	
	@Override
	public boolean updateBeforeRender(RenderContext rcon, double delta)
	{
		if (!this.populators.isEmpty())
		{
			this.populators.forEach(((pop) -> {pop.populate(this);}));
			
		}
		
		if (this.buffer.resizedRecently())
		{
			this.floatbuf.uploadBuffer(this.buffer.getBuffer());
			this.indbuf.uploadBuffer(this.buffer.getIndices());
			
		}
		else if (this.buffer.isDirty())
		{
			List<Tuple<Integer, FloatBuffer>> diffs = this.buffer.getFloatDiffs();
			
			if (!diffs.isEmpty())
			{
				IGL1 gl1 = rcon.getGL1();
				
				for (Tuple<Integer, FloatBuffer> diff : diffs)
				{
					gl1.glBufferSubData(this.floatbuf.getId(), diff.one, GLConst.GL_FLOAT, diff.two);
					
				}
				
			}
			
		}
		
		return true;
	}
	
	@Override
	protected void doRender(RenderContext rcon, double delta) throws RenderException
	{
		rcon.getGL1().glDrawElements(GLEnumPolyType.GL_TRIANGLES, this.images * 2, GLConst.GL_UNSIGNED_INT, 0);
		
	}
	
	@Override
	public void postRender(RenderContext rcon)
	{
		this.buffer.rewind();
		this.buffer.setIsDirty(false);
		
	}
	
	public void createSubCanvas(float xmin, float ymin, float xmax, float ymax)
	{
		if (this.sub == null)
		{
			this.sub = new SubCanvas(xmin, ymin, xmax, ymax);
			
		}
		
	}
	
	public boolean destroySubCanvas()
	{
		if (this.sub == null)
		{
			return false;
		}
		
		this.sub = null;
		
		return true;
	}
	
	public void drawImage(float x, float y, float w, float h, IGettable<Icon> icon)
	{
		this.drawImage(x, y, w, h, icon.get());
		
	}
	
	public void drawImage(float x, float y, float w, float h, Icon icon)
	{
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
	
	public void addPopulator(IPopulator<Canvas> pop)
	{
		if (this.populators == null)
		{
			this.populators = Lists.newArrayList();
			
		}
		
		this.populators.add(pop);
		
	}
	
	public int getImageCount()
	{
		return this.images;
	}
	
}
