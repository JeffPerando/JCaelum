
package com.elusivehawk.engine.render;

import com.elusivehawk.engine.render.tex.Color;
import com.elusivehawk.engine.render.tex.ITexture;
import com.elusivehawk.util.math.MathHelper;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class Material implements IRenderable
{
	protected ITexture tex = null;
	protected IRenderableTexture renTex = null;
	protected float shininess = 0f;
	protected Color filter = new Color();
	
	private boolean locked = false;
	
	public Material(){}
	
	@SuppressWarnings("unqualified-field-access")
	public Material(Material m)
	{
		tex = m.tex;
		shininess = m.shininess;
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
	
	public Material renTex(IRenderableTexture texture)
	{
		assert texture != null;
		
		if (!this.locked)
		{
			this.renTex = texture;
			
		}
		
		return this;
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
	
	public Material shine(float shine)
	{
		if (!this.locked)
		{
			this.shininess = MathHelper.clamp(shine, 0, 1f);
			
		}
		
		return this;
	}
	
	public Material tex(ITexture texture)
	{
		assert texture != null;
		
		if (!this.locked)
		{
			this.tex = texture;
			
		}
		
		return this;
	}
	
	public Material lock()
	{
		this.locked = true;
		
		return this;
	}
	
	public IRenderableTexture renTex()
	{
		return this.renTex;
	}
	
	public Color filter()
	{
		return this.filter;
	}
	
	public float shine()
	{
		return this.shininess;
	}
	
	public ITexture tex()
	{
		return this.tex;
	}
	
	public boolean isStatic()
	{
		return this.renTex != null || (this.tex != null && !this.tex.isAnimated());
	}
	
}
