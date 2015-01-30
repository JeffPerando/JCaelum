
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
	
	public RenderComponent(Component parent, IRenderable r)
	{
		this(parent, 0, r);
		
	}
	
	public RenderComponent(Component parent, IRenderable r, IPopulator<Component> pop)
	{
		this(parent, 0, r, pop);
		
	}
	
	public RenderComponent(Component parent, int p, IRenderable r, IPopulator<Component> pop)
	{
		this(parent, p, r);
		
		pop.populate(this);
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public RenderComponent(Component parent, int p, IRenderable r)
	{
		super(parent, p);
		
		assert r != null;
		
		renderable = r;
		
	}
	
	@Override
	public void preRender(RenderContext rcon)
	{
		this.renderable.preRender(rcon);
		
		super.preRender(rcon);
		
	}
	
	@Override
	public void render(RenderContext rcon)
	{
		this.renderable.render(rcon);
		
		super.render(rcon);
		
	}
	
	@Override
	public void postRender(RenderContext rcon)
	{
		this.renderable.postRender(rcon);
		
		super.postRender(rcon);
		
	}
	
}
