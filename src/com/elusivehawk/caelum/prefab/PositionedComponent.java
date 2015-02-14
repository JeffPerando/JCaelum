
package com.elusivehawk.caelum.prefab;

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
	private final Vector pos = (Vector)new Vector().setSync();
	private final Quaternion rot = (Quaternion)new Quaternion().setSync();
	
	public PositionedComponent(int p, IPopulator<Component> pop)
	{
		super(p);
		
		pop.populate(this);
		
	}
	
	public PositionedComponent(int p)
	{
		super(p);
		
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
