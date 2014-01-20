
package com.elusivehawk.engine.physics;

import com.elusivehawk.engine.math.Vector;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public interface ICollisionObject
{
	public void updatePositioning(double delta);
	
	public boolean isInactive();
	
	public void setInactive(boolean inactive);
	
	public Vector<Float> getCentralPosition();
	
	public void addChild(ICollisionObject obj);
	
	public void expand(float f);
	
	public void expand(float x, float y, float z);
	
	public boolean isNoclipping();
	
	public ICollisionListener canCollide(ICollisionObject obj);
	
	public boolean canCollide(Vector<Float> vec);
	
}
