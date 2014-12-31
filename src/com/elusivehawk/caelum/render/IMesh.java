
package com.elusivehawk.caelum.render;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import com.elusivehawk.caelum.physics.Shape;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public interface IMesh
{
	public Shape getCullBox();
	
	public FloatBuffer getVertexData();
	
	public IntBuffer getIndices();
	
}
