
package com.elusivehawk.engine.prefab;

import com.elusivehawk.util.IPopulator;
import com.elusivehawk.util.IUpdatable;
import com.elusivehawk.util.Logger;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class LogicComponent extends Component
{
	protected final IUpdatable logic;
	
	public LogicComponent(Component parent, IUpdatable upd)
	{
		this(parent, 0, upd);
		
	}
	
	public LogicComponent(Component parent, IUpdatable upd, IPopulator<Component> pop)
	{
		this(parent, 0, upd, pop);
		
	}
	
	public LogicComponent(Component parent, int p, IUpdatable upd, IPopulator<Component> pop)
	{
		this(parent, p, upd);
		
		pop.populate(this);
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public LogicComponent(Component parent, int p, IUpdatable upd)
	{
		super(parent, p);
		
		assert upd != null;
		
		logic = upd;
		
	}
	
	@Override
	public void update(double delta)
	{
		try
		{
			this.logic.update(delta);
			
		}
		catch (Throwable e)
		{
			Logger.log().err(e);
			
		}
		
		super.update(delta);
		
	}
	
}
