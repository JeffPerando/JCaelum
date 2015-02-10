
package com.elusivehawk.caelum.render;

import com.elusivehawk.caelum.Experimental;
import com.elusivehawk.util.math.Matrix;
import com.elusivehawk.util.math.MatrixHelper;
import com.elusivehawk.util.math.Quaternion;
import com.elusivehawk.util.math.Vector;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
@Experimental
public abstract class Camera3D implements ICamera
{
	protected final Vector pos = new Vector(3).setSync();
	protected final Quaternion rot = new Quaternion().setSync();
	
	private float fov, zNear, zFar;
	
	private Matrix view = null;
	private Matrix proj = null;
	private boolean updateProj = true, updateView = true;
	
	@SuppressWarnings("unqualified-field-access")
	public Camera3D(float fieldOfView, float nearZ, float farZ)
	{
		fov = fieldOfView;
		zNear = nearZ;
		zFar = farZ;
		
	}
	
	@Override
	public void preRender(RenderContext rcon)
	{
		if (this.updateProj)
		{
			Matrix m = MatrixHelper.projection(this.fov, rcon.getDisplay().getAspectRatio(), this.zFar, this.zNear);
			
			synchronized (this)
			{
				this.proj = m;
				this.updateProj = false;
				
			}
			
		}
		
		if (this.updateView)
		{
			Matrix m = this.calcView();
			
			synchronized (this)
			{
				this.view = m;
				this.updateView = false;
				
			}
			
		}
		
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
	
	public Camera3D setRotation(Quaternion off)
	{
		this.rot.set(off);
		
		return this;
	}
	
	public float getFOV()
	{
		return this.fov;
	}
	
	public float getZFar()
	{
		return this.zFar;
	}
	
	public float getZNear()
	{
		return this.zNear;
	}
	
	public synchronized void setFOV(float f)
	{
		assert f > 0f;
		
		this.fov = f;
		this.updateProj = true;
		
	}
	
	public synchronized void setZFar(float f)
	{
		this.zFar = f;
		this.updateProj = true;
		
	}
	
	public synchronized void setZNear(float f)
	{
		this.zNear = f;
		this.updateProj = true;
		
	}
	
	protected abstract Matrix calcView();
	
}
