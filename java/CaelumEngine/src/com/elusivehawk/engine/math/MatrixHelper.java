
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
		return new Matrix(4, 4).setIdentity();
	}
	
	public static Matrix createHomogenousMatrix(Vector rot, Vector scl, Vector trans)
	{
		return (Matrix)createRotationMatrix(rot).mul(createScalingMatrix(scl)).mul(createTranslationMatrix(trans));
	}
	
	public static Matrix createHomogenousMatrix(Quaternion rot, Vector scl, Vector trans)
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
	
	public static Matrix createRotationMatrix(Quaternion q)
	{
		float a = (float)Math.cos(q.get(A));
		float b = (float)Math.sin(q.get(A));
		float c = (float)Math.cos(q.get(B));
		float d = (float)Math.sin(q.get(B));
		float e = (float)Math.cos(q.get(C));
		float f = (float)Math.sin(q.get(C));
		
		float ad = a * d;
		float bd = b * d;
		
		float[] buf = new float[16];
		
		buf[0] = c * e; buf[1] = -c * f; buf[2] = d; buf[3] = 0f;
		//-------------------------------------------------
		buf[4] = bd * e + a * f; buf[5] = -bd * f + a * e; buf[6] = -b * c; buf[7] = 0f;
		//-------------------------------------------------
		buf[8] = -ad * e + b * f; buf[9] = ad * f + b * e; buf[10] = a * c; buf[11] = 0f;
		//-------------------------------------------------
		buf[12] = buf[13] = buf[14] = 0f; buf[15] = 1f;
		//-------------------------------------------------
		
		return new Matrix(buf);
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
