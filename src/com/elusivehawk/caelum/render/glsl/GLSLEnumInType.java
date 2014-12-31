
package com.elusivehawk.caelum.render.glsl;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public enum GLSLEnumInType
{
	UNIFORM, IN, OUT, INOUT;
	
	@Override
	public String toString()
	{
		return this.name().toLowerCase();
	}
	
}
