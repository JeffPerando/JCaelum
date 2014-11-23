
package com.elusivehawk.caelum;

import java.util.List;
import com.elusivehawk.caelum.assets.AssetManager;
import com.elusivehawk.caelum.input.IInputListener;
import com.elusivehawk.caelum.input.InputEvent;
import com.elusivehawk.caelum.physics.IPhysicsSimulator;
import com.elusivehawk.caelum.render.IRenderable;
import com.elusivehawk.caelum.render.RenderContext;
import com.elusivehawk.caelum.render.RenderException;
import com.google.common.collect.Lists;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class GameState extends AbstractGameComponent
{
	protected IInputListener inputLis = null;
	protected IPhysicsSimulator psim = null;
	protected List<IRenderable> renderers = Lists.newArrayList();
	
	public GameState(Game owner, String title)
	{
		super(owner, title);
		
		assert owner != null;
		
	}
	
	public GameState setInputListener(IInputListener lis)
	{
		this.inputLis = lis;
		
		return this;
	}
	
	public GameState setPhysicsSim(IPhysicsSimulator sim)
	{
		this.psim = sim;
		
		return this;
	}
	
	public GameState addRenderer(IRenderable r)
	{
		assert r != null;
		
		this.renderers.add(r);
		
		return this;
	}
	
	@Override
	public void onInputReceived(InputEvent event, double delta)
	{
		if (this.inputLis != null)
		{
			this.inputLis.onInputReceived(event, delta);
			
		}
		
	}
	
	@Override
	public void render(RenderContext rcon) throws RenderException
	{
		this.renderers.forEach(((r) -> {r.render(rcon);}));
		
	}
	
	@Override
	public void preRender(RenderContext rcon, double delta)
	{
		this.renderers.forEach(((r) -> {r.preRender(rcon, delta);}));
		
	}
	
	@Override
	public void postRender(RenderContext rcon) throws RenderException
	{
		this.renderers.forEach(((r) -> {r.postRender(rcon);}));
		
	}
	
	@Override
	public void initiate(GameArguments args, Display display, AssetManager assets){}
	
	@Override
	public void onShutdown(){}
	
	@Override
	public IPhysicsSimulator getPhysicsSimulator()
	{
		return this.psim;
	}
	
}
