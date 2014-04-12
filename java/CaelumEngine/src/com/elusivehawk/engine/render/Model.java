
package com.elusivehawk.engine.render;

import java.util.List;
import com.elusivehawk.engine.assets.Mesh;
import com.elusivehawk.engine.render.opengl.GLConst;
import com.elusivehawk.engine.render.opengl.VertexBuffer;
import com.elusivehawk.engine.util.BufferHelper;
import com.elusivehawk.engine.util.storage.Few;
import com.google.common.collect.Lists;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class Model
{
	protected final String name;
	
	protected ModelSection sec = null;
	protected List<ModelSection> sections = Lists.newArrayList();
	
	protected Few<VertexBuffer> fin = null;
	protected int glmode = GLConst.GL_TRIANGLES, polyCount = 0, pointCount = 0;
	
	@SuppressWarnings("unqualified-field-access")
	public Model(String title)
	{
		name = title;
		
	}
	
	public void startSection(String secname) throws RenderException
	{
		if (this.sec == null)
		{
			this.sec = new ModelSection(secname);
			
			return;
		}
		
		throw new RenderException(String.format("Cannot start new model section! Model: %s, Section: %s", this.name, this.sec.name));
	}
	
	public void addMesh(Mesh mesh) throws RenderException
	{
		if (this.sec != null)
		{
			this.sec.addMesh(mesh);
			
			return;
		}
		
		throw new RenderException(String.format("Cannot add mesh to model section! Model: %s", this.name));
	}
	
	public void endSection() throws RenderException
	{
		if (this.sec != null && this.sec.hasMeshes())
		{
			this.sections.add(this.sec);
			this.sec = null;
			
			return;
		}
		
		throw new RenderException(String.format("Cannot end model section! Model: %s", this.name));
	}
	
	public void finish() throws RenderException
	{
		if (this.sec != null)
		{
			throw new RenderException(String.format("Still working on model section %s!", this.sec.name));
		}
		
		if (this.sections.isEmpty())
		{
			throw new RenderException("No meshes, no shoes, no service. (You forgot to load at least one section)");
		}
		
		if (this.sections.size() > RenderHelper.MATERIAL_CAP)
		{
			throw new RenderException(String.format("Too many sections: %s, must be at or less than %s", this.sections.size(), RenderHelper.MATERIAL_CAP));
		}
		
		List<Integer> in = Lists.newArrayList(),
				matIndices = Lists.newArrayList();
		
		Tessellator t = new Tessellator();
		
		t.begin(GLConst.GL_TRIANGLES);//TODO Implement an indice efficiency... thing
		
		int i = 0, matIn = 0;
		
		for (ModelSection s : this.sections)
		{
			for (Mesh m : s)
			{
				for (int c = 0; c < m.points.length(); c++)
				{
					in.add(i = t.point(m.points.get(c), m.texOffs.get(c), m.normals.get(c)));
					this.pointCount++;
					
					if (matIndices.get(i) == null)
					{
						matIndices.add(i, matIn);
						
					}
					
				}
				
			}
			
			matIn++;
			
		}
		
		t.end();
		
		t.finish(in);
		
		VertexBuffer vtx = new VertexBuffer(GLConst.GL_ARRAY_BUFFER, t.getPolygons(), GLConst.GL_STATIC_DRAW/*TODO Review usage*/);
		VertexBuffer ind = new VertexBuffer(GLConst.GL_INDEX_ARRAY, t.getIndices(), GLConst.GL_STATIC_DRAW);
		VertexBuffer mat = new VertexBuffer(GLConst.GL_ARRAY_BUFFER, BufferHelper.makeIntBuffer(matIndices), GLConst.GL_STATIC_DRAW);
		
		this.fin = Few.createFew(vtx, ind, mat);
		this.polyCount = RenderHelper.getPolygonCount(in.size(), this.glmode);
		
	}
	
	public boolean isFinished()
	{
		return this.fin != null;
	}
	
	public Few<VertexBuffer> getVBOs()
	{
		return this.fin;
	}
	
	public int getDrawMode()
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
	
}
