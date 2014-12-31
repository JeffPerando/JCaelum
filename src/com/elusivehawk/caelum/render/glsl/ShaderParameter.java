
package com.elusivehawk.caelum.render.glsl;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class ShaderParameter
{
	private final GLSLEnumInType inType;
	private final String name, type, dflt;
	
	public ShaderParameter(String t, String n)
	{
		this(GLSLEnumInType.UNIFORM, t, n);
		
	}
	
	public ShaderParameter(GLSLEnumInType inout, String t, String n)
	{
		this(inout, t, n, null);
		
	}
	
	public ShaderParameter(String t, String n, String defArg)
	{
		this(GLSLEnumInType.UNIFORM, t, n, defArg);
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public ShaderParameter(GLSLEnumInType inout, String t, String n, String defArg)
	{
		inType = inout;
		type = t;
		name = n;
		dflt = defArg;
		
	}
	
	@Override
	public String toString()
	{
		StringBuilder b = new StringBuilder();
		
		b.append(String.format("%s %s %s", this.inType, this.type, this.name));
		
		if (this.dflt != null)
		{
			b.append(String.format(" = %s", this.dflt));
			
		}
		
		b.append(";");
		
		return b.toString();
	}
	
	public GLSLEnumInType getInType()
	{
		return this.inType;
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public String getType()
	{
		return this.type;
	}
	
	public String getDefaultArg()
	{
		return this.dflt;
	}
	
}
