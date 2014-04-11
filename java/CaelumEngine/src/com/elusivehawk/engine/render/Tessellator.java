
package com.elusivehawk.engine.render;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.HashMap;
import java.util.List;
import com.elusivehawk.engine.assets.Mesh;
import com.elusivehawk.engine.math.Vector;
import com.elusivehawk.engine.util.BufferHelper;
import com.elusivehawk.engine.util.storage.Buffer;
import com.elusivehawk.engine.util.storage.Pair;
import com.elusivehawk.engine.util.storage.Tuple;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * 
 * Utility class for creating models.
 * 
 * @author Elusivehawk
 */
public final class Tessellator
{
	private FloatBuffer polygons = null;
	private IntBuffer indices = null;
	
	private List<Vector> polys = Lists.newArrayList();
	private List<Vector> texOffs = Lists.newArrayList();
	private List<Vector> norms = Lists.newArrayList();
	
	private int glMode = -1;
	private int pointCount = 0, oldPointCount = 0;
	private boolean working = false;
	
	private HashMap<Integer, Pair<Integer>> arrays = Maps.newHashMap();
	
	public void begin(int gl) throws RenderException
	{
		if (this.isWorking())
		{
			throw new RenderException("Already tessellating! *trollface*");
		}
		
		if (RenderHelper.getPointCount(gl) == 0)
		{
			throw new RenderException("Invalid GL mode!");
			
		}
		
		if (this.arrays.containsKey(gl))
		{
			throw new RenderException("You already used this mode!");
			
		}
		
		this.glMode = gl;
		this.oldPointCount = this.polys.size();
		this.working = true;
		
	}
	
	public void vertex(float x, float y, float z) throws RenderException
	{
		if (!this.isWorking())
		{
			throw new RenderException("Not tessellating! *trollface*");
		}
		
		this.vertex(new Vector(x, y, z));
		
	}
	
	public void mesh(Mesh m) throws RenderException
	{
		if (!this.isWorking())
		{
			throw new RenderException("Not tessellating! *trollface*");
		}
		
		for (Vector vec : m.points)
		{
			this.vertex(vec);
			
		}
		
	}
	
	public void vertex(Vector vec) throws RenderException
	{
		if (!this.isWorking())
		{
			throw new RenderException("Not tessellating! *trollface*");
		}
		
		if (vec.getSize() < 3)
		{
			return;
		}
		
		this.polys.add(vec);
		
	}
	
	public void normal(float x, float y, float z) throws RenderException
	{
		this.normal(new Vector(x, y, z));
		
	}
	
	public void normal(Vector norm) throws RenderException
	{
		if (!this.isWorking())
		{
			throw new RenderException("Not tessellating! *trollface*");
		}
		
		this.norms.add(norm);
		
	}
	
	public void texOff(float x, float y) throws RenderException
	{
		if (this.isWorking())
		{
			return;
		}
		
		this.texOffs.add(new Vector(x, y));
		
	}
	
	public void end() throws RenderException
	{
		if (!this.isWorking())
		{
			throw new RenderException("Not tessellating! *trollface*");
		}
		
		int points = RenderHelper.getPointCount(this.glMode);
		int vectors = (this.polys.size() - this.oldPointCount);
		
		if (vectors % points != 0)
		{
			throw new RenderException(String.format("Odd number of vectors (%s) loaded in mode %s", vectors, this.glMode));
		}
		
		this.pointCount += vectors;
		
		while (this.texOffs.size() < this.polys.size())
		{
			this.texOffs.add(new Vector(0f, 0f));
			
		}
		
		while (this.norms.size() < this.polys.size())
		{
			this.norms.add(new Vector(0f, 0f, 0f));//TODO Automatically calculate surface normals.
			
		}
		
		this.arrays.put(this.glMode, Pair.createPair(this.oldPointCount, this.pointCount));
		
		this.glMode = -1;
		this.working = false;
		
	}
	
	public void finish() throws RenderException
	{
		if (this.isWorking())
		{
			throw new RenderException("Already tessellating! *trollface*");
		}
		
		if (this.polys.size() == 0)
		{
			throw new RenderException("You forgot to load polygons!");
		}
		
		List<Vector> vecs = Lists.newArrayList();
		List<Integer> indiceList = Lists.newArrayList();;
		
		Buffer<Float> temp = new Buffer<Float>();
		
		for (int c = 0; c < this.polys.size(); c++)
		{
			Vector vec = this.polys.get(c);
			
			int index = vecs.indexOf(vec);
			
			if (index == -1)
			{
				Vector tex = this.texOffs.get(c);
				Vector norm = this.norms.get(c);
				
				index = vecs.size();
				vecs.add(vec);
				
				temp.add(vec.multiget(Vector.XYZ));
				temp.add(tex.multiget(Vector.XY));
				temp.add(norm.multiget(Vector.XYZ));
				
			}
			
			indiceList.add(index);
			
		}
		
		temp.rewind();
		
		this.polygons = BufferHelper.makeFloatBuffer(temp).asReadOnlyBuffer();
		this.indices = BufferHelper.makeIntBuffer(indiceList).asReadOnlyBuffer();
		
		this.polys.clear();
		this.texOffs.clear();
		this.norms.clear();
		
	}
	
	public HashMap<Integer, Pair<Integer>> getOffsets()
	{
		return this.arrays;
	}
	
	public FloatBuffer getPolygons()
	{
		return this.polygons;
	}
	
	public IntBuffer getIndices()
	{
		return this.indices;
	}
	
	public int getPointCount()
	{
		return this.pointCount;
	}
	
	public boolean isWorking()
	{
		return this.working && this.glMode != -1;
	}
	
}
