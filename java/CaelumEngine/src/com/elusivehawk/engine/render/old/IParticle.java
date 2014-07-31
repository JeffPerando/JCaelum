
package com.elusivehawk.engine.render.old;

import com.elusivehawk.engine.render.Color;
import com.elusivehawk.util.math.Vector;

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
