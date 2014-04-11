
package com.elusivehawk.engine.render;

import java.util.List;
import com.elusivehawk.engine.assets.Mesh;
import com.elusivehawk.engine.render.opengl.VertexBuffer;
import com.elusivehawk.engine.util.storage.Pair;
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
	protected Pair<VertexBuffer> fin = null;
	protected List<ModelSection> sections = Lists.newArrayList();
	
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
		if (this.sec != null)
		{
			this.sections.add(this.sec);
			this.sec = null;
			
			return;
		}
		
		throw new RenderException(String.format("Cannot add mesh to model section! Model: %s", this.name));
	}
	
	public Pair<VertexBuffer> finish() throws RenderException
	{
		return null;//FIXME
	}
	
}
