
package com.elusivehawk.engine.render;

import java.util.HashMap;
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
public class Camera3D implements ICamera
{
	private HashMap<Integer, Float> stats = new HashMap<Integer, Float>();
	private DirtableStorage<Boolean> grabMouse = new DirtableStorage<Boolean>(true);
	private boolean dirty = true;
	
	public Camera3D()
	{
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
				
				if (x > 0 || y > 0)
				{
					//TODO Calculate stuff
					
				}
				
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
		
		Vector3f angle = new Vector3f(this.getFloat(EnumCameraPollType.ROT_X), this.getFloat(EnumCameraPollType.ROT_Y), this.getFloat(EnumCameraPollType.ROT_Y));
		Vector3f pos = new Vector3f(this.getFloat(EnumCameraPollType.POS_X), this.getFloat(EnumCameraPollType.POS_Y), this.getFloat(EnumCameraPollType.POS_Y));
		
		Matrix camM = MatrixHelper.createHomogenousMatrix(angle, new Vector3f(1.0f, 1.0f, 1.0f), pos); //TODO Calculate translation
		
		p.attachUniform("cam.m", camM.asBuffer(), GLProgram.EnumUniformType.M_FOUR);
		p.attachUniform("cam.zFar", BufferHelper.makeFloatBuffer(this.getFloat(EnumCameraPollType.Z_FAR)), GLProgram.EnumUniformType.ONE);
		p.attachUniform("cam.zNear", BufferHelper.makeFloatBuffer(this.getFloat(EnumCameraPollType.Z_FAR)), GLProgram.EnumUniformType.ONE);
		
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
	public float getFloat(EnumCameraPollType pollType)
	{
		return this.stats.get(pollType.ordinal());
	}
	
	@Override
	public boolean setFloat(EnumCameraPollType pollType, float f)
	{
		this.stats.put(pollType.ordinal(), f);
		
		this.setIsDirty(true);
		
		return true;
	}
	
	public void setMouseGrabbed(boolean b)
	{
		this.grabMouse.set(b);
		
	}
	
}
