
package com.elusivehawk.engine.prefab;

import com.elusivehawk.util.IPopulator;
import com.elusivehawk.util.math.Quaternion;
import com.elusivehawk.util.math.Vector;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class PositionedComponent extends Component implements Quaternion.Listener, Vector.Listener
{
	private final Vector pos = new Vector().setSync();
	private final Quaternion rot = new Quaternion().setSync();
	
	public PositionedComponent(Component owner, int p, IPopulator<Component> pop)
	{
		super(owner, p);
		
		pop.populate(this);
		
	}
	
	public PositionedComponent(Component owner, int p)
	{
		super(owner, p);
		
	}
	
	@Override
	public void onVecChanged(Vector vec)
	{
		this.pos.set(vec);
		
	}
	
	@Override
	public void onQuatChanged(Quaternion q)
	{
		this.rot.set(q);
		
	}
	
	public Vector getPosition()
	{
		return this.pos;
	}
	
	public Quaternion getRotation()
	{
		return this.rot;
	}
	
}
