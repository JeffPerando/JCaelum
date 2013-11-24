
package com.elusivehawk.engine.render;

import org.lwjgl.input.Mouse;
import com.elusivehawk.engine.core.BufferHelper;
import com.elusivehawk.engine.core.DirtableStorage;
import com.elusivehawk.engine.math.Matrix;
import com.elusivehawk.engine.math.MatrixHelper;
import com.elusivehawk.engine.math.Vector3f;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class Camera3D implements ICamera<Vector3f, Vector3f>
{
	private Vector3f angle = new Vector3f();
	private Vector3f pos = new Vector3f();
	private DirtableStorage<Boolean> grabMouse = new DirtableStorage<Boolean>(true);
	
	public Camera3D()
	{
		angle.setIsDirty(true);
		pos.setIsDirty(true);
		grabMouse.setIsDirty(true);
		
	}
	
	@Override
	public void updateCamera(IRenderHUB hub)
	{
		if (!hub.getRenderMode().is3D())
		{
			return;
		}
		
		if (Mouse.isCreated() && Mouse.isInsideWindow())
		{
			if (this.grabMouse.isDirty())
			{
				Mouse.setGrabbed(this.grabMouse.get());
				
				this.grabMouse.setIsDirty(false);
				
			}
			
			if (Mouse.isGrabbed())
			{
				int x = Mouse.getX();
				int y = Mouse.getY();
				
			}
			
		}
		
	}
	
	@Override
	public void postRender(IRenderHUB hub)
	{
		this.setIsDirty(false);
		
	}
	
	@Override
	public void updateUniform(GLProgram p)
	{
		if (!this.isDirty())
		{
			return;
		}
		
		Matrix camM = MatrixHelper.createHomogenousMatrix(this.angle, new Vector3f(1.0f, 1.0f, 1.0f), null); //TODO Calculate translation
		
		p.attachUniform("cam.m", camM.asBuffer(), GLProgram.EnumUniformType.M_FOUR);
		p.attachUniform("cam.zFar", BufferHelper.makeFloatBuffer(this.getZFar()), GLProgram.EnumUniformType.ONE);
		p.attachUniform("cam.zNear", BufferHelper.makeFloatBuffer(this.getZNear()), GLProgram.EnumUniformType.ONE);
		
	}
	
	@Override
	public boolean isDirty()
	{
		return this.angle.isDirty() || this.pos.isDirty();
	}
	
	@Override
	public void setIsDirty(boolean dirty)
	{
		this.angle.setIsDirty(false);
		this.pos.setIsDirty(false);
		
	}
	
	@Override
	public Vector3f getCamRot()
	{
		return angle;
	}
	
	@Override
	public Vector3f getCamPos()
	{
		return pos;
	}
	
	@Override
	public float getZFar()
	{
		return 0;
	}
	
	@Override
	public float getZNear()
	{
		return 0;
	}
	
	public void setMouseGrabbed(boolean b)
	{
		this.grabMouse.set(b);
		
	}
	
}
