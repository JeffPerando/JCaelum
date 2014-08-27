
package com.elusivehawk.engine.render;

import java.nio.IntBuffer;
import java.util.Iterator;
import java.util.List;
import com.elusivehawk.engine.assets.Asset;
import com.elusivehawk.engine.assets.IAssetReceiver;
import com.elusivehawk.engine.render.opengl.GLEnumBufferTarget;
import com.elusivehawk.engine.render.opengl.GLEnumDataType;
import com.elusivehawk.engine.render.opengl.GLEnumDataUsage;
import com.elusivehawk.engine.render.opengl.GLEnumPolyType;
import com.elusivehawk.engine.render.opengl.GLProgram;
import com.elusivehawk.engine.render.opengl.VertexBuffer;
import com.elusivehawk.util.BufferHelper;
import com.elusivehawk.util.IPopulator;
import com.elusivehawk.util.Internal;
import com.elusivehawk.util.storage.Few;
import com.google.common.collect.Lists;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 * 
 * @see IAssetReceiver
 * @see Tessellator
 */
public class Model implements IAssetReceiver, IPopulator<GLProgram>
{
	protected final List<ModelSection> sections = Lists.newArrayList();
	protected String name;
	
	protected ModelSection sec = null;
	protected Few<VertexBuffer> fin = null;
	protected int
			polyCount = 0,
			pointCount = 0,
			indiceCount = -1;
	protected GLEnumPolyType glmode = GLEnumPolyType.GL_TRIANGLES;
	protected IntBuffer indices = null;
	
	@SuppressWarnings("unqualified-field-access")
	public Model(String mname)
	{
		name = mname;
		
	}
	
	@Override
	public void populate(GLProgram p)
	{
		Few<VertexBuffer> vbos = this.getVBOs();
		
		if (vbos != null)
		{
			p.attachVBO(vbos.one, 0, 1, 2);
			p.attachVBO(vbos.two, null);
			p.attachVBO(vbos.three, 4);
			
		}
		
	}
	
	@Override
	public void onAssetLoaded(Asset a)
	{
		if (a instanceof Mesh)
		{
			this.addMesh((Mesh)a);
			
		}
		
	}
	
	public void startSection(String secname) throws RenderException
	{
		if (this.isModelFinished())
		{
			throw new RenderException("Model already finished.");
		}
		
		if (this.sec != null)
		{
			throw new RenderException("Cannot start new model section %s", this.sec.name);
		}
		
		this.sec = new ModelSection(secname);
		
	}
	
	public void addMesh(Mesh mesh) throws RenderException
	{
		if (this.isModelFinished())
		{
			throw new RenderException("Model already finished");
		}
		
		if (this.sec == null)
		{
			throw new RenderException("Current model section is null.");
		}
		
		this.sec.addMesh(mesh);
		
	}
	
	public void endSection() throws RenderException
	{
		if (this.isModelFinished())
		{
			throw new RenderException("Model already finished.");
		}
		
		if (this.sec == null || !this.sec.hasMeshes())
		{
			throw new RenderException("Cannot end model section.");
		}
		
		this.sections.add(this.sec);
		this.sec = null;
		
	}
	
	public void finishModel()
	{
		if (this.sec != null)
		{
			throw new RenderException("Still working on model section %s!", this.sec.name);
		}
		
		if (this.sections.isEmpty())
		{
			throw new RenderException("No meshes, no shoes, no service. (You forgot to load at least one section)");
		}
		
		if (this.sections.size() > RenderConst.MATERIAL_CAP)
		{
			throw new RenderException("Too many sections: %s, must be at or less than %s", this.sections.size(), RenderConst.MATERIAL_CAP);
		}
		
		List<Integer> in = Lists.newArrayList(),
				matIndices = Lists.newArrayList();
		
		Tessellator t = new Tessellator();
		
		t.begin(GLEnumPolyType.GL_TRIANGLES);//TODO Implement an indice efficiency... thing
		
		int i = 0, matIn = 0, maxInd =-1;
		
		for (ModelSection s : this.sections)
		{
			for (Mesh m : s)
			{
				for (int c = 0; c < m.points.length(); c++)
				{
					i = t.point(m.points.get(c), m.texOffs.get(c), m.normals.get(c));
					
					in.add(i);
					this.pointCount++;
					
					if (matIndices.get(i) == null)
					{
						matIndices.add(i, matIn);
						
					}
					
					maxInd = Math.max(i, maxInd);
					
				}
				
			}
			
			matIn++;
			
		}
		
		t.end();
		
		t.finish(in);
		
		this.polyCount = RenderHelper.getPolygonCount(in.size(), this.glmode);
		this.indices = t.getIndices();
		this.indiceCount = maxInd;
		
		VertexBuffer vtx = new VertexBuffer(GLEnumBufferTarget.GL_ARRAY_BUFFER, GLEnumDataUsage.GL_STATIC_DRAW/*TODO Review usage*/, GLEnumDataType.GL_FLOAT, t.getPolygons());
		VertexBuffer ind = new VertexBuffer(GLEnumBufferTarget.GL_ELEMENT_ARRAY_BUFFER, GLEnumDataUsage.GL_STATIC_DRAW, GLEnumDataType.GL_INT, this.indices);
		VertexBuffer mat = new VertexBuffer(GLEnumBufferTarget.GL_ARRAY_BUFFER, GLEnumDataUsage.GL_STATIC_DRAW, GLEnumDataType.GL_INT, BufferHelper.makeIntBuffer(matIndices));
		
		this.fin = Few.createFew(vtx, ind, mat);
		
	}
	
	public boolean isModelFinished()
	{
		return this.fin != null;
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public Few<VertexBuffer> getVBOs()
	{
		return this.fin;
	}
	
	public GLEnumPolyType getPolygonType()
	{
		return this.glmode;
	}
	
	public int getPolyCount()
	{
		return this.polyCount;
	}
	
	public int getTotalPointCount()
	{
		return this.pointCount;
	}
	
	public int getIndiceCount()
	{
		return this.indiceCount;
	}
	
	public int getIndice(int pos)
	{
		return this.indices == null ? -1 : this.indices.get(pos);
	}
	
	/**
	 * 
	 * 
	 * 
	 * @author Elusivehawk
	 */
	@Internal
	private static class ModelSection implements Iterable<Mesh>
	{
		public final String name;
		
		private final List<Mesh> meshes = Lists.newArrayList();
		
		@SuppressWarnings("unqualified-field-access")
		ModelSection(String title)
		{
			name = title;
			
		}
		
		@Override
		public Iterator<Mesh> iterator()
		{
			return this.meshes.iterator();
		}
		
		public void addMesh(Mesh m)
		{
			this.meshes.add(m);
			
		}
		
		public boolean hasMeshes()
		{
			return !this.meshes.isEmpty();
		}
		
	}
	
}
