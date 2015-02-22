
package com.elusivehawk.caelum.prefab;

import java.util.List;
import com.elusivehawk.caelum.AbstractGameComponent;
import com.elusivehawk.caelum.CaelumException;
import com.elusivehawk.caelum.Game;
import com.elusivehawk.caelum.input.IInputListener;
import com.elusivehawk.caelum.input.Input;
import com.elusivehawk.caelum.physics.IPhysicsSimulator;
import com.elusivehawk.caelum.render.Renderable;
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
	private final Game master;
	
	private IInputListener inputLis = null;
	private IPhysicsSimulator psim = null;
	
	private final List<Renderable> renderers = Lists.newArrayList();
	private final List<IUpdatable> modules = Lists.newArrayList();
	
	@SuppressWarnings("unqualified-field-access")
	public GameState(Game owner, String title)
	{
		super(title);
		
		assert owner != null;
		
		master = owner;
		
	}
	
	@Override
	public void onInputReceived(Input input, double delta)
	{
		this.inputLis.onInputReceived(input, delta);
		
	}
	
	@Override
	public void update(double delta) throws Throwable
	{
		this.updateModules(delta);
		
	}
	
	@Override
	public void delete(RenderContext rcon)
	{
		this.renderers.forEach(((r) -> {r.delete(rcon);}));
		
	}
	
	@Override
	public void renderImpl(RenderContext rcon) throws RenderException
	{
		this.renderers.forEach(((r) -> {r.render(rcon);}));
		
	}
	
	@Override
	public void preRender(RenderContext rcon) throws RenderException
	{
		this.renderers.forEach(((r) -> {r.preRender(rcon);}));
		
	}
	
	@Override
	public void postRender(RenderContext rcon) throws RenderException
	{
		this.renderers.forEach(((r) -> {r.postRender(rcon);}));
		
	}
	
	@Override
	public IPhysicsSimulator getPhysicsSimulator()
	{
		return this.psim;
	}
	
	@Override
	public void initiate() throws Throwable{}
	
	@Override
	public void onShutdown(){}
	
	@Override
	public String toString()
	{
		return String.format("%s.%s", this.master.toString(), this.name);
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
	
	public GameState addRenderer(Renderable r)
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
