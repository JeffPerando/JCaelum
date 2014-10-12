
package com.elusivehawk.engine.prefab;

import com.elusivehawk.engine.assets.Asset;
import com.elusivehawk.engine.render.RenderContext;
import com.elusivehawk.engine.render.RenderException;
import com.elusivehawk.engine.render.RenderTicket;
import com.elusivehawk.util.IPopulator;
import com.elusivehawk.util.math.Quaternion;
import com.elusivehawk.util.math.Vector;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class RenderTicketComponent extends PositionedComponent
{
	private final RenderTicket tkt;
	
	public RenderTicketComponent(Component parent, RenderTicket ticket)
	{
		this(parent, 0, ticket);
		
	}
	
	public RenderTicketComponent(Component parent, int p, RenderTicket ticket, IPopulator<Component> pop)
	{
		this(parent, p, ticket);
		
		pop.populate(this);
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public RenderTicketComponent(Component parent, int p, RenderTicket ticket)
	{
		super(parent, p);
		
		assert ticket != null;
		
		tkt = ticket;
		
	}
	
	@Override
	public void preRender(RenderContext rcon, double delta)
	{
		this.tkt.preRender(rcon, delta);
		
		super.preRender(rcon, delta);
		
	}
	
	@Override
	public void render(RenderContext rcon) throws RenderException
	{
		this.tkt.render(rcon);
		
		super.render(rcon);
		
	}
	
	@Override
	public void postRender(RenderContext rcon)
	{
		this.tkt.postRender(rcon);
		
		super.postRender(rcon);
		
	}
	
	@Override
	public void onVecChanged(Vector vec)
	{
		super.onVecChanged(vec);
		
		this.tkt.onVecChanged(vec);
		
	}
	
	@Override
	public void onQuatChanged(Quaternion q)
	{
		super.onQuatChanged(q);
		
		this.tkt.onQuatChanged(q);
		
	}
	
	@Override
	public void onAssetLoaded(Asset a)
	{
		this.tkt.onAssetLoaded(a);
		
		super.onAssetLoaded(a);
		
	}
	
	public RenderTicket getRenderTicket()
	{
		return this.tkt;
	}
	
}
