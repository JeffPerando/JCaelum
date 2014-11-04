
package com.elusivehawk.caelum.render.gl;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class VertexAttrib
{
	public final String name;
	public final int size, type, stride;
	public final boolean unsigned, normalized;
	public final long first;
	
	@SuppressWarnings("unqualified-field-access")
	public VertexAttrib(String attrib, int length, int t, boolean u, boolean n, int off, long f)
	{
		name = attrib;
		size = length;
		type = t;
		unsigned = u;
		normalized = n;
		stride = off;
		first = f;
		
	}
	
}
