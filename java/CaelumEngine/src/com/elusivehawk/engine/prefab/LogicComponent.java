
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
	
	@SuppressWarnings("unqualified-field-access")
	public LogicComponent(ComponentGroup parent, IUpdatable upd)
	{
		super(parent);
		
		assert upd != null;
		
		logic = upd;
		
	}
	
	public LogicComponent(ComponentGroup parent, IUpdatable upd, IPopulator<Component> pop)
	{
		this(parent, upd);
		
		pop.populate(this);
		
	}
	
	@Override
	public void update(double delta, Object... extra)
	{
		try
		{
			this.logic.update(delta, extra);
			
		}
		catch (Throwable e)
		{
			Logger.log().err(e);
			
		}
		
		super.update(delta, extra);
		
	}
	
}
