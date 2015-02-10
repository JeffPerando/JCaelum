
package com.elusivehawk.caelum.prefab;

import java.util.List;
import com.elusivehawk.caelum.AbstractGameComponent;
import com.elusivehawk.caelum.CaelumException;
import com.elusivehawk.caelum.Display;
import com.elusivehawk.caelum.Game;
import com.elusivehawk.caelum.input.IInputListener;
import com.elusivehawk.caelum.input.Input;
import com.elusivehawk.caelum.physics.IPhysicsSimulator;
import com.elusivehawk.caelum.render.IRenderable;
import com.elusivehawk.caelum.render.RenderContext;
import com.elusivehawk.caelum.render.RenderException;
import com.elusivehawk.util.IUpdatable;
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
	
	protected final List<IRenderable> renderers = Lists.newArrayList();
	protected final List<IUpdatable> modules = Lists.newArrayList();
	
	public GameState(Game owner, String title)
	{
		super(owner, title);
		
		assert owner != null;
		
	}
	
	@Override
	public void onInputReceived(Input input, double delta)
	{
		if (this.inputLis != null)
		{
			this.inputLis.onInputReceived(input, delta);
			
		}
		
	}
	
	@Override
	public void update(double delta) throws Throwable
	{
		this.updateModules(delta);
		
	}
	
	@Override
	public void render(RenderContext rcon) throws RenderException
	{
		this.renderers.forEach(((r) -> {r.render(rcon);}));
		
	}
	
	@Override
	public void preRender(RenderContext rcon)
	{
		this.renderers.forEach(((r) -> {r.preRender(rcon);}));
		
	}
	
	@Override
	public void postRender(RenderContext rcon) throws RenderException
	{
		this.renderers.forEach(((r) -> {r.postRender(rcon);}));
		
	}
	
	@Override
	public void initiate(Display display){}
	
	@Override
	public void onShutdown(){}
	
	@Override
	public IPhysicsSimulator getPhysicsSimulator()
	{
		return this.psim;
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
	
	//XXX Module things
	
	protected final void updateModules(double delta) throws Throwable
	{
		for (IUpdatable m : this.modules)
		{
			m.update(delta);
			
		}
		
	}
	
	public synchronized void addModule(IUpdatable m)
	{
		if (m instanceof Thread)
		{
			throw new CaelumException("Threads aren't modules. Silly Buttons..."/*[sic]*/);
		}
		
		this.modules.add(m);
		
	}
	
	public synchronized void removeModule(IUpdatable m)
	{
		this.modules.remove(m);
		
	}
	
}
