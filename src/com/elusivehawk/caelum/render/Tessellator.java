
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
	public static final int FLOATS_PER_INDEX = 3 + 2 + 3;//Vertex + Texture + Normal
	
	private List<ModelPoint> points = Lists.newArrayList();
	private List<Integer> indices = Lists.newArrayList();
	
	private FloatBuffer fs_ret = null;
	private IntBuffer in_ret = null;
	private boolean done = false, optimized = false;
	
	public Tessellator(){}
	
	public Tessellator(IPopulator<Tessellator> pop)
	{
		pop.populate(this);
		
	}
	
	public int vertex(float x, float y, float z)
	{
		return this.vertexTex(x, y, z, 0, 0);
	}
	
	public int vertex(Vector vtx)
	{
		return this.vertexTex(vtx, new Vector(2));
	}
	
	public int vertexTex(float x, float y, float z, float u, float v)
	{
		return this.point(x, y, z, u, v, 0, 0, 0);
	}
	
	public int vertexTex(Vector vtx, Vector tex)
	{
		return this.point(vtx, tex, new Vector(3));
	}
	
	public int point(float x, float y, float z, float u, float v, float nx, float ny, float nz)
	{
		return this.point(new Vector(x, y, z), new Vector(u, v), new Vector(nx, ny, nz));
	}
	
	public int point(Vector vtx, Vector tex, Vector n)
	{
		if (this.done)
		{
			throw new CaelumException("What are you doing with this tessellator? Go home! It's used!");
		}
		
		ModelPoint point = new ModelPoint(vtx, tex, n);
		
		int i = this.indices.indexOf(point);
		
		if (i == -1)
		{
			i = this.points.size();
			this.points.add(point);
			
		}
		
		this.indices.add(i);
		
		this.optimized = false;
		
		return i;
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
			
		}
		
		this.fs_ret = fs.asReadOnlyBuffer();
		this.in_ret = BufferHelper.makeIntBuffer(this.indices).asReadOnlyBuffer();
		this.done = true;
		
	}
	
	public boolean isOptimized()
	{
		return this.optimized;
	}
	
	public boolean optimize()
	{
		if (this.done)
		{
			return false;
		}
		
		if (this.optimized)
		{
			return true;
		}
		
		if (this.points.size() < 2)
		{
			return false;
		}
		
		if (this.points.size() % 3 != 0)
		{
			return false;
		}
		
		List<Triangle> triangles = Lists.newArrayListWithCapacity(this.indices.size() / 3);
		
		for (int c = 0; c < this.points.size(); c += 3)
		{
			triangles.add(new Triangle(this.indices.get(c), this.indices.get(c + 1), this.indices.get(c + 2)));
			
		}
		
		List<Triangle> optimized = Lists.newArrayList();
		
		optimized.add(triangles.get(0));
		
		while (!triangles.isEmpty())
		{
			for (int c = 1; c < triangles.size(); c++)
			{
				Triangle t = triangles.get(c);
				
				boolean used = false;
				
				if (t.canGoAheadOf(optimized.get(0)))
				{
					optimized.add(0, t);
					used = true;
					
				}
				else if (t.canGoBehind(optimized.get(optimized.size() - 1)))
				{
					optimized.add(t);
					used = true;
					
				}
				
				if (used)
				{
					triangles.remove(c);
					c--;//To make sure we don't skip a triangle
					
					continue;
				}
				
				boolean useful = false;
				
				for (Triangle other : triangles)
				{
					if (t.equals(other))
					{
						continue;
					}
					
					if (t.canGoAheadOf(other) || t.canGoBehind(other))
					{
						useful = true;
						break;
					}
					
				}
				
				if (!useful)
				{
					return false;
				}
				
			}
			
		}
		
		if (optimized.isEmpty())
		{
			return false;
		}
		
		List<Integer> newind = Lists.newArrayList();
		
		Triangle first = optimized.get(0);
		
		newind.add(first.a);
		newind.add(first.b);
		newind.add(first.c);
		
		for (int c = 1; c < optimized.size(); c++)
		{
			newind.add(optimized.get(c).c);
			
		}
		
		this.indices.clear();
		this.indices.addAll(newind);
		
		this.optimized = true;
		
		return true;
	}
	
}
