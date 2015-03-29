
package com.elusivehawk.caelum.render.gl;

import com.elusivehawk.util.MakeStruct;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
@MakeStruct
public class VertexAttrib
{
	public final int index, size, type, stride;
	public final boolean normalized;
	public final long first;
	
	@SuppressWarnings("unqualified-field-access")
	public VertexAttrib(int i, int length, int t, boolean n, int off, long f)
	{
		index = i;
		size = length;
		type = t;
		normalized = n;
		stride = off;
		first = f;
		
	}
	
}
