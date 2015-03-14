
package com.elusivehawk.caelum.prefab;

import com.elusivehawk.caelum.render.Renderable;
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
	protected final Renderable renderable;
	
	public RenderComponent(Renderable r)
	{
		this(0, r);
		
	}
	
	public RenderComponent(Renderable r, IPopulator<Component> pop)
	{
		this(0, r);
		
		pop.populate(this);
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public RenderComponent(int p, Renderable r)
	{
		super(p);
		
		assert r != null;
		
		renderable = r;
		
	}
	
	public RenderComponent(int p, Renderable r, IPopulator<Component> pop)
	{
		this(p, r);
		
		pop.populate(this);
		
	}
	
	@Override
	public void preRender(RenderContext rcon)
	{
		super.preRender(rcon);
		
		this.renderable.preRender(rcon);
		
	}
	
	@Override
	public void render(RenderContext rcon)
	{
		super.render(rcon);
		
		this.renderable.render(rcon);
		
	}
	
	@Override
	public void postRender(RenderContext rcon)
	{
		super.postRender(rcon);
		
		this.renderable.postRender(rcon);
		
	}
	
	@Override
	public void dispose(Object... args)
	{
		super.dispose(args);
		
		this.renderable.dispose(args);
		
	}
	
}
