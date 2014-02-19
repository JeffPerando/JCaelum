
package com.elusivehawk.engine.render;

import com.elusivehawk.engine.math.Vector;
import com.elusivehawk.engine.render2.Color;

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
