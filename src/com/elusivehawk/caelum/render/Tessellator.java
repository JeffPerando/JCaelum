
package com.elusivehawk.caelum.render;

import static com.elusivehawk.util.math.MathConst.X;
import static com.elusivehawk.util.math.MathConst.Y;
import static com.elusivehawk.util.math.MathConst.Z;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.List;
import com.elusivehawk.caelum.CaelumException;
import com.elusivehawk.util.IPopulator;
import com.elusivehawk.util.math.MathConst;
import com.elusivehawk.util.math.Vector;
import com.elusivehawk.util.storage.ArrayHelper;
import com.elusivehawk.util.storage.BufferHelper;
import com.google.common.collect.Lists;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class Tessellator
{
	public static final int FLOATS_PER_INDEX = 3 + 2 + 3 + 1;//Vertex + Texture + Normal + Material
	
	private List<ModelPoint> points = Lists.newArrayList();
	private List<Integer> indices = Lists.newArrayList();
	
	private FloatBuffer fs_ret = null;
	private IntBuffer in_ret = null;
	private boolean done = false;
	
	public Tessellator(){}
	
	public Tessellator(IPopulator<Tessellator> pop)
	{
		pop.populate(this);
		
	}
	
	public void vertex(Vector vtx)
	{
		this.vertex(vtx.get(X), vtx.get(Y), vtx.get(Z));
		
	}
	
	public void vertex(float x, float y, float z)
	{
		this.vertexTex(x, y, z, 0, 0);
		
	}
	
	public void vertexTex(Vector vtx, Vector tex)
	{
		this.vertexTex(vtx.get(X), vtx.get(Y), vtx.get(Z), tex.get(X), tex.get(Y));
		
	}
	
	public void vertexTex(float x, float y, float z, float u, float v)
	{
		this.vertexTexNormal(x, y, z, u, v, 0, 0, 0);
		
	}
	
	public void vertexTexNormal(Vector vtx, Vector tex, Vector n)
	{
		this.vertexTexNormal(vtx.get(X), vtx.get(Y), vtx.get(Z), tex.get(X), tex.get(Y), n.get(X), n.get(Y), n.get(Z));
		
	}
	
	public void vertexTexNormal(float x, float y, float z, float u, float v, float nx, float ny, float nz)
	{
		this.point(x, y, z, u, v, nx, ny, nz, 0);
		
	}
	
	public void point(Vector vtx, Vector tex, Vector n, int m)
	{
		this.point(vtx.get(X), vtx.get(Y), vtx.get(Z), tex.get(X), tex.get(Y), n.get(X), n.get(Y), n.get(Z), m);
		
	}
	
	public void point(float x, float y, float z, float u, float v, float nx, float ny, float nz, int m)
	{
		if (this.done)
		{
			throw new CaelumException("What are you doing with this tessellator? Go home! It's used!");
		}
		
		ModelPoint point = new ModelPoint(x, y, z, u, v, nx, ny, nz, m);
		
		int i = this.getIndex(point);
		
		if (i == -1)
		{
			this.indices.add(this.points.size());
			this.points.add(point);
			
		}
		else
		{
			this.indices.add(i);
			
		}
		
	}
	
	public FloatBuffer getVertexData()
	{
		return this.fs_ret;
	}
	
	public IntBuffer getIndices()
	{
		return this.in_ret;
	}
	
	public void reset()
	{
		this.points.clear();
		this.indices.clear();
		
		this.fs_ret = null;
		this.in_ret = null;
		this.done = false;
		
	}
	
	public void finish()
	{
		if (this.done)
		{
			throw new CaelumException("Already done!");
		}
		
		if (this.points.isEmpty())
		{
			throw new CaelumException("You didn't load any points!");
		}
		
		FloatBuffer fs = BufferHelper.createFloatBuffer(FLOATS_PER_INDEX * this.points.size());
		
		for (ModelPoint point : this.points)
		{
			fs.put(ArrayHelper.asFloats(point.vtx.multiget(MathConst.XYZ)));
			fs.put(ArrayHelper.asFloats(point.tex.multiget(MathConst.XY)));
			fs.put(ArrayHelper.asFloats(point.norm.multiget(MathConst.XYZ)));
			fs.put(point.material);
			
		}
		
		this.fs_ret = fs.asReadOnlyBuffer();
		this.in_ret = BufferHelper.makeIntBuffer(this.indices).asReadOnlyBuffer();
		this.done = true;
		
	}
	
	private int getIndex(ModelPoint point)
	{
		for (int i = 0; i < this.points.size(); i++)
		{
			if (this.points.get(i).equals(point))
			{
				return i;
			}
			
		}
		
		return -1;
	}
	
}
