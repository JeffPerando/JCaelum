
package com.elusivehawk.engine.render;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.List;
import com.elusivehawk.engine.render.opengl.GLEnumPolyType;
import com.elusivehawk.util.BufferHelper;
import com.elusivehawk.util.EnumLogType;
import com.elusivehawk.util.Logger;
import com.elusivehawk.util.math.MathConst;
import com.elusivehawk.util.math.Vector;
import com.google.common.collect.Lists;

/**
 * 
 * Utility class for creating models.
 * 
 * @author Elusivehawk
 */
public final class Tessellator
{
	private FloatBuffer finbuf = null;
	private IntBuffer indices = null;
	
	private final List<ModelPoint> polys = Lists.newArrayList();
	
	private GLEnumPolyType glMode = null;
	
	public void begin(GLEnumPolyType gl) throws RenderException
	{
		if (this.isWorking())
		{
			throw new RenderException("Already tessellating! *trollface*");
		}
		
		if (gl.getPointCount() == 0)
		{
			throw new RenderException("Invalid GL mode!");
			
		}
		
		this.glMode = gl;
		
	}
	
	public int vertex(float x, float y, float z) throws RenderException
	{
		if (!this.isWorking())
		{
			throw new RenderException("Not tessellating! *trollface*");
		}
		
		return this.vertexWithTexUV(x, y, z, 0f, 0f);
	}
	
	public int vertexWithTexUV(float x, float y, float z, float u, float v)
	{
		return this.point(new Vector(x, y, z), new Vector(u, v), new Vector(3));
	}
	
	public int vertexWithNormal(float x, float y, float z, float xn, float yn, float zn)
	{
		return this.point(new Vector(x, y, z), new Vector(2), new Vector(xn, yn, zn));
	}
	
	public int point(Vector vtx, Vector tex, Vector norm) throws RenderException
	{
		if (!this.isWorking())
		{
			throw new RenderException("Not tessellating! *trollface*");
		}
		
		assert vtx != null;
		
		Vector t = tex, n = norm;
		
		if (t == null)
		{
			t = new Vector(2);
			
		}
		
		if (n == null)
		{
			n = new Vector(3);
			
		}
		
		if (vtx.length() < 3 || t.length() < 2 || n.length() < 3)
		{
			throw new RenderException("Your vector(s) are too small");
		}
		
		ModelPoint point = new ModelPoint(vtx, t, n);
		
		int i = this.polys.indexOf(point);
		
		if (i == -1)
		{
			i = this.polys.size();
			this.polys.add(point);
			
		}
		
		return i;
	}
	
	public void end() throws RenderException
	{
		if (!this.isWorking())
		{
			throw new RenderException("Not tessellating! *trollface*");
		}
		
		if (this.polys.size() % this.glMode.getPointCount() != 0)
		{
			throw new RenderException(String.format("Odd number of vectors (%s) loaded in mode %s", this.polys.size(), this.glMode));
		}
		
		this.glMode = null;
		
	}
	
	public void finish(List<Integer> in) throws RenderException
	{
		if (this.isWorking())
		{
			throw new RenderException("Already tessellating! *trollface*");
		}
		
		if (this.polys.size() == 0)
		{
			throw new RenderException("You forgot to load any points!");
		}
		
		List<Float> temp = Lists.newArrayList();
		ModelPoint p = null;
		int  i = 0;
		
		for (int c = 0; c < in.size(); c++)
		{
			i = in.get(c);
			
			p = this.polys.get(i);
			
			if (p == null)
			{
				Logger.log().log(EnumLogType.WARN, "Skipping indice %s at location %s", i, c);
				
				continue;
			}
			
			Number[] n = p.v.multiget(MathConst.XYZ);
			
			for (Number num : n)
			{
				temp.add(num.floatValue());
				
			}
			
			n = p.t.multiget(MathConst.XY);
			
			for (Number num : n)
			{
				temp.add(num.floatValue());
				
			}
			
			n = p.n.multiget(MathConst.XYZ);
			
			for (Number num : n)
			{
				temp.add(num.floatValue());
				
			}
			
		}
		
		this.finbuf = BufferHelper.makeFloatBuffer(temp).asReadOnlyBuffer();
		this.indices = BufferHelper.makeIntBuffer(in).asReadOnlyBuffer();
		
		this.polys.clear();
		
	}
	
	public FloatBuffer getPolygons()
	{
		return this.finbuf;
	}
	
	public IntBuffer getIndices()
	{
		return this.indices;
	}
	
	public int getPointCount()
	{
		return this.polys.size();
	}
	
	public boolean isWorking()
	{
		return this.glMode != null;
	}
	
}
