
package com.elusivehawk.caelum.render;

import java.io.DataInputStream;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import com.elusivehawk.caelum.assets.EnumAssetType;
import com.elusivehawk.caelum.physics.Shape;
import com.elusivehawk.caelum.render.gl.GLEnumDrawType;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class MeshAsset extends GraphicAsset implements IMesh
{
	private int indices = 0, polyCount = 0;
	private GLEnumDrawType drawType = GLEnumDrawType.GL_TRIANGLES;
	
	public MeshAsset(String filepath)
	{
		super(filepath, EnumAssetType.MESH);
		
	}
	
	@Override
	public void delete(RenderContext rcon)
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public Shape getCullBox()
	{
		return null;
	}
	
	@Override
	public FloatBuffer getVertexData()
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public IntBuffer getIndices()
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	protected boolean readAsset(DataInputStream in) throws Throwable
	{
		// TODO Auto-generated method stub
		return false;
	}
	
}
