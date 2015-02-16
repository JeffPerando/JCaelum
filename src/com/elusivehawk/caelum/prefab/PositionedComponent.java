
package com.elusivehawk.caelum.prefab;

import com.elusivehawk.util.IPopulator;
import com.elusivehawk.util.math.QuaternionF;
import com.elusivehawk.util.math.VectorF;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class PositionedComponent extends Component implements QuaternionF.Listener, VectorF.Listener
{
	private final VectorF pos = (VectorF)new VectorF().setSync();
	private final QuaternionF rot = (QuaternionF)new QuaternionF().setSync();
	
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
	public void onVecChanged(VectorF vec)
	{
		this.pos.set(vec);
		
	}
	
	@Override
	public void onQuatChanged(QuaternionF q)
	{
		this.rot.set(q);
		
	}
	
	public VectorF getPosition()
	{
		return this.pos;
	}
	
	public QuaternionF getRotation()
	{
		return this.rot;
	}
	
}
