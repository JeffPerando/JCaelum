
package com.elusivehawk.engine.render.old;

import com.elusivehawk.engine.math.Vector;
import com.elusivehawk.engine.render.Color;

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
	
	public Vector getPosition();
	
	public Color getColor();
	
	public boolean updatePositionOrColor();
	
	public boolean flaggedForRemoval();
	
}
