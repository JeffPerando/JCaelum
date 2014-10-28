
package com.elusivehawk.engine.render;

import com.elusivehawk.engine.assets.EnumAssetType;
import com.elusivehawk.engine.render.gl.GLEnumDrawType;
import com.elusivehawk.engine.render.gl.VertexArray;
import com.elusivehawk.util.IPopulator;
import com.elusivehawk.util.io.IByteReader;

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
	protected void finishGPULoading(RenderContext rcon)
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	protected boolean readAsset(IByteReader r) throws Throwable
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
