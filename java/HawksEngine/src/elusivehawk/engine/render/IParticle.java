
package elusivehawk.engine.render;

import elusivehawk.engine.math.Vector3f;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public interface IParticle
{
	public void updateParticle();
	
	public boolean updatePositionOrColor();
	
	public Vector3f getPosition();
	
	public Color getColor();
	
	public boolean remove();
	
}
