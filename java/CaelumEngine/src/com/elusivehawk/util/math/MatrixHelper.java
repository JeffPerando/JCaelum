
package com.elusivehawk.util.math;

import static com.elusivehawk.util.math.MathConst.*;
import static com.elusivehawk.util.math.MathHelper.*;

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
	
	@SuppressWarnings("unused")//FIXME
	public static Matrix createProjectionMatrix(Vector pos, Quaternion rot, float fov, float aspect, float zFar, float zNear)
	{
		float[] ret = new float[16];
		
		float yScale = 1 / (float)Math.tan(toRadians(fov / 2f));
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
		
		setEulerZYX(ret, vec);
		
		return ret;
	}
	
	public static Matrix createRotationMatrix(float x, float y, float z)
	{
		return setEulerZYX(new Matrix(), x, y, z);
	}
	
	public static Matrix setEulerZYX(Matrix mat, Vector euler)
	{
		return setEulerZYX(mat, euler.get(X), euler.get(Y), euler.get(Z));
	}
	
	public static Matrix setEulerZYX(Matrix m, float eulerX, float eulerY, float eulerZ)
	{
		float cx = (float)Math.cos(eulerX);
		float cy = (float)Math.cos(eulerY);
		float cz = (float)Math.cos(eulerZ);
		float sx = (float)Math.sin(eulerX);
		float sy = (float)Math.sin(eulerY);
		float sz = (float)Math.sin(eulerZ);
		
		float cxz = cx * cz;
		float cxsz = cx * sz;
		float sxcz = sx * cz;
		float sxz = sx * sz;
		
		m.setRow(0, cy * cz, sy * sxcz - cxsz, sy * cxz + sxz);
		m.setRow(1, cy * sz, sy * sxz + cxz, sy * cxsz - sxcz);
		m.setRow(2, -sy, cy * sx, cy * cx);
		
		return m;
	}
	
	public static Matrix createRotationMatrix(Quaternion q)
	{
		return createRotationMatrix(q, createIdentityMatrix());
	}
	
	public static Matrix createRotationMatrix(Quaternion q, Matrix dest)//TODO Convert into algorithm
	{
		q.normalize();
		
		float s = 2f / MathHelper.length(q);
		
		dest.set(0, 0, 1 - s * (square(q.get(Y)) + square(q.get(Z))), false);
		dest.set(1, 0, s * (q.get(X) * q.get(Y) + q.get(W) * q.get(Z)), false);
		dest.set(2, 0, s * (q.get(X) * q.get(Z) - q.get(W) * q.get(Y)), false);
		
		dest.set(0, 1, s * (q.get(X) * q.get(Y) - q.get(W) * q.get(Z)), false);
		dest.set(1, 1, 1 - s * (square(q.get(X)) + square(q.get(Z))), false);
		dest.set(2, 1, s * (q.get(Y) * q.get(Z) + q.get(W) * q.get(X)), false);
		
		dest.set(0, 2, s * (q.get(X) * q.get(Z) + q.get(W) * q.get(Y)), false);
		dest.set(1, 2, s * (q.get(Y) * q.get(Z) - q.get(W) * q.get(X)), false);
		dest.set(2, 2, 1 - s * (square(q.get(X)) + square(q.get(Y))), false);
		
		dest.onChanged();
		
		return dest;
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
