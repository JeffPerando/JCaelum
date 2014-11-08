
package com.elusivehawk.caelum.render;

import java.nio.FloatBuffer;
import java.util.List;
import com.elusivehawk.caelum.render.gl.GLConst;
import com.elusivehawk.caelum.render.gl.GLEnumBufferTarget;
import com.elusivehawk.caelum.render.gl.GLEnumDataType;
import com.elusivehawk.caelum.render.gl.GLEnumDataUsage;
import com.elusivehawk.caelum.render.gl.GLEnumDrawType;
import com.elusivehawk.caelum.render.gl.GLProgram;
import com.elusivehawk.caelum.render.gl.IGL1;
import com.elusivehawk.caelum.render.gl.VertexBuffer;
import com.elusivehawk.util.IPopulator;
import com.elusivehawk.util.math.MathHelper;
import com.elusivehawk.util.storage.FloatBufferer;
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
		
		zBuffer = false;
		
		buffer = new FloatBufferer(8, 12 * 6);
		
		floatbuf = new VertexBuffer(GLEnumBufferTarget.GL_ARRAY_BUFFER, GLEnumDataUsage.GL_DYNAMIC_DRAW, GLEnumDataType.GL_FLOAT, buffer.getBuffer());
		indbuf = new VertexBuffer(GLEnumBufferTarget.GL_ELEMENT_ARRAY_BUFFER, GLEnumDataUsage.GL_DYNAMIC_DRAW, GLEnumDataType.GL_INT, buffer.getIndices());
		
		program.addVertexAttrib("in_pos", 2, GLConst.GL_UNSIGNED_INT, false, 0, 0);
		program.addVertexAttrib("in_tex", 2, GLConst.GL_UNSIGNED_INT, false, 2, 0);
		program.addVertexAttrib("in_mat", 1, GLConst.GL_UNSIGNED_INT, false, 4, 0);
		
		vao.attachVBO(floatbuf, 0, 1);
		vao.attachVBO(indbuf, null);
		
	}
	
	@Override
	protected boolean initiate(RenderContext rcon)
	{
		return true;
	}
	
	@Override
	protected void doRender(RenderContext rcon) throws RenderException
	{
		rcon.getGL1().glDrawElements(GLEnumDrawType.GL_TRIANGLES, this.images * 2, GLConst.GL_UNSIGNED_INT, 0);
		
	}
	
	@Override
	public void preRender(RenderContext rcon, double delta)
	{
		if (this.populators != null)
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
		
	}
	
	@Override
	public void postRender(RenderContext rcon)
	{
		this.buffer.rewind();
		this.buffer.setIsDirty(false);
		
	}
	
	@SuppressWarnings("sync-override")
	@Override
	public RenderableObj setEnableZBuffer(boolean z)
	{
		throw new UnsupportedOperationException("One does not simply enable Z buffering for a 2D canvas");
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
	
	public void drawImage(float x, float y, float w, float h)
	{
		this.drawImage(x, y, w, h, 0);
		
	}
	
	public void drawImage(float x, float y, float w, float h, int mat)
	{
		this.drawImage(x, y, w, h, (Icon)null, mat);
		
	}
	
	public void drawImage(float x, float y, float w, float h, Icon icon)
	{
		this.drawImage(x, y, w, h, icon, 0);
		
	}
	
	public void drawImage(float x, float y, float w, float h, Icon icon, int mat)
	{
		if (this.sub != null)
		{
			x = this.sub.interpolateX(x);
			y = this.sub.interpolateY(y);
			w = this.sub.interpolateW(w);
			h = this.sub.interpolateH(h);
			
		}
		
		int m = MathHelper.clamp(mat, 0, 16);
		
		float[][] img = new float[][]
				{
				{x, y, 0, 0, m},
				{w, y, 0, 0, m},
				{x, h, 0, 0, m},
				{w, h, 0, 0, m}
				};
		
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
