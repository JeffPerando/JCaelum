
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
	public static final Icon BLANK_ICON = new Icon(0, 0, 1, 1);
	
	public static final int IMAGE_BUFFER_SIZE = 12;
	
	public static final int INDICES_PER_IMG = 6;
	public static final int FLOATS_PER_INDEX = 5;
	public static final int FLOATS_PER_IMG = FLOATS_PER_INDEX * INDICES_PER_IMG;
	
	private final GLBuffer vertex;
	
	private FloatBuffer imgbuf = null;
	private Rectangle sub = null;
	
	private int images = 0;
	private boolean expanded = false, drewNewImages = false;
	
	public Canvas()
	{
		this(new GLProgram(), IMAGE_BUFFER_SIZE);
		
	}
	
	public Canvas(GLProgram program)
	{
		this(program, IMAGE_BUFFER_SIZE);
		
	}
	
	public Canvas(int images)
	{
		this(new GLProgram(), images);
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public Canvas(GLProgram program, int images)
	{
		super(program);
		
		imgbuf = BufferHelper.createFloatBuffer(FLOATS_PER_IMG * images);
		
		vertex = new GLBuffer(GLEnumBufferTarget.GL_ARRAY_BUFFER, GLEnumDataUsage.GL_STREAM_DRAW, GLEnumDataType.GL_FLOAT, imgbuf);
		
		zBuffer = false;
		
		vertex.addAttrib(0, 2, GLConst.GL_FLOAT, false, 20, 0);			//Position data
		vertex.addAttrib(1, 2, GLConst.GL_FLOAT, false, 20, 8);			//Texture off
		vertex.addAttrib(2, 1, GLConst.GL_FLOAT, false, 20, 16);		//Material index
		
		vao.addVBO(vertex);
		
	}
	
	@Override
	protected boolean initiate(RenderContext rcon)
	{
		return this.p.attachShaders(rcon.get2DShaders()) > 0;
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
		
		if (this.drewNewImages)
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
			
			this.drewNewImages = false;
			
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
	
	public void drawImage(float x, float y, float z, float w)
	{
		this.drawImage(x, y, z, w, 0);
		
	}
	
	public void drawImage(float x, float y, float z, float w, int mat)
	{
		this.drawImage(x, y, z, w, BLANK_ICON, mat);
		
	}
	
	public void drawImage(float x, float y, float z, float w, Icon icon)
	{
		this.drawImage(x, y, z, w, icon, 0);
		
	}
	
	public void drawImage(float x, float y, float z, float w, Icon icon, int mat)
	{
		this.drawImage(new Rectangle(x, y, z, w), icon, mat);
		
	}
	
	public void drawImage(Rectangle r)
	{
		this.drawImage(r, null, 0);
		
	}
	
	public void drawImage(Rectangle r, Icon icon)
	{
		this.drawImage(r, icon, 0);
		
	}
	
	public void drawImage(Rectangle r, int mat)
	{
		this.drawImage(r, BLANK_ICON, mat);
		
	}
	
	public void drawImage(Rectangle r, Icon icon, int mat)
	{
		if (this.sub != null)
		{
			r = this.sub.interpolate(r);
			
		}
		
		if (icon == null)
		{
			icon = BLANK_ICON;
			
		}
		
		mat = MathHelper.clamp(mat, 0, RenderConst.MATERIAL_CAP - 1);
		
		if (this.imgbuf.remaining() == 0)
		{
			FloatBuffer fb = BufferHelper.expand(this.imgbuf, FLOATS_PER_IMG * 12);
			
			synchronized (this)
			{
				this.imgbuf = fb;
				this.expanded = true;
				
			}
			
		}
		
		this.addCorner(r.x, r.y, mat, 0, icon);
		this.addCorner(r.x, r.w, mat, 1, icon);
		this.addCorner(r.z, r.y, mat, 2, icon);
		
		this.addCorner(r.x, r.w, mat, 1, icon);
		this.addCorner(r.z, r.y, mat, 2, icon);
		this.addCorner(r.z, r.w, mat, 3, icon);
		
		synchronized (this)
		{
			this.images++;
			this.drewNewImages = true;
			
		}
		
	}
	
	public void clear()
	{
		this.imgbuf.clear();
		this.images = 0;
		
	}
	
	private void addCorner(float a, float b, int m, int corner, Icon icon)
	{
		float[] fs = new float[]{a, b, icon.getCorner(corner)[0], icon.getCorner(corner)[1], m};
		
		synchronized (this)
		{
			this.imgbuf.put(fs);
			
		}
		
	}
	
}
