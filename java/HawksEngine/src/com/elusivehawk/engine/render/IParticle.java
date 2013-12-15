
package com.elusivehawk.engine.render;

import com.elusivehawk.engine.math.Vector3f;

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
	
	public Vector3f getPosition();
	
	public Color getColor();
	
	public boolean updatePositionOrColor();
	
	public boolean flaggedForRemoval();
	
}
