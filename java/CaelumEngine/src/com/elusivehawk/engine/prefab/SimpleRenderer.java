
package com.elusivehawk.engine.prefab;

import com.elusivehawk.engine.render.ILogicalRender;
import com.elusivehawk.engine.render.opengl.GLEnumPolyType;
import com.elusivehawk.engine.render.opengl.GLProgram;
import com.elusivehawk.util.IPopulator;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class SimpleRenderer implements ILogicalRender
{
	private final GLProgram p = new GLProgram();
	
	private final int polyCount;
	private final GLEnumPolyType polyType;
	
	public SimpleRenderer(int polys, GLEnumPolyType type, IPopulator<GLProgram> pop)
	{
		this(polys, type);
		
		pop.populate(this.getProgram());
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public SimpleRenderer(int polys, GLEnumPolyType type)
	{
		polyCount = polys;
		polyType = type;
		
	}
	
	@Override
	public GLProgram getProgram()
	{
		return this.p;
	}
	
	@Override
	public GLEnumPolyType getPolygonType()
	{
		return this.polyType;
	}
	
	@Override
	public int getPolyCount()
	{
		return this.polyCount;
	}
	
}
