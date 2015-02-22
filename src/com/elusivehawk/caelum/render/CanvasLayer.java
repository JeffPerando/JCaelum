
package com.elusivehawk.caelum.render;

import java.nio.FloatBuffer;
import com.elusivehawk.caelum.prefab.Rectangle;
import com.elusivehawk.caelum.render.gl.GL1;
import com.elusivehawk.caelum.render.gl.GLBuffer;
import com.elusivehawk.caelum.render.gl.GLConst;
import com.elusivehawk.caelum.render.gl.GLEnumBufferTarget;
import com.elusivehawk.caelum.render.gl.GLEnumDataUsage;
import com.elusivehawk.caelum.render.gl.GLEnumDrawType;
import com.elusivehawk.caelum.render.gl.GLVertexArray;
import com.elusivehawk.caelum.render.tex.Material;
import com.elusivehawk.util.math.MathHelper;
import com.elusivehawk.util.storage.BufferHelper;
import com.elusivehawk.util.storage.DirtableStorage;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class CanvasLayer extends Renderable
{
	private final Canvas parent;
	
	private final DirtableStorage<Material> mat = new DirtableStorage<Material>().setSync();
	private final GLVertexArray vao = new GLVertexArray();
	private final GLBuffer vertex = new GLBuffer(GLEnumBufferTarget.GL_ARRAY_BUFFER);
	
	private FloatBuffer imgbuf = BufferHelper.createFloatBuffer(Canvas.FLOATS_PER_IMG * 12);
	private SubCanvas sub = null;
	private int images = 0;
	
	private boolean expanded = false, updateImgBuf = false;
	
	public CanvasLayer()
	{
		this(null);
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public CanvasLayer(Canvas cvs)
	{
		parent = cvs;
		
	}
	
	@Override
	public void delete(RenderContext rcon)
	{
		this.vertex.delete(rcon);
		this.vao.delete(rcon);
		
		if (!this.mat.isNull())
		{
			this.mat.get().delete(rcon);
			
		}
		
	}
	
	@Override
	public boolean initiate(RenderContext rcon)
	{
		this.vertex.init(rcon, this.imgbuf, GLEnumDataUsage.GL_STREAM_DRAW);
		
		this.vertex.addAttrib(0, 2, GLConst.GL_FLOAT, 16, 0);		//Position data
		this.vertex.addAttrib(1, 2, GLConst.GL_FLOAT, 16, 8);		//Texture off
		
		this.vao.addVBO(this.vertex);
		
		return true;
	}
	
	@Override
	public void renderImpl(RenderContext rcon) throws RenderException
	{
		if (this.images == 0)
		{
			return;
		}
		
		if (!this.vao.bind(rcon))
		{
			return;
		}
		
		if (!this.mat.isNull())
		{
			this.mat.get().bind(rcon);
			
		}
		
		GL1.glDrawArrays(GLEnumDrawType.GL_TRIANGLES, 0, this.images * 6);
		
		if (!this.mat.isNull())
		{
			this.mat.get().unbind(rcon);
			
		}
		
		this.vao.unbind(rcon);
		
	}
	
	@Override
	public void preRender(RenderContext rcon)
	{
		super.preRender(rcon);
		
		if (!this.mat.isNull())
		{
			this.mat.get().preRender(rcon);
			
		}
		
		if (this.imgbuf.position() != 0)
		{
			this.imgbuf.flip();
			
		}
		
		if (this.updateImgBuf)
		{
			if (this.expanded)
			{
				this.vertex.init(rcon, this.imgbuf, GLEnumDataUsage.GL_STREAM_DRAW);
				
				this.expanded = false;
				
			}
			else
			{
				this.vertex.update(rcon, this.imgbuf, 0);
				
			}
			
			this.updateImgBuf = false;
			
		}
		
	}
	
	@Override
	public void postRender(RenderContext rcon) throws RenderException
	{
		super.postRender(rcon);
		
		if (!this.mat.isNull())
		{
			this.mat.get().postRender(rcon);
			
		}
		
	}
	
	public Material getMaterial()
	{
		return this.mat.get();
	}
	
	public int getImageCount()
	{
		return this.images;
	}
	
	public void setMaterial(Material m)
	{
		this.mat.set(m);
		
	}
	
	public void createSubCanvas(Rectangle r)
	{
		this.createSubCanvas(r.x, r.y, r.z, r.w);
		
	}
	
	public void createSubCanvas(float x, float y, float z, float w)
	{
		if (this.sub == null)
		{
			this.sub = new SubCanvas(x, y, z, w);
			
		}
		else
		{
			this.sub.createSubCanvas(x, y, z, w);
			
		}
		
	}
	
	public void drawImage(Rectangle r, Icon icon)
	{
		int pos = this.images * Canvas.FLOATS_PER_IMG;
		
		if (this.imgbuf.position() != pos)
		{
			this.imgbuf.position(pos);
			
		}
		
		if (this.imgbuf.remaining() == 0)
		{
			this.imgbuf = BufferHelper.expand(this.imgbuf, Canvas.FLOATS_PER_IMG * 12);
			this.expanded = true;
			
		}
		
		this.addCorners(r, icon);
		
		this.images++;
		this.updateImgBuf = true;
		
	}
	
	public void redrawImage(int image, Rectangle r, Icon icon)
	{
		assert this.images > 0;
		assert MathHelper.bounds(image, 0, this.images - 1);
		
		synchronized (this)
		{
			this.imgbuf.position(image * Canvas.FLOATS_PER_IMG);
			
			this.addCorners(r, icon);
			
			this.imgbuf.position(0);
			
			this.updateImgBuf = true;
			
		}
		
	}
	
	public void clear()
	{
		this.imgbuf.clear();
		this.images = 0;
		this.sub = null;
		
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
		this.addCorner(r.z, r.y, icon, 1);
		this.addCorner(r.x, r.w, icon, 2);
		
		this.addCorner(r.z, r.y, icon, 1);
		this.addCorner(r.x, r.w, icon, 2);
		this.addCorner(r.z, r.w, icon, 3);
		
	}
	
	private void addCorner(float x, float y, Icon icon, int corner)
	{
		if (this.parent == null || this.parent.doCoordCorrection())
		{
			x = (x * 2 - 1);
			y = ((1 - y) * 2 - 1);
			
		}
		
		this.imgbuf.put(x);
		this.imgbuf.put(y);
		this.imgbuf.put(icon.getX(corner));
		this.imgbuf.put(icon.getY(corner));
		
	}
	
}
