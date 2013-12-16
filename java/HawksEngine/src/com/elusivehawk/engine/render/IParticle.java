
package com.elusivehawk.engine.render;

import com.elusivehawk.engine.math.Vector;

/**
 * 
 * @deprecated To be replaced with a less memory-hogging particle system.
 * 
 * @author Elusivehawk
 */
@Deprecated
public interface IParticle
{
	public void updateParticle();
	
	public Vector<Float> getPosition();
	
	public Color getColor();
	
	public boolean updatePositionOrColor();
	
	public boolean flaggedForRemoval();
	
}
