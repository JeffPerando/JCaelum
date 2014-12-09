
package com.elusivehawk.caelum.render.gl;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class VertexAttrib
{
	public final int index, size, type, stride;
	public final boolean unsigned, normalized;
	public final long first;
	
	@SuppressWarnings("unqualified-field-access")
	public VertexAttrib(int i, int length, int t, boolean u, boolean n, int off, long f)
	{
		index = i;
		size = length;
		type = t;
		unsigned = u;
		normalized = n;
		stride = off;
		first = f;
		
	}
	
}
