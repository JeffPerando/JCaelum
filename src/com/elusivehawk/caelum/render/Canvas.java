
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
	
	private final GLBuffer vertex;
	
	private FloatBuffer floatbuf = null;
	private Rectangle sub = null;
	
	private int images = 0;
	private boolean expanded = false;
	
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
		
		floatbuf = BufferHelper.createFloatBuffer(RenderConst.FLOATS_PER_IMG * images);
		vertex = new GLBuffer(GLEnumBufferTarget.GL_ARRAY_BUFFER, GLEnumDataUsage.GL_STREAM_DRAW, GLEnumDataType.GL_FLOAT, floatbuf);
		
		zBuffer = false;
		
		vertex.addAttrib(0, 2, GLConst.GL_FLOAT, false, 20, 0);			//Position data
		vertex.addAttrib(1, 2, GLConst.GL_FLOAT, false, 20, 8);			//Texture off
		vertex.addAttrib(2, 1, GLConst.GL_UNSIGNED_INT, false, 20, 16);	//Material index
		
		vao.addVBO(this.vertex);
		
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
		
		if (this.floatbuf.position() != 0)
		{
			this.floatbuf.position(0);
			
		}
		
		if (this.expanded)
		{
			this.vertex.uploadBuffer(this.floatbuf);
			
			this.expanded = false;
			
		}
		else if (this.isDirty())
		{
			this.vertex.updateVBO(this.floatbuf, 0);
			
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
	
	public void createSubCanvas(float xmin, float ymin, float xmax, float ymax)
	{
		this.createSubCanvas(new Rectangle(xmin, ymin, xmax, ymax));
		
	}
	
	public void createSubCanvas(Rectangle r)
	{
		if (this.sub == null)
		{
			this.sub = r;
			
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
		
		if (this.floatbuf.remaining() == 0)
		{
			FloatBuffer fb = BufferHelper.expand(this.floatbuf, RenderConst.FLOATS_PER_IMG * 4);
			
			synchronized (this)
			{
				this.floatbuf = fb;
				this.expanded = true;
				
			}
			
		}
		
		this.addCorner(r.x, r.y, mat, 0, icon);
		this.addCorner(r.z, r.y, mat, 1, icon);
		this.addCorner(r.x, r.w, mat, 2, icon);
		
		this.addCorner(r.z, r.y, mat, 1, icon);
		this.addCorner(r.x, r.w, mat, 2, icon);
		this.addCorner(r.z, r.w, mat, 3, icon);
		
		synchronized (this)
		{
			this.images++;
			
		}
		
		this.setIsDirty(true);
		
	}
	
	public void clear()
	{
		this.floatbuf.clear();
		this.images = 0;
		
	}
	
	private void addCorner(float a, float b, int m, int corner, Icon icon)
	{
		float[] fs = new float[]{a, b, icon.getCorner(corner)[0], icon.getCorner(corner)[1], m};
		
		synchronized (this)
		{
			this.floatbuf.put(fs);
			
		}
		
	}
	
}
