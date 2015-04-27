
package com.elusivehawk.caelum.render;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.List;
import com.elusivehawk.caelum.CaelumException;
import com.elusivehawk.util.IPopulator;
import com.elusivehawk.util.math.VectorF;
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
	
	private final List<ModelPoint> points = Lists.newArrayList();
	private final List<Integer> indices = Lists.newArrayList();
	
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
	
	public int vertex(VectorF vtx)
	{
		return this.vertexTex(vtx, (VectorF)new VectorF(2).setImmutable());
	}
	
	public int vertexTex(float x, float y, float z, float u, float v)
	{
		return this.point(x, y, z, u, v, 0, 0, 0);
	}
	
	public int vertexTex(VectorF vtx, VectorF tex)
	{
		return this.point(vtx, tex, (VectorF)new VectorF(3).setImmutable());
	}
	
	public int point(float x, float y, float z, float u, float v, float nx, float ny, float nz)
	{
		return this.point((VectorF)new VectorF(x, y, z).setImmutable(), (VectorF)new VectorF(u, v).setImmutable(), (VectorF)new VectorF(nx, ny, nz).setImmutable());
	}
	
	public int point(VectorF vtx, VectorF tex, VectorF n)
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
	
	public void reset()
	{
		this.points.clear();
		this.indices.clear();
		
		this.fs_ret = null;
		this.in_ret = null;
		this.done = false;
		
	}
	
	public MeshData finish()
	{
		return this.finish(false);
	}
	
	public MeshData finish(boolean optimize)
	{
		if (this.points.isEmpty())
		{
			throw new CaelumException("You didn't load any points!");
		}
		
		if (optimize && !this.optimize())
		{
			throw new CaelumException("Could not optimize!");
		}
		
		if (!this.done)
		{
			FloatBuffer fs = BufferHelper.createFloatBuffer(this.points.size() * FLOATS_PER_INDEX);
			
			this.points.forEach(((point) ->
			{
				point.vtx.put(fs);
				point.tex.put(fs);
				point.norm.put(fs);
				
			}));
			
			this.fs_ret = fs.asReadOnlyBuffer();
			this.in_ret = BufferHelper.makeIntBuffer(this.indices).asReadOnlyBuffer();
			this.done = true;
			
		}
		
		MeshData ret = new MeshData(this.fs_ret);
		
		if (this.in_ret != null)
		{
			ret.indices(this.in_ret);
			
		}
		
		if (this.optimized)
		{
			ret.strip();
			
		}
		
		ret.texCoordSize(2);
		
		ret.lock();
		
		return ret;
	}
	
	private boolean optimize()
	{
		if (this.done)
		{
			return this.optimized;
		}
		
		if (this.optimized)
		{
			return true;
		}
		
		if (this.indices.size() < 6)
		{
			return false;
		}
		
		if (this.indices.size() % 3 != 0)
		{
			return false;
		}
		
		List<Triangle> triangles = Lists.newArrayListWithCapacity(this.indices.size() / 3);
		
		for (int c = 0; c < this.points.size(); c += 3)
		{
			triangles.add(new Triangle(this.indices.get(c), this.indices.get(c + 1), this.indices.get(c + 2)));
			
		}
		
		boolean opd = true;
		
		for (int c = 1; c < triangles.size(); c++)
		{
			if (!triangles.get(c).canGoAheadOf(triangles.get(c - 1)))
			{
				opd = false;
				break;
			}
			
		}
		
		if (opd)
		{
			return true;
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
		
		optimized.forEach(((tri) ->
		{
			newind.add(tri.c);
			
		}));
		
		this.indices.clear();
		this.indices.addAll(newind);
		
		this.optimized = true;
		
		return true;
	}
	
}
