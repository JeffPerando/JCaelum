
package com.elusivehawk.engine.render.old;

import com.elusivehawk.engine.math.Matrix;
import com.elusivehawk.engine.render.EnumCameraPollType;
import com.elusivehawk.engine.render.EnumRenderMode;
import com.elusivehawk.engine.render.ICamera;
import com.elusivehawk.engine.render.RenderContext;
import com.elusivehawk.engine.render.opengl.GLProgram;
import com.elusivehawk.engine.util.BufferHelper;
import com.elusivehawk.engine.util.DirtableStorage;

/**
 * 
 * The default 3D camera.
 * 
 * @author Elusivehawk
 */
@Deprecated
public class Camera3D implements ICamera
{
	private float[] stats = new float[EnumCameraPollType.values().length];
	private DirtableStorage<Boolean> grabMouse = new DirtableStorage<Boolean>(true);
	private boolean dirty = true;
	private Matrix camMat = new Matrix(16);
	
	@SuppressWarnings("unqualified-field-access")
	public Camera3D()
	{
		grabMouse.setIsDirty(true);
		
	}
	
	@Override
	public void updateCamera(RenderContext context)
	{
		if (!context.getHub().getRenderMode().is3D())
		{
			return;
		}
		
		/*if (Mouse.isCreated() && Mouse.isInsideWindow())
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
					//TODO Calculate stuff, clean up code
					
					Vector<Float> angle = new VectorF(3, this.getFloat(EnumCameraPollType.ROT_X), this.getFloat(EnumCameraPollType.ROT_Y), this.getFloat(EnumCameraPollType.ROT_Y));
					Vector<Float> pos = new VectorF(3, this.getFloat(EnumCameraPollType.POS_X), this.getFloat(EnumCameraPollType.POS_Y), this.getFloat(EnumCameraPollType.POS_Y));
					
					this.camMat = MatrixHelper.createHomogenousMatrix(angle, new VectorF(3, 1.0f, 1.0f, 1.0f), pos);
					this.dirty = true;
					
				}
				
			}
			
		}
		*/
	}
	
	@Override
	public void postRender(RenderContext context)
	{
		this.setIsDirty(false);
		
	}
	
	@Override
	public void updateUniform(GLProgram p, RenderContext context, EnumRenderMode mode)
	{
		if (!this.isDirty())
		{
			return;
		}
		
		p.attachUniform("cam.m", this.camMat.asBuffer(), GLProgram.EnumUniformType.M_FOUR, context);
		p.attachUniform("cam.zFar", BufferHelper.makeFloatBuffer(this.getFloat(EnumCameraPollType.Z_FAR)), GLProgram.EnumUniformType.ONE, context);
		p.attachUniform("cam.zNear", BufferHelper.makeFloatBuffer(this.getFloat(EnumCameraPollType.Z_FAR)), GLProgram.EnumUniformType.ONE, context);
		
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
		return this.stats[pollType.ordinal()];
	}
	
	@Override
	public boolean setFloat(EnumCameraPollType pollType, float f)
	{
		this.stats[pollType.ordinal()] = f;
		
		this.setIsDirty(true);
		
		return true;
	}
	
	public void setMouseGrabbed(boolean b)
	{
		this.grabMouse.set(b);
		
	}
	
}
