
package com.elusivehawk.engine.render;

import java.util.List;
import com.elusivehawk.engine.assets.Mesh;
import com.google.common.collect.Lists;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class ModelSection
{
	public final String name;
	private final List<Mesh> meshes = Lists.newArrayList();
	
	@SuppressWarnings("unqualified-field-access")
	ModelSection(String title)
	{
		name = title;
		
	}
	
	public void addMesh(Mesh m)
	{
		this.meshes.add(m);
		
	}
	
}
