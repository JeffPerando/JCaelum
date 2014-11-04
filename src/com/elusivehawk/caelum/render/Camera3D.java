
package com.elusivehawk.caelum.render;

import com.elusivehawk.caelum.CaelumEngine;
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
	protected final Vector pos = new Vector().setSync(), posOff = new Vector();
	protected final Quaternion rot = new Quaternion().setSync(), rotOff = new Quaternion();
	
	private volatile float
				fov,
				zNear,
				zFar;
	
	private volatile Matrix view = null;
	private Matrix proj = null;
	private volatile boolean updateProj = true, updateView = true;
	
	@SuppressWarnings("unqualified-field-access")
	public Camera3D(Quaternion rotation, float fov, float nearZ, float farZ)
	{
		this(fov, nearZ, farZ);
		
		rot.set(rotation);
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public Camera3D(Vector position, float fov, float nearZ, float farZ)
	{
		this(fov, nearZ, farZ);
		
		pos.set(position);
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public Camera3D(Vector position, Quaternion rotation, float fov, float nearZ, float farZ)
	{
		this(fov, nearZ, farZ);
		
		pos.set(position);
		rot.set(rotation);
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public Camera3D(float fieldOfView, float nearZ, float farZ)
	{
		fov = fieldOfView;
		zNear = nearZ;
		zFar = farZ;
		
	}
	
	@Override
	public void onQuatChanged(Quaternion q)
	{
		q.add(this.rotOff, this.rot);
		this.updateView = true;
		
	}
	
	@Override
	public void onVecChanged(Vector vec)
	{
		vec.add(this.posOff, this.pos);
		this.updateView = true;
		
	}
	
	@Override
	public void preRender(RenderContext rcon, double delta)
	{
		if (this.updateProj)
		{
			IDisplay display = CaelumEngine.display();
			
			this.proj = MatrixHelper.createProjectionMatrix(this.fov, (float)display.getHeight() / (float)display.getWidth(), this.zFar, this.zNear);
			
			this.updateProj = false;
			
		}
		
		if (this.updateView)
		{
			this.view = this.calcView();
			
			this.updateView = false;
			
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
	
	public Camera3D setPosOffset(Vector off)
	{
		this.posOff.set(off);
		
		return this;
	}
	
	public Camera3D setRotOffset(Quaternion off)
	{
		this.rotOff.set(off);
		
		return this;
	}
	
	public float getFOV()
	{
		return this.fov;
	}
	
	public void setFOV(float f)
	{
		assert f > 0f;
		
		this.fov = f;
		this.updateProj = true;
		
	}
	
	public float getZFar()
	{
		return this.zFar;
	}
	
	public void setZFar(float f)
	{
		this.zFar = f;
		this.updateProj = true;
		
	}
	
	public float getZNear()
	{
		return this.zNear;
	}
	
	public void setZNear(float f)
	{
		this.zNear = f;
		this.updateProj = true;
		
	}
	
	protected abstract Matrix calcView();
	
}
