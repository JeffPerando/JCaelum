
package com.elusivehawk.caelum.render;

import com.elusivehawk.caelum.Experimental;
import com.elusivehawk.util.math.MatrixF;
import com.elusivehawk.util.math.MatrixHelper;
import com.elusivehawk.util.math.QuaternionF;
import com.elusivehawk.util.math.VectorF;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
@Experimental
public abstract class Camera3D extends Camera
{
	protected final VectorF pos = (VectorF)new VectorF(3).setSync();
	protected final QuaternionF rot = (QuaternionF)new QuaternionF().setSync();
	
	private float fov, zNear, zFar;
	
	private boolean updateProj = true, updateView = true;
	
	@SuppressWarnings("unqualified-field-access")
	public Camera3D(float fieldOfView, float nearZ, float farZ)
	{
		super(4);
		
		fov = fieldOfView;
		zNear = nearZ;
		zFar = farZ;
		
	}
	
	@Override
	public void preRender(RenderContext rcon)
	{
		if (this.updateProj)
		{
			MatrixHelper.projection(this.fov, rcon.getDisplay().getAspectRatio(), this.zFar, this.zNear, this.proj);
			
			synchronized (this)
			{
				this.updateProj = false;
				
			}
			
		}
		
		if (this.updateView)
		{
			this.calcView(this.view);
			
			synchronized (this)
			{
				this.updateView = false;
				
			}
			
		}
		
	}
	
	@Override
	public MatrixF getView()
	{
		return this.view;
	}
	
	@Override
	public MatrixF getProjection()
	{
		return this.proj;
	}
	
	public Camera3D setRotation(QuaternionF off)
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
	
	protected abstract void calcView(MatrixF dest);
	
}
