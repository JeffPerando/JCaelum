
package com.elusivehawk.caelum.prefab;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import com.elusivehawk.caelum.Experimental;
import com.elusivehawk.util.storage.BufferHelper;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
@Experimental
public class TerrainChunk
{
	private final int width, breadth;
	private final FloatBuffer heightmap, texOff;
	private final IntBuffer texId;
	
	@SuppressWarnings("unqualified-field-access")
	public TerrainChunk(int xsize, int zsize)
	{
		width = xsize;
		breadth = zsize;
		heightmap = BufferHelper.createFloatBuffer(xsize * zsize);
		texOff = BufferHelper.createFloatBuffer(xsize * zsize * 2);
		texId = BufferHelper.createIntBuffer(xsize * zsize);
		
		for (int x = 0; x < xsize; x++)
		{
			for (int z = 0; z < zsize; z++)
			{
				texOff.put(new float[]{(float)x / xsize, (float)z / zsize});
				
			}
			
		}
		
	}
	
	public int getWidth()
	{
		return this.width;
	}
	
	public int getBreadth()
	{
		return this.breadth;
	}
	
	public float getHeight(int x, int z)
	{
		return this.heightmap.get(x + (z * this.breadth));
	}
	
	public void setHeight(int x, float y, int z)
	{
		this.heightmap.put(x + (z * this.breadth), y);
		
	}
	
}
