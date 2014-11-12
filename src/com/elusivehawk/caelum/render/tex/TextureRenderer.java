
package com.elusivehawk.caelum.render.tex;

import com.elusivehawk.caelum.render.IDisplay;
import com.elusivehawk.caelum.render.IRenderable;
import com.elusivehawk.caelum.render.RenderContext;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class TextureRenderer extends RenderableTexture
{
	private final IRenderable renderer;
	
	@SuppressWarnings("unqualified-field-access")
	public TextureRenderer(IRenderable r)
	{
		assert r != null;
		
		renderer = r;
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public TextureRenderer(IRenderable r, boolean depth)
	{
		super(depth);
		
		assert r != null;
		
		renderer = r;
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public TextureRenderer(IRenderable r, IDisplay display)
	{
		super(display);
		
		assert r != null;
		
		renderer = r;
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public TextureRenderer(IRenderable r, IDisplay display, boolean depth)
	{
		super(display, depth);
		
		assert r != null;
		
		renderer = r;
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public TextureRenderer(IRenderable r, int width, int height, boolean depth)
	{
		super(width, height, depth);
		
		assert r != null;
		
		renderer = r;
		
	}
	
	@Override
	public void preRender(RenderContext rcon, double delta)
	{
		this.renderer.preRender(rcon, delta);
		
	}
	
	@Override
	public void renderTexture(RenderContext rcon)
	{
		this.renderer.render(rcon);
		
	}
	
	@Override
	public void postRender(RenderContext rcon)
	{
		super.postRender(rcon);
		
		this.renderer.postRender(rcon);
		
	}
	
}
