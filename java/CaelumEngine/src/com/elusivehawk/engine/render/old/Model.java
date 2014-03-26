
package com.elusivehawk.engine.render.old;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.elusivehawk.engine.core.CaelumEngine;
import com.elusivehawk.engine.core.EnumLogType;
import com.elusivehawk.engine.math.Vector;
import com.elusivehawk.engine.render.RenderContext;
import com.elusivehawk.engine.render.RenderHelper;
import com.elusivehawk.engine.render.opengl.GLConst;
import com.elusivehawk.engine.render.opengl.VertexBufferObject;
import com.elusivehawk.engine.util.BufferHelper;
import com.elusivehawk.engine.util.SemiFinalStorage;
import com.elusivehawk.engine.util.SemiFinalStorage.IStorageListener;
import com.elusivehawk.engine.util.SimpleList;
import com.elusivehawk.engine.util.Tuple;

/**
 * 
 * The modelling system used by This Very Engine!
 * 
 * @author Elusivehawk
 */
@Deprecated
@SuppressWarnings("rawtypes")
public class Model implements IStorageListener
{
	@SuppressWarnings("unchecked")
	public final SemiFinalStorage<Integer> indiceCount = new SemiFinalStorage<Integer>(0, this);
	@SuppressWarnings("unchecked")
	public final SemiFinalStorage<VertexBufferObject> finBuf = new SemiFinalStorage<VertexBufferObject>(null, this);
	@SuppressWarnings("unchecked")
	public final SemiFinalStorage<VertexBufferObject> indiceBuf = new SemiFinalStorage<VertexBufferObject>(null, this);
	
	private boolean finished = false;
	private List<Vector> polys = SimpleList.newList();
	private List<Vector> texOffs = SimpleList.newList();
	private List<Vector> norms = SimpleList.newList();
	private int glMode = Integer.MIN_VALUE;
	private int pointCount = 0, oldPointCount = 0;
	private HashMap<Integer, Tuple<Integer, Integer>> arrays = new HashMap<Integer, Tuple<Integer, Integer>>();
	
	public void finish()
	{
		if (this.finished)
		{
			return;
		}
		
		if (this.polys.size() == 0)
		{
			throw new RuntimeException("You forgot to load polygons!");
			
		}
		
		if (this.glMode != Integer.MIN_VALUE)
		{
			this.end();
			
		}
		
		this.finished = true;
		
		List<Vector> vecs = new SimpleList<Vector>();
		List<Integer> indiceList = new SimpleList<Integer>();
		
		List<Float> temp = new ArrayList<Float>();
		
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
				
				temp.add(vec.get(Vector.X));
				temp.add(vec.get(Vector.Y));
				temp.add(vec.get(Vector.Z));
				
				temp.add(tex.get(Vector.X));
				temp.add(tex.get(Vector.Y));
				
				temp.add(norm.get(Vector.X));
				temp.add(norm.get(Vector.Y));
				temp.add(norm.get(Vector.Z));
				
			}
			
			indiceList.add(index);
			
		}
		
		FloatBuffer fin = BufferHelper.makeFloatBuffer(temp).asReadOnlyBuffer();
		IntBuffer indices = BufferHelper.makeIntBuffer(indiceList).asReadOnlyBuffer();
		RenderContext context = CaelumEngine.renderContext();
		
		int vb = context.getGL1().glGetInteger(GLConst.GL_VERTEX_ARRAY);
		
		if (vb != 0)
		{
			CaelumEngine.log().log(EnumLogType.WARN, "Temporarily unbinding vertex array!");
			context.getGL3().glBindVertexArray(0);
			
		}
		
		int[] vbos = RenderHelper.createVBOs(2);
		
		this.finBuf.set(new VertexBufferObject(vbos[0], GLConst.GL_ARRAY_BUFFER, fin, GLConst.GL_STATIC_DRAW));
		this.indiceBuf.set(new VertexBufferObject(vbos[1], GLConst.GL_ELEMENT_ARRAY_BUFFER, indices, GLConst.GL_STATIC_DRAW));
		
		if (vb != 0)
		{
			CaelumEngine.log().log(EnumLogType.WARN, "Rebinding vertex array");
			context.getGL3().glBindVertexArray(vb);
			
		}
		
		this.polys = null;
		this.texOffs = null;
		this.norms = null;
		
		this.indiceCount.set(this.pointCount);
		
	}
	
	public final void begin(int gl)
	{
		if (this.finished)
		{
			return;
		}
		
		if (this.glMode != Integer.MIN_VALUE)
		{
			throw new RuntimeException("You're already adding vertices!");
			
		}
		
		if (RenderHelper.getPointCount(gl) == 0)
		{
			throw new RuntimeException("Invalid GL mode!");
			
		}
		
		if (this.arrays.containsKey(gl))
		{
			throw new RuntimeException("You already used this mode!");
			
		}
		
		this.glMode = gl;
		this.oldPointCount = this.polys.size();
		
	}
	
	public final void end()
	{
		if (this.finished)
		{
			return;
		}
		
		if (this.glMode == Integer.MIN_VALUE)
		{
			throw new RuntimeException("You need to call begin() first!");
			
		}
		
		int points = RenderHelper.getPointCount(this.glMode);
		int vectors = (this.polys.size() - this.oldPointCount);
		
		if (vectors % points != 0)
		{
			throw new RuntimeException("Odd number of vectors loaded: " + vectors + " in mode " + this.glMode);
			
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
		
		Tuple<Integer, Integer> t = Tuple.create(this.oldPointCount, this.pointCount);
		
		this.arrays.put(this.glMode, t);
		
		this.glMode = Integer.MIN_VALUE;
		
	}
	
	public final void vertex(float x, float y, float z)
	{
		if (this.finished)
		{
			return;
		}
		
		this.vertex(new Vector(x, y, z));
		
	}
	
	public final void vertex(Vector vec)
	{
		if (this.finished)
		{
			return;
		}
		
		if (vec.getSize() < 3)
		{
			return;
		}
		
		this.polys.add(vec);
		
	}
	
	public final void color(float x, float y, float z)
	{
		this.normal(new Vector(x, y, z));
		
	}
	
	public final void normal(Vector norm)
	{
		if (this.finished)
		{
			return;
		}
		
		this.norms.add(norm);
		
	}
	
	public final void texOff(float x, float y)
	{
		if (this.finished)
		{
			return;
		}
		
		this.texOffs.add(new Vector(x, y));
		
	}
	
	public HashMap<Integer, Tuple<Integer, Integer>> getOffsets()
	{
		return this.arrays;
	}
	
	public boolean isFinished()
	{
		return this.finished;
	}
	
	@Override
	public boolean canChange(SemiFinalStorage stor)
	{
		return this.isFinished();
	}
	
}
