
package com.elusivehawk.engine.render;

import com.elusivehawk.engine.Experimental;
import com.elusivehawk.util.math.Vector;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
@Experimental
public class Face
{
	public static final int 
			VERTEX = 0,
			TEX_COORD = 1,
			NORMAL = 2;
	
	private final Vector[] info = new Vector[9];
	
	@Override
	public boolean equals(Object obj)
	{
		if (!(obj instanceof Face))
		{
			return false;
		}
		
		Face f = (Face)obj;
		
		for (int c = 0; c < 9; c++)
		{
			if (!f.info[c].equals(this.info[c]))
			{
				return false;
			}
			
		}
		
		return true;
	}
	
	public Face setVector(int off, int i, Vector vtx)
	{
		this.info[i * 3 + off] = vtx;
		
		return this;
	}
	
	public Vector[] getVectors()
	{
		return this.info;
	}
	
}
