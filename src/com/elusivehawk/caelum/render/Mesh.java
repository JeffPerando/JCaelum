
package com.elusivehawk.caelum.render;

import java.io.DataInputStream;
import com.elusivehawk.caelum.assets.EnumAssetType;
import com.elusivehawk.caelum.render.gl.GLEnumDrawType;
import com.elusivehawk.caelum.render.gl.VertexArray;
import com.elusivehawk.util.IPopulator;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class Mesh extends GraphicAsset implements IPopulator<VertexArray>
{
	private int indices = 0, polyCount = 0;
	private GLEnumDrawType drawType = GLEnumDrawType.GL_TRIANGLES;
	
	public Mesh(String filepath)
	{
		super(filepath, EnumAssetType.MESH);
		
	}
	
	@Override
	public void delete(RenderContext rcon)
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void populate(VertexArray obj)
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	protected boolean readAsset(DataInputStream in) throws Throwable
	{
		// TODO Auto-generated method stub
		return false;
	}
	
	public int getIndiceCount()
	{
		return this.indices;
	}
	
	public int getPolyCount()
	{
		return this.polyCount;
	}
	
	public GLEnumDrawType getDrawType()
	{
		return this.drawType;
	}
	
}
