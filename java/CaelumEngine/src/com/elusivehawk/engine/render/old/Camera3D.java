
package com.elusivehawk.engine.render.old;

import com.elusivehawk.engine.render.RenderContext;
import com.elusivehawk.engine.render.opengl.GLProgram;
import com.elusivehawk.engine.render.opengl.IGLManipulator;
import com.elusivehawk.util.math.Matrix;
import com.elusivehawk.util.math.MatrixHelper;
import com.elusivehawk.util.storage.DirtableStorage;

/**
 * 
 * The default 3D camera.
 * 
 * @author Elusivehawk
 */
@Deprecated
public class Camera3D implements IGLManipulator
{
	private DirtableStorage<Boolean> grabMouse = new DirtableStorage<Boolean>(true);
	private boolean dirty = true;
	private Matrix camMat = new Matrix(16);
	
	@SuppressWarnings("unqualified-field-access")
	public Camera3D()
	{
		grabMouse.setIsDirty(true);
		
	}
	
	@Override
	public void updateUniforms(RenderContext con)
	{
		/*if (!con.getRenderMode().is3D())
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
					//TODO Calculate stuff, clean up code
					
					Vector<Float> angle = new VectorF(3, this.getFloat(EnumCameraPollType.ROT_X), this.getFloat(EnumCameraPollType.ROT_Y), this.getFloat(EnumCameraPollType.ROT_Y));
					Vector<Float> pos = new VectorF(3, this.getFloat(EnumCameraPollType.POS_X), this.getFloat(EnumCameraPollType.POS_Y), this.getFloat(EnumCameraPollType.POS_Y));
					
					this.camMat = MatrixHelper.createHomogenousMatrix(angle, new VectorF(3, 1.0f, 1.0f, 1.0f), pos);
					this.dirty = true;
					
				}
				
			}
			
		}
		*/
		
		this.camMat = /*FIXME*/MatrixHelper.createProjectionMatrix(null, null, 0f, 0f, 0f, 0f);
		this.dirty = true;
		
	}
	
	@Override
	public void manipulateUniforms(RenderContext con, GLProgram p)
	{
		if (!this.dirty)
		{
			return;
		}
		
		p.attachUniform("proj", this.camMat.asBuffer(), GLProgram.EnumUniformType.M_FOUR);
		
	}
	
	@Override
	public void postRender()
	{
		this.dirty = false;
		
	}
	
	public void setMouseGrabbed(boolean b)
	{
		this.grabMouse.set(b);
		
	}
	
}
