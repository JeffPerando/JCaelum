
package com.elusivehawk.engine.render;

import com.elusivehawk.engine.core.CaelumEngine;
import com.elusivehawk.engine.render.opengl.GLConst;
import com.elusivehawk.engine.render.opengl.IGL1;
import com.elusivehawk.engine.render.opengl.IGL2;
import com.elusivehawk.engine.render.opengl.IGL3;
import com.elusivehawk.engine.util.FileHelper;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public final class RenderContext
{
	private final IRenderHUB hub;
	
	private final IGL1 gl1;
	private final IGL2 gl2;
	private final IGL3 gl3;
	
	private final int sVertex, sFrag;
	
	@SuppressWarnings("unqualified-field-access")
	public RenderContext(IRenderHUB renderhub)
	{
		hub = renderhub;
		
		gl1 = (IGL1)CaelumEngine.renderEnvironment().getGL(IRenderEnvironment.GL_1);
		gl2 = (IGL2)CaelumEngine.renderEnvironment().getGL(IRenderEnvironment.GL_2);
		gl3 = (IGL3)CaelumEngine.renderEnvironment().getGL(IRenderEnvironment.GL_3);
		
		sVertex = RenderHelper.loadShader(FileHelper.createFile("/vertex.glsl"), GLConst.GL_VERTEX_SHADER, this);
		sFrag = RenderHelper.loadShader(FileHelper.createFile("/fragment.glsl"), GLConst.GL_FRAGMENT_SHADER, this);
		
	}
	
	public IRenderHUB getHub()
	{
		return this.hub;
	}
	
	public IGL1 getGL1()
	{
		return this.gl1;
	}
	
	public IGL2 getGL2()
	{
		return this.gl2;
	}
	
	public IGL3 getGL3()
	{
		return this.gl3;
	}
	
	public int getDefaultFragmentShader()
	{
		return this.sFrag;
	}
	
	public int getDefaultVertexShader()
	{
		return this.sVertex;
	}
	
}
