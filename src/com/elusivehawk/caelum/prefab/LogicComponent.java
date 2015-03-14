
package com.elusivehawk.caelum.prefab;

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
	
	public LogicComponent(IUpdatable upd)
	{
		this(0, upd);
		
	}
	
	public LogicComponent(IUpdatable upd, IPopulator<Component> pop)
	{
		this(upd);
		
		pop.populate(this);
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public LogicComponent(int p, IUpdatable upd)
	{
		super(p);
		
		assert upd != null;
		
		logic = upd;
		
	}
	
	public LogicComponent(int p, IUpdatable upd, IPopulator<Component> pop)
	{
		this(p, upd);
		
		pop.populate(this);
		
	}
	
	@Override
	public void update(double delta)
	{
		super.update(delta);
		
		try
		{
			this.logic.update(delta);
			
		}
		catch (Throwable e)
		{
			Logger.err(e);
			
		}
		
	}
	
}
