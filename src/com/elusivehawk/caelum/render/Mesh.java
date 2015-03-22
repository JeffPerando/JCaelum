
package com.elusivehawk.caelum.render;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class Mesh implements IMeshDataProvider
{
	private final MeshData data;
	
	@SuppressWarnings("unqualified-field-access")
	public Mesh(MeshData md)
	{
		assert md != null;
		
		data = md;
		
	}
	
	@Override
	public MeshData getData()
	{
		return this.data;
	}
	
}
