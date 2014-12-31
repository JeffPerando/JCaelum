
package com.elusivehawk.caelum.render.tex;

import com.elusivehawk.caelum.render.IRenderable;
import com.elusivehawk.caelum.render.RenderContext;
import com.elusivehawk.caelum.render.RenderException;
import com.elusivehawk.util.IPopulator;
import com.elusivehawk.util.math.MathHelper;
import com.elusivehawk.util.parse.json.IJsonSerializer;
import com.elusivehawk.util.parse.json.JsonObject;
import com.elusivehawk.util.parse.json.JsonParseException;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public final class Material implements IJsonSerializer, IRenderable
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
	
	public Material(JsonObject json)
	{
		Object texture = json.getValue("tex");
		
		if (texture != null)
		{
			if (!(texture instanceof String))
			{
				throw new JsonParseException("Invalid value for JSON key \"tex\": %s", texture);
			}
			
			tex(new TextureAsset((String)texture));
			
		}
		
		Object glowTexture = json.getValue("glowTex");
		
		if (glowTexture != null)
		{
			if (!(glowTexture instanceof String))
			{
				throw new JsonParseException("Invalid value for JSON key \"glowTex\": %s", glowTexture);
			}
			
			glowTex(new TextureAsset((String)texture));
			
		}
		
		Object colorFilter = json.getValue("filter");
		
		if (colorFilter != null)
		{
			if (!(colorFilter instanceof JsonObject))
			{
				throw new JsonParseException("Invalid value for JSON key \"filter\": %s", colorFilter);
			}
			
			filter(new Color(ColorFormat.RGB, (JsonObject)colorFilter));
			
		}
		
		Object shininess = json.getValue("shine");
		
		if (shininess != null)
		{
			if (!(shininess instanceof Double))
			{
				throw new JsonParseException("Invalid value for JSON key \"shine\": %s", shininess);
			}
			
			shine(((Double)shininess).floatValue());
			
		}
		
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
	public String toJson(int tabs)
	{
		JsonObject ret = new JsonObject();
		
		ret.add("filter", this.filter);
		ret.add("shine", this.shine);
		
		if (this.tex instanceof IJsonSerializer)
		{
			ret.add("tex", this.tex);
			
		}
		
		if (this.glowTex instanceof IJsonSerializer)
		{
			ret.add("glowTex", this.glowTex);
			
		}
		
		return ret.toJson(tabs);
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
	
	@Override
	public boolean equals(Object obj)
	{
		if (!(obj instanceof Material))
		{
			return false;
		}
		
		Material m = (Material)obj;
		
		if (!this.filter().equals(m.filter()))
		{
			return false;
		}
		
		if (this.shine() != m.shine())
		{
			return false;
		}
		
		if (this.tex() != m.tex())
		{
			return false;
		}
		
		if (this.glowTex() != m.glowTex())
		{
			return false;
		}
		
		if (this.renTex() != m.renTex())
		{
			return false;
		}
		
		return true;
	}
	
	public Material filter(Color col)
	{
		assert col != null;
		
		if (!this.locked)
		{
			this.filter = col.convert(ColorFormat.RGB);
			
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
