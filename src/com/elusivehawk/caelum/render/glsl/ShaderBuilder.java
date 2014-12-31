
package com.elusivehawk.caelum.render.glsl;

import java.util.List;
import com.elusivehawk.util.IPopulator;
import com.elusivehawk.util.storage.Pair;
import com.google.common.collect.Lists;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class ShaderBuilder implements IPopulator<ShaderBuilder>
{
	private final int version;
	private final boolean compat;
	
	private final List<Pair<String>> constants = Lists.newArrayList();
	private final List<ShaderParameter> params = Lists.newArrayList();
	private final List<ShaderMethod> methods = Lists.newArrayList();
	
	public ShaderBuilder()
	{
		this(330);
		
	}
	
	public ShaderBuilder(IPopulator<ShaderBuilder> pop)
	{
		this();
		
		pop.populate(this);
		
	}
	
	public ShaderBuilder(int v)
	{
		this(v, false);
		
	}
	
	public ShaderBuilder(int v, IPopulator<ShaderBuilder> pop)
	{
		this(v, false);
		
		pop.populate(this);
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public ShaderBuilder(int v, boolean compatibility)
	{
		version = v;
		compat = compatibility;
		
	}
	
	public ShaderBuilder(int v, boolean compatibility, IPopulator<ShaderBuilder> pop)
	{
		this(v, compatibility);
		
		pop.populate(this);
		
	}
	
	@Override
	public void populate(ShaderBuilder b)
	{
		this.constants.forEach(((cnst) -> {b.addConst(cnst);}));
		this.params.forEach(((param) -> {b.addParam(param);}));
		this.methods.forEach(((method) -> {b.addMethod(method);}));
		
	}
	
	@Override
	public String toString()
	{
		StringBuilder b = new StringBuilder();
		
		b.append(String.format("#version %s %s\n\n", this.version, this.compat ? "compatibility" : "core"));
		
		this.constants.forEach(((cnst) ->
		{
			b.append(String.format("#define %s %s\n", cnst.one, cnst.two));
			
		}));
		
		b.append("\n");
		
		this.params.forEach(((param) ->
		{
			b.append(param.toString());
			
		}));
		
		b.append("\n");
		
		
		this.methods.forEach(((method) ->
		{
			b.append(method.toString());
			
		}));
		
		b.append("\n");
		
		return b.toString();
	}
	
	public ShaderBuilder addConst(String name, String value)
	{
		return this.addConst(Pair.createPair(name, value));
	}
	
	public ShaderBuilder addConst(Pair<String> cnst)
	{
		assert cnst != null;
		assert cnst.one != null;
		
		this.constants.add(cnst);
		
		return this;
	}
	
	public ShaderBuilder addParam(ShaderParameter param)
	{
		assert param != null;
		assert param.getInType() != GLSLEnumInType.INOUT;
		
		this.params.add(param);
		
		return this;
	}
	
	public ShaderBuilder addParam(ShaderParameter[] parameters)
	{
		for (ShaderParameter param : parameters)
		{
			this.addParam(param);
			
		}
		
		return this;
	}
	
	public ShaderBuilder addMethod(ShaderMethod method)
	{
		assert method != null;
		
		this.methods.add(method);
		
		return this;
	}
	
}
