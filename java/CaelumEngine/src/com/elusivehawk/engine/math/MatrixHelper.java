
package com.elusivehawk.engine.math;

import static com.elusivehawk.engine.math.MathConst.*;
import com.elusivehawk.engine.physics.jbullet.linearmath.MatrixUtil;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public final class MatrixHelper
{
	private MatrixHelper(){}
	
	public static Matrix createIdentityMatrix()
	{
		Matrix ret = new Matrix(4, 4);
		
		for (int c = 0; c < 4; c++)
		{
			ret.set(c, c, 1f);
			
		}
		
		return ret;
	}
	
	public static Matrix createHomogenousMatrix(Vector rot, Vector scl, Vector trans)
	{
		return (Matrix)createRotationMatrix(rot).mul(createScalingMatrix(scl)).mul(createTranslationMatrix(trans));
	}
	
	public static Matrix createProjectionMatrix(Vector pos, Vector rot, float fov, float aspect, float zFar, float zNear)
	{
		float[] ret = new float[16];
		
		float yScale = 1 / (float)Math.tan(MathHelper.toRadians(fov / 2f));
		float xScale = yScale / aspect;
		float frustumLength = zFar - zNear;
		
		ret[0] = xScale;
		ret[5] = yScale;
		ret[10] = -((zFar + zNear) / frustumLength);
		ret[11] = -((2 * zFar * zNear) / frustumLength);
		ret[14] = -1;
		
		return new Matrix(ret);
	}
	
	public static Matrix createRotationMatrix(Vector vec)
	{
		Matrix ret = new Matrix(4, 4);
		
		MatrixUtil.setEulerZYX(ret, vec);
		
		return ret;
	}
	
	public static Matrix createRotationMatrix(float x, float y, float z)
	{
		Matrix ret = new Matrix(4, 4);
		
		MatrixUtil.setEulerZYX(ret, x, y, z);
		
		return ret;
	}
	
	public static Matrix createScalingMatrix(Vector vec)
	{
		return createScalingMatrix(vec.get(X), vec.get(Y), vec.get(Z));
	}
	
	public static Matrix createScalingMatrix(float x, float y, float z)
	{
		Matrix ret = createIdentityMatrix();
		
		ret.set(0, x);
		ret.set(5, y);
		ret.set(10, z);
		
		return ret;
	}
	
	public static Matrix createTranslationMatrix(Vector vec)
	{
		return createTranslationMatrix(vec.get(X), vec.get(Y), vec.get(Z));
	}
	
	public static Matrix createTranslationMatrix(float x, float y, float z)
	{
		Matrix ret = createIdentityMatrix();
		
		ret.set(3, x);
		ret.set(7, y);
		ret.set(11, z);
		
		return ret;
	}
	
}
