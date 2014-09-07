
package com.elusivehawk.engine.prefab;

import com.elusivehawk.engine.render.IRenderable;
import com.elusivehawk.engine.render.RenderContext;
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
	
	@SuppressWarnings("unqualified-field-access")
	public RenderComponent(ComponentGroup parent, IRenderable r)
	{
		super(parent);
		
		assert r != null;
		
		renderable = r;
		
	}
	
	public RenderComponent(ComponentGroup parent, IRenderable r, IPopulator<Component> pop)
	{
		this(parent, r);
		
		pop.populate(this);
		
	}
	
	@Override
	public void render(RenderContext rcon, double delta)
	{
		this.renderable.render(rcon, delta);
		
		super.render(rcon, delta);
		
	}
	
}
