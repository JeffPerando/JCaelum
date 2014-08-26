
package com.elusivehawk.engine.render.two;

import com.elusivehawk.engine.render.Filterable;
import com.elusivehawk.engine.render.ILogicalRender;
import com.elusivehawk.engine.render.RenderContext;
import com.elusivehawk.engine.render.opengl.GLEnumPolyType;
import com.elusivehawk.engine.render.opengl.GLProgram;
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
	private final FloatBufferer buffer;
	
	private SubCanvas sub = null;
	private int images = 0;
	
	@SuppressWarnings("unqualified-field-access")
	public Canvas(int imgs)
	{
		buffer = new FloatBufferer(4, imgs * 6);
		
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
		if (this.sub != null)
		{
			x = this.sub.interpolateX(x);
			y = this.sub.interpolateY(y);
			w = this.sub.interpolateW(w);
			h = this.sub.interpolateH(h);
			
		}
		
		
		
		this.images++;
		
	}
	
	public void clear()
	{
		this.buffer.reset();
		
	}
	
}
