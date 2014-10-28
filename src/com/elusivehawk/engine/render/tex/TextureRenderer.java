
package com.elusivehawk.engine.render.tex;

import com.elusivehawk.engine.render.IDisplay;
import com.elusivehawk.engine.render.IRenderable;
import com.elusivehawk.engine.render.RenderContext;

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
		renderer = r;
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public TextureRenderer(IRenderable r, boolean depth)
	{
		super(depth);
		
		renderer = r;
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public TextureRenderer(IRenderable r, IDisplay display)
	{
		super(display);
		
		renderer = r;
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public TextureRenderer(IRenderable r, IDisplay display, boolean depth)
	{
		super(display, depth);
		
		renderer = r;
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public TextureRenderer(IRenderable r, int width, int height, boolean depth)
	{
		super(width, height, depth);
		
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
