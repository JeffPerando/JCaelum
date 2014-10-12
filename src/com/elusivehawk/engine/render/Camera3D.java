
package com.elusivehawk.engine.render;

import com.elusivehawk.engine.Experimental;
import com.elusivehawk.engine.input.Input;
import com.elusivehawk.util.math.Matrix;
import com.elusivehawk.util.math.Quaternion;
import com.elusivehawk.util.math.Vector;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
@Experimental
public class Camera3D implements ICamera
{
	private final Vector pos = new Vector().setSync(), posOff = new Vector();
	private final Quaternion rot = new Quaternion(), rotOff = new Quaternion();
	
	private final float fov, zNear, zFar;
	
	private volatile Matrix view = null, proj = null;
	private volatile boolean dirty = true;
	
	@SuppressWarnings("unqualified-field-access")
	public Camera3D(float fieldOfView, float nearZ, float farZ)
	{
		fov = fieldOfView;
		zNear = nearZ;
		zFar = farZ;
		
	}
	
	@Override
	public boolean isDirty()
	{
		return this.dirty;
	}
	
	@Override
	public void setIsDirty(boolean b)
	{
		this.dirty = b;
		
	}
	
	@Override
	public void onQuatChanged(Quaternion q)
	{
		q.add(this.rotOff, this.rot);
		this.dirty = true;
		
	}
	
	@Override
	public void onVecChanged(Vector vec)
	{
		vec.add(this.posOff, this.pos);
		this.dirty = true;
		
	}
	
	@Override
	public void onInputReceived(Input in)
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void render(RenderContext rcon)
	{
		rcon.renderGame(this);
		
	}
	
	@Override
	public Matrix getView()
	{
		return this.view;
	}
	
	@Override
	public Matrix getProjection()
	{
		return this.proj;
	}
	
	public Camera3D setPosOffset(Vector off)
	{
		this.posOff.set(off);
		
		return this;
	}
	
}
