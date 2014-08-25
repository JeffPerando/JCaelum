
package com.elusivehawk.engine;

import java.util.Collection;
import java.util.List;
import com.elusivehawk.engine.assets.AssetManager;
import com.elusivehawk.engine.physics.IPhysicsSimulator;
import com.elusivehawk.engine.render.RenderContext;
import com.elusivehawk.engine.render.RenderException;
import com.elusivehawk.engine.render.RenderHelper;
import com.elusivehawk.engine.render.old.IRenderEngine;
import com.elusivehawk.engine.render.old.IRenderHUB;
import com.elusivehawk.engine.render.opengl.GLConst;
import com.elusivehawk.engine.render.opengl.GLEnumTexture;
import com.elusivehawk.engine.render.opengl.IGL1;
import com.elusivehawk.util.IUpdatable;
import com.elusivehawk.util.StringHelper;
import com.google.common.collect.Lists;

/**
 * 
 * An abstract class that is meant to unify some {@link Game} and {@link GameState} classes.
 * 
 * @author Elusivehawk
 * 
 * @see Game
 * @see GameArguments
 * @see GameState
 * @see IUpdatable
 */
public abstract class AbstractGameComponent implements IUpdatable
{
	private final AbstractGameComponent master;
	
	protected final String name;
	protected final List<IUpdatable> modules = Lists.newArrayList();
	
	protected AbstractGameComponent(String title)
	{
		this(null, title);
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	protected AbstractGameComponent(AbstractGameComponent owner, String title)
	{
		master = owner;
		name = title;
		
	}
	
	@Override
	public String toString()
	{
		if (this.master == null)
		{
			return this.getFormattedName();
		}
		
		StringBuilder b = StringHelper.newBuilder(this.modules.size() * 2 + 2);
		
		b.append(this.name);
		b.append('{');
		
		for (int c = 0; c < this.modules.size(); c++)
		{
			b.append(this.modules.get(c));
			
			if (c < (this.modules.size() - 1))
			{
				b.append(", ");
				
			}
			
		}
		
		b.append('}');
		
		return String.format("%s.%s", this.master.master == null ? this.master.name : this.master.toString(), b.toString());
	}
	
	@Override
	public void update(double delta) throws Throwable
	{
		this.updateModules(delta);
		
	}
	
	protected final void updateModules(double delta) throws Throwable
	{
		for (IUpdatable m : this.modules)
		{
			m.update(delta);
			
		}
		
	}
	
	//XXX Module things
	
	public synchronized void addModule(IUpdatable m)
	{
		if (m instanceof Thread)
		{
			throw new CaelumException("Threads are NOT modules. Silly Buttons..."/*[sic]*/);
		}
		
		this.modules.add(m);
		
	}
	
	public synchronized void removeModule(IUpdatable m)
	{
		this.modules.remove(m);
		
	}
	
	public String getFormattedName()
	{
		return this.name;
	}
	
	@SuppressWarnings("unused")
	public void onScreenFlipped(boolean flip){}
	
	@SuppressWarnings("unused")
	public void loadAssets(AssetManager mgr){}
	
	@Deprecated
	public IRenderHUB getRenderHUB()
	{
		return null;
	}
	
	public abstract void initiate(GameArguments args) throws Throwable;
	
	/**
	 * 
	 * NOTICE: THIS IS NOT THREAD SAFE!<br>
	 * I mean it people, sync your entities and crap!
	 * 
	 * @param rcon
	 * @param delta
	 * 
	 * @throws RenderException
	 * 
	 * @see RenderHelper
	 */
	public abstract void render(RenderContext rcon, double delta) throws RenderException;
	
	public abstract void onShutdown();
	
	public abstract IPhysicsSimulator getPhysicsSimulator();
	
}
