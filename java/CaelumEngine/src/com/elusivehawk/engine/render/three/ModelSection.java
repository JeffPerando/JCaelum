
package com.elusivehawk.engine.render.three;

import java.util.Iterator;
import java.util.List;
import com.elusivehawk.engine.assets.Mesh;
import com.google.common.collect.Lists;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class ModelSection implements Iterable<Mesh>
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
