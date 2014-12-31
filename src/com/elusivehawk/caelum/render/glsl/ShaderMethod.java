
package com.elusivehawk.caelum.render.glsl;

import java.io.File;
import java.util.List;
import com.elusivehawk.util.IPopulator;
import com.elusivehawk.util.io.IOHelper;
import com.elusivehawk.util.parse.ParseHelper;
import com.google.common.collect.Lists;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class ShaderMethod
{
	private final String name, type;
	
	private final List<ShaderParameter> args = Lists.newArrayList();
	private final List<String> code = Lists.newArrayList();
	
	public ShaderMethod(String methodName)
	{
		this(methodName, "void");
		
	}
	
	public ShaderMethod(String methodName, IPopulator<ShaderMethod> pop)
	{
		this(methodName);
		
		pop.populate(this);
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public ShaderMethod(String methodName, String retType)
	{
		name = methodName;
		type = retType;
		
	}
	
	public ShaderMethod(String methodName, String retType, IPopulator<ShaderMethod> pop)
	{
		this(methodName, retType);
		
		pop.populate(this);
		
	}
	
	@Override
	public String toString()
	{
		StringBuilder b = new StringBuilder();
		
		b.append(String.format("%s %s(", this.type, this.name));
		
		boolean argPrev = false;
		
		if (this.args.isEmpty())
		{
			b.append("void");
			
		}
		else
		{
			for (ShaderParameter arg : this.args)
			{
				if (argPrev)
				{
					b.append(", ");
					
				}
				else argPrev = true;
				
				b.append(arg.toString());
				
			}
			
		}
		
		b.append(")\n{");
		
		for (String src : this.code)
		{
			b.append("\t");
			b.append(src);
			
		}
		
		b.append("}");
		
		return b.toString();
	}
	
	public ShaderMethod arg(ShaderParameter param)
	{
		assert param != null;
		assert param.getInType() != GLSLEnumInType.UNIFORM;
		
		this.args.add(param);
		
		return this;
	}
	
	public ShaderMethod code(File src)
	{
		return this.code(IOHelper.readTextToOneLine(src));
	}
	
	public ShaderMethod code(List<String> src)
	{
		return this.code(ParseHelper.concat(src, "\n", null));
	}
	
	public ShaderMethod code(String[] src)
	{
		return this.code(ParseHelper.concat("\n", null, src));
	}
	
	public ShaderMethod code(String src)
	{
		assert src != null;
		
		this.code.add(src);
		
		return this;
	}
	
}
