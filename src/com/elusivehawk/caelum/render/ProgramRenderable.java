
package com.elusivehawk.caelum.render;

import com.elusivehawk.caelum.render.gl.GL1;
import com.elusivehawk.caelum.render.gl.GL2;
import com.elusivehawk.caelum.render.gl.GLConst;
import com.elusivehawk.caelum.render.gl.GLProgram;
import com.elusivehawk.caelum.render.tex.Color;
import com.elusivehawk.caelum.render.tex.ITexture;
import com.elusivehawk.caelum.render.tex.Material;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public abstract class ProgramRenderable extends Renderable
{
	protected final GLProgram program;
	
	protected boolean zBuffer = true;
	
	@SuppressWarnings("unqualified-field-access")
	protected ProgramRenderable(GLProgram p)
	{
		assert p != null;
		
		program = p;
		
	}
	
	@Override
	public void preRender(RenderContext rcon)
	{
		super.preRender(rcon);
		
		/*if (this.program.bind(rcon))
		{
			//this.manipulateProgram(rcon);
			
			if (rcon.doUpdateScreenFlipUniform())
			{
				GL2.glUniform1("flip", rcon.isScreenFlipped());
				
			}
			
		}
		
		this.program.unbind(rcon);*/
		
	}
	
	@Override
	public void renderImpl(RenderContext rcon) throws RenderException
	{
		boolean zBuffer = GL1.glIsEnabled(GLConst.GL_DEPTH_TEST);
		
		if (zBuffer != this.zBuffer)
		{
			if (this.zBuffer)
			{
				GL1.glEnable(GLConst.GL_DEPTH_TEST);
				
			}
			else
			{
				GL1.glDisable(GLConst.GL_DEPTH_TEST);
				
			}
			
		}
		
		if (this.program.bind(rcon))
		{
			if (rcon.doUpdateCamera())
			{
				Camera cam = rcon.getCamera();
				
				GL2.glUniformMatrix4("view", cam.getView());
				GL2.glUniformMatrix4("proj", cam.getProjection());
				
			}
			
			this.doRender(rcon);
			
		}
		
		this.program.unbind(rcon);
		
	}
	
	public void setMaterial(Color col)
	{
		this.setMaterial(new Material().filter(col).lock());
		
	}
	
	public void setMaterial(ITexture tex)
	{
		this.setMaterial(new Material().tex(tex).lock());
		
	}
	
	public void setMaterial(ITexture tex, Color col)
	{
		this.setMaterial(new Material().tex(tex).filter(col).lock());
		
	}
	
	public void setMaterial(ITexture tex, boolean invert)
	{
		this.setMaterial(new Material().tex(tex).invert(invert).lock());
		
	}
	
	public synchronized ProgramRenderable setEnableZBuffer(boolean z)
	{
		this.zBuffer = z;
		
		return this;
	}
	
	protected abstract void doRender(RenderContext rcon) throws RenderException;
	
	public abstract void setMaterial(Material mat);
	
}
