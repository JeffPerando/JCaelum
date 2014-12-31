
package com.elusivehawk.caelum.render;

import java.util.Map;
import com.elusivehawk.caelum.render.gl.GL1;
import com.elusivehawk.caelum.render.gl.GL2;
import com.elusivehawk.caelum.render.gl.GLConst;
import com.elusivehawk.caelum.render.gl.GLProgram;
import com.elusivehawk.caelum.render.gl.GLVertexArray;
import com.elusivehawk.caelum.render.tex.ITexture;
import com.elusivehawk.caelum.render.tex.Material;
import com.elusivehawk.caelum.render.tex.MaterialSet;
import com.google.common.collect.Maps;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public abstract class RenderableObj implements /*IFilterable, */IRenderable
{
	private final Map<ITexture, Integer> boundTex = Maps.newHashMap();
	
	protected final GLProgram p;
	
	protected final GLVertexArray vao = new GLVertexArray();
	protected final MaterialSet mats = new MaterialSet();
	
	protected boolean initiated = false, zBuffer = true;
	
	//protected Filters filters = null;
	
	protected int renderCount = 0, texCount = 0;
	
	protected RenderableObj()
	{
		this(new GLProgram());
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	protected RenderableObj(GLProgram program)
	{
		assert program != null;
		
		p = program;
		
	}
	
	@Override
	public void render(RenderContext rcon) throws RenderException
	{
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
		
		if (!this.initiated)
		{
			if (!this.initiate(rcon))
			{
				return;
			}
			
			this.initiated = true;
			
		}
		
		this.mats.forEach(((m) -> {m.render(rcon);}));
		
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
		
		if (this.p.bind(rcon))
		{
			if (this.vao.bind(rcon))
			{
				if (rcon.doUpdateCamera())
				{
					GL2.glUniformMatrix4fv("view", cam.getView().asBuffer());
					GL2.glUniformMatrix4fv("proj", cam.getProjection().asBuffer());
					
				}
				
				if (this.mats.isDirty())
				{
					GL2.glUniform1i("matCount", this.mats.size());
					
					this.mats.setIsDirty(false);
					
				}
				
				this.updateMatUniforms(rcon);
				
				this.doRender(rcon);
				
				if (this.texCount > 0)
				{
					this.boundTex.forEach(((tex, slot) ->
					{
						GL1.glActiveTexture(slot);
						GL1.glBindTexture(tex.getType(), 0);
						
					}));
					
					this.boundTex.clear();
					this.texCount = 0;
					
				}
				
			}
			
			this.vao.unbind(rcon);
			
		}
		
		this.p.unbind(rcon);
		
		this.renderCount--;
		
	}
	
	@Override
	public void preRender(RenderContext rcon, double delta)
	{
		this.mats.forEach(((mat) -> {mat.preRender(rcon, delta);}));
		
		if (this.p.bind(rcon))
		{
			//this.manipulateProgram(rcon);
			
			if (rcon.updateScreenFlipUniform())
			{
				GL2.glUniform1i("flip", rcon.isScreenFlipped() ? 1 : 0);
				
			}
			
		}
		
		this.p.unbind(rcon);
		
	}
	
	@Override
	public void postRender(RenderContext rcon)
	{
		this.mats.forEach(((mat) -> {mat.postRender(rcon);}));
		
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
	
	public void addMaterials(Material... ms)
	{
		int c = 0;
		
		while (this.mats.size() < RenderConst.MATERIAL_CAP && c < ms.length)
		{
			this.mats.add(ms[c++]);
			
		}
		
	}
	
	public int addMaterial(Material m)
	{
		return this.mats.add(m);
	}
	
	public synchronized RenderableObj setEnableZBuffer(boolean z)
	{
		this.zBuffer = z;
		
		return this;
	}
	
	public int getMaterialCount()
	{
		return this.mats.size();
	}
	
	public boolean isCulled(ICamera cam)
	{
		return false;
	}
	
	private void updateMatUniforms(RenderContext rcon)
	{
		int c = 0;
		
		for (Material m : this.mats)
		{
			GL2.glUniform1i(String.format("texes[%s]", c), this.bindTexture(rcon, m.tex()));
			GL2.glUniform1i(String.format("rTexes[%s]", c), this.bindTexture(rcon, m.renTex()));
			GL2.glUniform1i(String.format("gTexes[%s]", c), this.bindTexture(rcon, m.glowTex()));
			GL2.glUniform3f(String.format("filters[%s]", c), m.filter().asFloats());
			GL2.glUniform1f(String.format("shines[%s]", c), m.shine());
			
			c++;
			
		}
		
	}
	
	private int bindTexture(RenderContext rcon, ITexture tex)
	{
		if (tex == null)
		{
			return 0;
		}
		
		GL1.glActiveTexture(GLConst.GL_TEXTURE0 + this.texCount);
		GL1.glBindTexture(tex);
		
		this.boundTex.put(tex, this.texCount);
		this.texCount++;
		
		return this.texCount - 1;
	}
	
	protected abstract boolean initiate(RenderContext rcon);
	
	protected abstract void doRender(RenderContext rcon) throws RenderException;
	
}
