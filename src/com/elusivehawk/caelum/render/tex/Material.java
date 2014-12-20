
package com.elusivehawk.caelum.render.tex;

import com.elusivehawk.caelum.render.IRenderable;
import com.elusivehawk.caelum.render.RenderContext;
import com.elusivehawk.caelum.render.RenderException;
import com.elusivehawk.util.IPopulator;
import com.elusivehawk.util.math.MathHelper;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public final class Material implements IRenderable
{
	private ITexture tex = null, glowTex = null;
	private RenderableTexture renTex = null;
	private float shine = 0f;
	private Color filter = Color.WHITE;
	
	private boolean locked = false;
	private int texCount = 0;
	
	public Material(){}
	
	public Material(IPopulator<Material> pop)
	{
		pop.populate(this);
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public Material(Material m)
	{
		tex = m.tex;
		glowTex = m.glowTex;
		renTex = m.renTex;
		shine = m.shine;
		filter = m.filter;
		
	}
	
	@Override
	public void render(RenderContext rcon) throws RenderException
	{
		if (this.renTex != null)
		{
			this.renTex.render(rcon);
			
		}
		
	}
	
	@Override
	public void preRender(RenderContext rcon, double delta)
	{
		if (this.tex != null)
		{
			this.tex.preRender(rcon, delta);
			
		}
		
		if (this.glowTex != null)
		{
			this.glowTex.preRender(rcon, delta);
			
		}
		
		if (this.renTex != null)
		{
			this.renTex.preRender(rcon, delta);
			
		}
		
	}
	
	@Override
	public void postRender(RenderContext rcon)
	{
		if (this.renTex != null)
		{
			this.renTex.postRender(rcon);
			
		}
		
	}
	
	@Override
	public Material clone()
	{
		return new Material(this);
	}
	
	@Override
	public int hashCode()
	{
		int ret = 31;
		
		ret *= 31 + (this.tex == null ? 0 : this.tex.hashCode());
		ret *= 31 + (this.renTex == null ? 0 : this.renTex.hashCode());
		ret *= 31 + (Float.floatToRawIntBits(this.shine));
		ret *= 31 + (this.filter == null ? 0 : this.filter.hashCode());
		
		return ret;
	}
	
	public Material filter(Color col)
	{
		assert col != null;
		
		if (!this.locked)
		{
			this.filter = col;
			
		}
		
		return this;
	}
	
	public Material glowTex(ITexture glow)
	{
		assert glow != null;
		
		if (!this.locked)
		{
			if (this.glowTex == null)
			{
				this.texCount++;
				
			}
			
			this.glowTex = glow;
			
		}
		
		return this;
	}
	
	public Material renTex(RenderableTexture texture)
	{
		assert texture != null;
		
		if (!this.locked)
		{
			if (this.renTex == null)
			{
				this.texCount++;
				
			}
			
			this.renTex = texture;
			
			if (this.shine == 0f)
			{
				this.shine = 1f;
				
			}
			
		}
		
		return this;
	}
	
	public Material shine(float shininess)
	{
		if (!this.locked)
		{
			this.shine = MathHelper.clamp(shininess, 0, 1f);
			
		}
		
		return this;
	}
	
	public Material tex(ITexture texture)
	{
		assert texture != null;
		
		if (!this.locked)
		{
			if (this.tex == null)
			{
				this.texCount++;
				
			}
			
			this.tex = texture;
			
		}
		
		return this;
	}
	
	public Material lock()
	{
		this.locked = true;
		
		return this;
	}
	
	public Color filter()
	{
		return this.filter;
	}
	
	public ITexture glowTex()
	{
		return this.glowTex;
	}
	
	public RenderableTexture renTex()
	{
		return this.renTex;
	}
	
	public float shine()
	{
		return this.shine;
	}
	
	public ITexture tex()
	{
		return this.tex;
	}
	
	public int texCount()
	{
		return this.texCount;
	}
	
	public boolean isStatic()
	{
		return this.renTex == null && (this.tex != null && this.tex.isStatic());
	}
	
}
