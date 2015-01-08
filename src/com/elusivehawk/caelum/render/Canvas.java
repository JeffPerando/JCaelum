
package com.elusivehawk.caelum.render;

import java.nio.FloatBuffer;
import com.elusivehawk.caelum.prefab.Rectangle;
import com.elusivehawk.caelum.render.gl.GL1;
import com.elusivehawk.caelum.render.gl.GLBuffer;
import com.elusivehawk.caelum.render.gl.GLConst;
import com.elusivehawk.caelum.render.gl.GLEnumBufferTarget;
import com.elusivehawk.caelum.render.gl.GLEnumDataType;
import com.elusivehawk.caelum.render.gl.GLEnumDataUsage;
import com.elusivehawk.caelum.render.gl.GLEnumDrawType;
import com.elusivehawk.caelum.render.gl.GLProgram;
import com.elusivehawk.util.IPopulator;
import com.elusivehawk.util.math.MathHelper;
import com.elusivehawk.util.storage.BufferHelper;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class Canvas extends RenderableObj
{
	public static final int IMAGE_BUFFER_SIZE = 12;
	
	public static final int INDICES_PER_IMG = 6;
	public static final int FLOATS_PER_INDEX = 4;
	public static final int FLOATS_PER_IMG = FLOATS_PER_INDEX * INDICES_PER_IMG;
	
	private final GLBuffer vertex;
	
	private FloatBuffer imgbuf = null;
	private Rectangle sub = null;
	
	private int images = 0;
	private boolean expanded = false, updateImgBuf = false, correctCoords = true;
	
	public Canvas()
	{
		this(new GLProgram(), IMAGE_BUFFER_SIZE);
		
	}
	
	public Canvas(IPopulator<Canvas> pop)
	{
		this();
		
		pop.populate(this);
		
	}
	
	public Canvas(GLProgram program)
	{
		this(program, IMAGE_BUFFER_SIZE);
		
	}
	
	public Canvas(GLProgram program, IPopulator<Canvas> pop)
	{
		this(program);
		
		pop.populate(this);
		
	}
	
	public Canvas(int images)
	{
		this(new GLProgram(), images);
		
	}
	
	public Canvas(int images, IPopulator<Canvas> pop)
	{
		this(images);
		
		pop.populate(this);
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public Canvas(GLProgram program, int images)
	{
		super(program);
		
		imgbuf = BufferHelper.createFloatBuffer(FLOATS_PER_IMG * images);
		
		vertex = new GLBuffer(GLEnumBufferTarget.GL_ARRAY_BUFFER, GLEnumDataUsage.GL_STREAM_DRAW, GLEnumDataType.GL_FLOAT, imgbuf);
		
		zBuffer = false;
		
		vertex.addAttrib(0, 2, GLConst.GL_FLOAT, false, 16, 0);			//Position data
		vertex.addAttrib(1, 2, GLConst.GL_FLOAT, false, 16, 8);			//Texture off
		
		vao.addVBO(vertex);
		
	}
	
	public Canvas(GLProgram program, int images, IPopulator<Canvas> pop)
	{
		this(program, images);
		
		pop.populate(this);
		
	}
	
	@Override
	protected boolean initiate(RenderContext rcon)
	{
		return this.program.attachShaders(rcon.get2DShaders()) > 0;
	}
	
	@Override
	protected void doRender(RenderContext rcon) throws RenderException
	{
		if (this.images > 0)
		{
			GL1.glDrawArrays(GLEnumDrawType.GL_TRIANGLES, 0, this.images * 6);
			
		}
		
	}
	
	@Override
	public void preRender(RenderContext rcon, double delta)
	{
		super.preRender(rcon, delta);
		
		if (this.imgbuf.position() != 0)
		{
			this.imgbuf.position(0);
			
		}
		
		if (this.updateImgBuf)
		{
			if (this.expanded)
			{
				this.vertex.uploadBuffer(this.imgbuf);
				
				this.expanded = false;
				
			}
			else
			{
				this.vertex.updateVBO(this.imgbuf, 0);
				
			}
			
			this.updateImgBuf = false;
			
		}
		
	}
	
	@Override
	public void postRender(RenderContext rcon)
	{
		super.postRender(rcon);
		
	}
	
	@SuppressWarnings("sync-override")
	@Override
	public RenderableObj setEnableZBuffer(boolean z)
	{
		if (z)
		{
			throw new UnsupportedOperationException("One does not simply enable Z buffering for a 2D canvas");
		}
		
		return this;
	}
	
	public int getImageCount()
	{
		return this.images;
	}
	
	public void setSubCanvas(float xmin, float ymin, float xmax, float ymax)
	{
		this.setSubCanvas(new Rectangle(xmin, ymin, xmax, ymax));
		
	}
	
	public void setSubCanvas(Rectangle r)
	{
		if (this.sub == null)
		{
			synchronized (this)
			{
				this.sub = r;
				
			}
			
		}
		
	}
	
	public int drawImage(float x, float y, float z, float w)
	{
		return this.drawImage(new Rectangle(x, y, z, w));
	}
	
	public int drawImage(float x, float y, float z, float w, Icon icon)
	{
		return this.drawImage(new Rectangle(x, y, z, w), icon);
	}
	
	public int drawImage(Rectangle r)
	{
		return this.drawImage(r, Icon.BLANK_ICON);
	}
	
	public int drawImage(Rectangle r, Icon icon)
	{
		int pos = this.images * FLOATS_PER_IMG;
		
		if (this.imgbuf.position() != pos)
		{
			synchronized (this)
			{
				this.imgbuf.position(pos);
				
			}
			
		}
		
		if (this.imgbuf.remaining() == 0)
		{
			FloatBuffer fb = BufferHelper.expand(this.imgbuf, FLOATS_PER_IMG * 12);
			
			synchronized (this)
			{
				this.imgbuf = fb;
				this.expanded = true;
				
			}
			
		}
		
		synchronized (this)
		{
			this.addCorners(r, icon);
			
			this.images++;
			this.updateImgBuf = true;
			
		}
		
		return this.images - 1;
	}
	
	public void redrawImage(int image, float x, float y, float z, float w, Icon icon)
	{
		this.redrawImage(image, new Rectangle(x, y, z, w), icon);
		
	}
	
	public void redrawImage(int image, Rectangle r, Icon icon)
	{
		assert this.images > 0;
		assert MathHelper.bounds(image, 0, this.images - 1);
		
		synchronized (this)
		{
			this.imgbuf.position(image * FLOATS_PER_IMG);
			
			this.addCorners(r, icon);
			
			this.imgbuf.position(0);
			
			this.updateImgBuf = true;
			
		}
		
	}
	
	public void clear()
	{
		this.imgbuf.clear();
		this.images = 0;
		
		this.vertex.updateVBO(this.imgbuf, 0);
		
	}
	
	public Canvas setEnableCoordCorrection(boolean correct)
	{
		this.correctCoords = correct;
		
		return this;
	}
	
	private void addCorners(Rectangle r, Icon icon)
	{
		if (this.sub != null)
		{
			r = this.sub.interpolate(r);
			
		}
		
		if (icon == null)
		{
			icon = Icon.BLANK_ICON;
			
		}
		
		this.addCorner(r.x, r.y, icon, 0);
		this.addCorner(r.x, r.w, icon, 1);
		this.addCorner(r.z, r.y, icon, 2);
		
		this.addCorner(r.x, r.w, icon, 1);
		this.addCorner(r.z, r.y, icon, 2);
		this.addCorner(r.z, r.w, icon, 3);
		
	}
	
	private void addCorner(float x, float y, Icon icon, int corner)
	{
		if (this.correctCoords)
		{
			x = (x * 2 - 1);
			y = ((1 - y) * 2 - 1);
			
		}
		
		this.imgbuf.put(x);
		this.imgbuf.put(y);
		this.imgbuf.put(icon.getCorner(corner)[0]);
		this.imgbuf.put(icon.getCorner(corner)[1]);
		
	}
	
}
