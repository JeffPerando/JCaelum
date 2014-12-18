
package com.elusivehawk.caelum.render;

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
	
	public void vertex(float x, float y, float z)
	{
		this.vertexTex(x, y, z, 0, 0);
		
	}
	
	public void vertex(Vector vtx)
	{
		this.vertexTex(vtx, new Vector(2));
		
	}
	
	public void vertexTex(float x, float y, float z, float u, float v)
	{
		this.vertexTexNormal(x, y, z, u, v, 0, 0, 0);
		
	}
	
	public void vertexTex(Vector vtx, Vector tex)
	{
		this.vertexTexNormal(vtx, tex, new Vector(3));
		
	}
	
	public void vertexTexNormal(float x, float y, float z, float u, float v, float nx, float ny, float nz)
	{
		this.point(x, y, z, u, v, nx, ny, nz, 0);
		
	}
	
	public void vertexTexNormal(Vector vtx, Vector tex, Vector n)
	{
		this.point(vtx, tex, n, 0);
		
	}
	
	public void point(float x, float y, float z, float u, float v, float nx, float ny, float nz, int m)
	{
		this.point(new Vector(x, y, z), new Vector(u, v), new Vector(nx, ny, nz), m);
		
	}
	
	public void point(Vector vtx, Vector tex, Vector n, int m)
	{
		if (this.done)
		{
			throw new CaelumException("What are you doing with this tessellator? Go home! It's used!");
		}
		
		ModelPoint point = new ModelPoint(vtx, tex, n, m);
		
		int i = this.indices.indexOf(point);
		
		if (i == -1)
		{
			i = this.points.size();
			this.points.add(point);
			
		}
		
		this.indices.add(i);
		
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
			fs.put(point.mat);
			
		}
		
		this.fs_ret = fs.asReadOnlyBuffer();
		this.in_ret = BufferHelper.makeIntBuffer(this.indices).asReadOnlyBuffer();
		this.done = true;
		
	}
	
}
