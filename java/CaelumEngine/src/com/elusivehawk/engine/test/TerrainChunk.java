
package com.elusivehawk.engine.test;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import com.elusivehawk.engine.EnumEngineFeature;
import com.elusivehawk.util.BufferHelper;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
@IntendedFor({EnumEngineFeature.PHYSICS, EnumEngineFeature.RENDER})
public class TerrainChunk
{
	protected final int size;
	protected final float calcSize;
	protected final FloatBuffer terrain, texOff;
	protected final IntBuffer tex;
	
	@SuppressWarnings("unqualified-field-access")
	public TerrainChunk(int quality)
	{
		size = quality;
		calcSize = 1.0f / size;
		
		terrain = BufferHelper.createFloatBuffer(quality * quality * 3);
		texOff = BufferHelper.createFloatBuffer(quality * quality * 2);
		tex = BufferHelper.createIntBuffer(quality * quality);
		
		for (int x = 0; x < size; x++)
		{
			for (int z = 0; z < size; z++)
			{
				terrain.put(new float[]{x * calcSize, 0f, z * calcSize});
				texOff.put(new float[]{x * calcSize, z * calcSize});
				
			}
			
		}
		
	}
	
	public int getIndex(int x, int z)
	{
		return (x * this.size) + z;
	}
	
	public void setHeight(int x, int z, float y)
	{
		this.terrain.put(this.getIndex(x, z), y);
		
	}
	
}
