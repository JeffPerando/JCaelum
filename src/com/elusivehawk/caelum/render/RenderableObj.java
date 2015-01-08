
package com.elusivehawk.caelum.render;

import com.elusivehawk.caelum.render.gl.GL1;
import com.elusivehawk.caelum.render.gl.GL2;
import com.elusivehawk.caelum.render.gl.GLConst;
import com.elusivehawk.caelum.render.gl.GLProgram;
import com.elusivehawk.caelum.render.gl.GLVertexArray;
import com.elusivehawk.caelum.render.tex.Color;
import com.elusivehawk.caelum.render.tex.ITexture;
import com.elusivehawk.caelum.render.tex.Material;
import com.elusivehawk.util.storage.DirtableStorage;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public abstract class RenderableObj implements /*IFilterable, */IRenderable
{
	protected final GLProgram program;
	
	protected final GLVertexArray vao = new GLVertexArray();
	protected final DirtableStorage<Material> mat = new DirtableStorage<Material>(null).setSync();
	
	protected boolean initiated = false, zBuffer = true;
	
	//protected Filters filters = null;
	
	protected int renderCount = 0;
	
	protected RenderableObj()
	{
		this(new GLProgram());
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	protected RenderableObj(GLProgram p)
	{
		program = p;
		
	}
	
	@Override
	public void render(RenderContext rcon) throws RenderException
	{
		if (!this.initiated)
		{
			return;
		}
		
		ICamera cam = rcon.getCamera();
		
		if (this.isCulled(cam))
		{
			return;
		}
		
		if (this.renderCount == RenderConst.RECURSIVE_LIMIT)
		{
			return;
		}
		
		this.renderCount++;
		
		if (!this.mat.isNull())
		{
			this.mat.get().render(rcon);
			
		}
		
		boolean zBuffer = GL2.glIsEnabled(GLConst.GL_DEPTH_TEST);
		
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
			if (this.vao.bind(rcon))
			{
				if (rcon.doUpdateCamera())
				{
					GL2.glUniformMatrix4fv("view", cam.getView().asBuffer());
					GL2.glUniformMatrix4fv("proj", cam.getProjection().asBuffer());
					
				}
				
				if (!this.mat.isNull())
				{
					if (this.mat.isDirty())
					{
						this.updateMatUniforms(rcon);
						
						this.mat.setIsDirty(false);
						
					}
					else if (!this.mat.get().isStatic())
					{
						this.updateMatUniforms(rcon);
						
					}
					
				}
				
				this.updateMatUniforms(rcon);
				
				this.doRender(rcon);
				
				rcon.releaseTextures();
				
			}
			
			this.vao.unbind(rcon);
			
		}
		
		this.program.unbind(rcon);
		
		this.renderCount--;
		
	}
	
	@Override
	public void preRender(RenderContext rcon, double delta)
	{
		if (!this.initiated)
		{
			if (!this.initiate(rcon))
			{
				return;
			}
			
			this.initiated = true;
			
		}
		
		if (!this.mat.isNull())
		{
			this.mat.get().preRender(rcon, delta);
			
		}
		
		if (this.program.bind(rcon))
		{
			//this.manipulateProgram(rcon);
			
			if (rcon.doUpdateScreenFlipUniform())
			{
				GL2.glUniform1i("flip", rcon.isScreenFlipped() ? 1 : 0);
				
			}
			
		}
		
		this.program.unbind(rcon);
		
	}
	
	@Override
	public void postRender(RenderContext rcon)
	{
		if (!this.mat.isNull())
		{
			this.mat.get().postRender(rcon);
			
		}
		
	}
	
	/*@Override
	public int addFilter(UUID type, IFilter f)
	{
		if (this.filters == null)
		{
			this.setFilters(new Filters());
			
		}
		
		return this.filters.addFilter(type, f);
	}
	
	@Override
	public void removeFilter(UUID type, IFilter f)
	{
		if (this.filters != null)
		{
			this.filters.removeFilter(type, f);
			
		}
		
	}
	
	@Override
	public void removeFilter(UUID type, int i)
	{
		if (this.filters != null)
		{
			this.filters.removeFilter(type, i);
			
		}
		
	}
	
	public synchronized RenderableObj setFilters(Filters fs)
	{
		assert fs != null;
		
		this.filters = fs;
		
		return this;
	}*/
	
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
	
	public void setMaterial(Material m)
	{
		assert m.locked();
		
		this.mat.set(m);
		
	}
	
	public synchronized RenderableObj setEnableZBuffer(boolean z)
	{
		this.zBuffer = z;
		
		return this;
	}
	
	public boolean isCulled(ICamera cam)
	{
		return false;
	}
	
	private void updateMatUniforms(RenderContext rcon)
	{
		Material m = this.mat.get();
		
		GL2.glUniform1i("mat.tex", rcon.bindTexture(m.tex()));
		GL2.glUniform1i("mat.renTex", rcon.bindTexture(m.renTex()));
		GL2.glUniform1i("mat.glowTex", rcon.bindTexture(m.glowTex()));
		GL2.glUniform4f("mat.filter", m.filter().asFloats());
		GL2.glUniform1f("mat.shine", m.shine());
		GL2.glUniform1i("mat.invert", m.invert() ? 1 : 0);
		
	}
	
	protected abstract boolean initiate(RenderContext rcon);
	
	protected abstract void doRender(RenderContext rcon) throws RenderException;
	
}
