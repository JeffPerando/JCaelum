
package com.elusivehawk.engine;

import java.util.List;
import com.elusivehawk.engine.assets.AssetManager;
import com.elusivehawk.engine.physics.IPhysicsSimulator;
import com.elusivehawk.engine.render.IRenderable;
import com.elusivehawk.engine.render.RenderContext;
import com.elusivehawk.engine.render.RenderException;
import com.google.common.collect.Lists;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class GameState extends AbstractGameComponent
{
	protected IPhysicsSimulator psim = null;
	protected List<IRenderable> renderers = Lists.newArrayList();
	
	public GameState(Game owner, String title)
	{
		super(owner, title);
		
		assert owner != null;
		
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
	public void initiate(GameArguments args, AssetManager assets) throws Throwable{}
	
	@Override
	public void render(RenderContext rcon) throws RenderException
	{
		this.renderers.forEach(((r) -> {r.render(rcon);}));
		
	}
	
	@Override
	public void onShutdown(){}
	
	@Override
	public IPhysicsSimulator getPhysicsSimulator()
	{
		return this.psim;
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
	
}
