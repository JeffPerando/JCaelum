
package com.elusivehawk.caelum.prefab;

import com.elusivehawk.caelum.render.IRenderable;
import com.elusivehawk.caelum.render.RenderContext;
import com.elusivehawk.util.IPopulator;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class RenderComponent extends Component
{
	protected final IRenderable renderable;
	
	public RenderComponent(IRenderable r)
	{
		this(0, r);
		
	}
	
	public RenderComponent(IRenderable r, IPopulator<Component> pop)
	{
		this(0, r);
		
		pop.populate(this);
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public RenderComponent(int p, IRenderable r)
	{
		super(p);
		
		assert r != null;
		
		renderable = r;
		
	}
	
	public RenderComponent(int p, IRenderable r, IPopulator<Component> pop)
	{
		this(p, r);
		
		pop.populate(this);
		
	}
	
	@Override
	public void preRender(RenderContext rcon)
	{
		this.renderable.preRender(rcon);
		
		super.preRender(rcon);
		
	}
	
	@Override
	public void postRender(RenderContext rcon)
	{
		this.renderable.postRender(rcon);
		
		super.postRender(rcon);
		
	}
	
	@Override
	public void delete(RenderContext rcon)
	{
		this.renderable.delete(rcon);
		
		super.delete(rcon);
		
	}
	
	@Override
	public void render(RenderContext rcon)
	{
		this.renderable.render(rcon);
		
		super.render(rcon);
		
	}
	
}
