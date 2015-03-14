
package com.elusivehawk.caelum.render.tex;

import com.elusivehawk.caelum.CaelumException;
import com.elusivehawk.caelum.IDisposable;
import com.elusivehawk.caelum.assets.Asset;
import com.elusivehawk.caelum.render.IBindable;
import com.elusivehawk.caelum.render.IRenderer;
import com.elusivehawk.caelum.render.RenderContext;
import com.elusivehawk.caelum.render.RenderException;
import com.elusivehawk.caelum.render.gl.GL2;
import com.elusivehawk.util.IPopulator;
import com.elusivehawk.util.math.MathHelper;
import com.elusivehawk.util.parse.json.IJsonSerializer;
import com.elusivehawk.util.parse.json.JsonObject;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public final class Material implements IDisposable, IRenderer, IBindable, IJsonSerializer
{
	private ITexture tex = null, glowTex = null;
	private RenderableTexture renTex = null;
	private float shine = 0f;
	private Color filter = Color.BLACK.convertTo(ColorFormat.RGBA);
	
	private boolean locked = false, invert = false, initiated = false, bound = false;
	private int texCount = 0, texBind = -1, renBind = -1, glowBind = -1;
	
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
				throw new CaelumException("Invalid value for JSON key \"tex\": %s", texture);
			}
			
			tex(new TextureAsset((String)texture));
			
		}
		
		Object glowTexture = json.getValue("glowTex");
		
		if (glowTexture != null)
		{
			if (!(glowTexture instanceof String))
			{
				throw new CaelumException("Invalid value for JSON key \"glowTex\": %s", glowTexture);
			}
			
			glowTex(new TextureAsset((String)texture));
			
		}
		
		Object colorFilter = json.getValue("filter");
		
		if (colorFilter != null)
		{
			if (!(colorFilter instanceof JsonObject))
			{
				throw new CaelumException("Invalid value for JSON key \"filter\": %s", colorFilter);
			}
			
			filter(new Color(ColorFormat.RGBA, (JsonObject)colorFilter));
			
		}
		
		Object shininess = json.getValue("shine");
		
		if (shininess != null)
		{
			if (!(shininess instanceof Double))
			{
				throw new CaelumException("Invalid value for JSON key \"shine\": %s", shininess);
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
	public void dispose(Object... args)
	{
		if (this.tex != null && !(this.tex instanceof Asset))
		{
			this.tex.dispose(args);
			
		}
		
		if (this.renTex != null)
		{
			this.renTex.dispose(args);
			
		}
		
		if (this.glowTex != null && !(this.glowTex instanceof Asset))
		{
			this.glowTex.dispose(args);
			
		}
		
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
	public boolean bind(RenderContext rcon)
	{
		if (!this.locked())
		{
			return false;
		}
		
		if (this.bound)
		{
			return false;
		}
		
		int t = rcon.bindTexture(this.tex);
		int r = rcon.bindTexture(this.renTex);
		int g = rcon.bindTexture(this.glowTex);
		
		if (!this.initiated)
		{
			GL2.glUniform1("mat.tex", (this.texBind = t));
			GL2.glUniform1("mat.renTex", (this.renBind = r));
			GL2.glUniform1("mat.glowTex", (this.glowBind = g));
			
			GL2.glUniform4("mat.filter", this.filter.asFloats());
			GL2.glUniform1("mat.shine", this.shine);
			GL2.glUniform1("mat.invert", this.invert ? 1 : 0);
			
			this.initiated = true;
			
		}
		
		if (t != this.texBind)
		{
			GL2.glUniform1("mat.tex", (this.texBind = t));
			
		}
		
		if (r != this.renBind)
		{
			GL2.glUniform1("mat.renTex", (this.renBind = r));
			
		}
		
		if (g != this.glowBind)
		{
			GL2.glUniform1("mat.glowTex", (this.glowBind = g));
			
		}
		
		this.bound = true;
		
		return false;
	}
	
	@Override
	public void unbind(RenderContext rcon)
	{
		this.bound = false;
		
	}
	
	@Override
	public boolean isBound(RenderContext rcon)
	{
		return this.bound;
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
	public void preRender(RenderContext rcon)
	{
		if (this.tex != null)
		{
			this.tex.preRender(rcon);
			
		}
		
		if (this.glowTex != null)
		{
			this.glowTex.preRender(rcon);
			
		}
		
		if (this.renTex != null)
		{
			this.renTex.preRender(rcon);
			
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
		ret *= 31 + (this.glowTex == null ? 0 : this.glowTex.hashCode());
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
			if (!col.isImmutable())
			{
				col = col.clone().setImmutable();
				
			}
			
			this.filter = col.convertTo(ColorFormat.RGBA);
			
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
	
	public Material invert(boolean b)
	{
		if (!this.locked)
		{
			this.invert = b;
			
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
				this.shine(1f);
				
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
	
	public boolean invert()
	{
		return this.invert;
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
	
	public boolean locked()
	{
		return this.locked;
	}
	
	public boolean isStatic()
	{
		if (this.renTex != null)
		{
			return false;
		}
		
		if (this.tex != null)
		{
			return this.tex.isStatic();
		}
		
		if (this.glowTex != null)
		{
			return this.glowTex.isStatic();
		}
		
		return true;
	}
	
}
