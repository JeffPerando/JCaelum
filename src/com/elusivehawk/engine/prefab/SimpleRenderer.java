
package com.elusivehawk.engine.prefab;

import com.elusivehawk.engine.render.ILogicalRender;
import com.elusivehawk.engine.render.opengl.GLEnumPolyType;
import com.elusivehawk.engine.render.opengl.GLProgram;
import com.elusivehawk.engine.render.opengl.VertexArray;
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
	private final VertexArray vao = new VertexArray();
	
	private final int polyCount;
	private final GLEnumPolyType polyType;
	
	public SimpleRenderer(int polys, GLEnumPolyType type, IPopulator<GLProgram> glpop, IPopulator<VertexArray> vaopop)
	{
		this(polys, type);
		
		glpop.populate(this.p);
		vaopop.populate(this.vao);
		
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
	public VertexArray getVAO()
	{
		return this.vao;
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
