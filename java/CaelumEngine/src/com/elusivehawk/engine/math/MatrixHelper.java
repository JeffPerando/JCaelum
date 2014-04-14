
package com.elusivehawk.engine.math;

/**
 * 
 * Just a small class I wrote to help with making matrices.
 * 
 * @author Elusivehawk
 */
public final class MatrixHelper
{
	private MatrixHelper(){}
	
	public static Matrix createHomogenousMatrix(Vector rot, Vector scl, Vector trans)
	{
		return (Matrix)createRotationMatrix(rot).mul(createScalingMatrix(scl)).mul(createTranslationMatrix(trans));
	}
	
	public static Matrix createIdentityMatrix()
	{
		float[] ret = new float[16];
		
		for (int c = 0; c < 4; c++)
		{
			ret[c * 5] = 1f;
			
		}
		
		return new Matrix(ret);
	}
	
	@SuppressWarnings("unused")//FIXME
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
		return createRotationMatrix(vec.get(Vector.X), vec.get(Vector.Y), vec.get(Vector.Z));
	}
	
	public static Matrix createRotationMatrix(float x, float y, float z)
	{
		//Hold on to your butts...
		
		return rotate(z, Vector.Z_AXIS, rotate(y, Vector.Y_AXIS, rotate(x, Vector.X_AXIS, createIdentityMatrix())));
		
		/* Old code:
		float a = (float)Math.cos(x);
		float b = (float)Math.sin(x);
		float c = (float)Math.cos(y);
		float d = (float)Math.sin(y);
		float e = (float)Math.cos(z);
		float f = (float)Math.sin(z);
		
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
		
		return new Matrix(buf);*/
	}
	
	public static Matrix createScalingMatrix(Vector vec)
	{
		return createScalingMatrix(vec.get(Vector.X), vec.get(Vector.Y), vec.get(Vector.Z));
	}
	
	public static Matrix createScalingMatrix(float x, float y, float z)
	{
		Matrix ret = createIdentityMatrix();
		
		ret.set(0, x);
		ret.set(5, y);
		ret.set(10, z);
		
		//ret.m00 = x; ret.m01 = 0; ret.m02 = 0; ret.m03 = 0;
		//-------------------------------------------------
		//ret.m10 = 0; ret.m11 = y; ret.m12 = 0; ret.m13 = 0;
		//-------------------------------------------------
		//ret.m20 = 0; ret.m21 = 0; ret.m22 = z; ret.m23 = 0;
		//-------------------------------------------------
		//ret.m30 = 0; ret.m31 = 0; ret.m32 = 0; ret.m33 = 1;
		//-------------------------------------------------
		
		return ret;
	}
	
	public static Matrix createTranslationMatrix(Vector vec)
	{
		return createTranslationMatrix(vec.get(Vector.X), vec.get(Vector.Y), vec.get(Vector.Z));
	}
	
	public static Matrix createTranslationMatrix(float x, float y, float z)
	{
		Matrix ret = createIdentityMatrix();
		
		ret.set(3, x);
		ret.set(7, y);
		ret.set(11, z);
		
		//ret.data[0][0] = 1; ret.data[0][1] = 0; ret.data[0][2] = 0; ret.data[0][3] = x;
		//-------------------------------------------------
		//ret.data[1][0] = 0; ret.data[1][1] = 1; ret.data[1][2] = 0; ret.data[1][3] = y;
		//-------------------------------------------------
		//ret.data[2][0] = 0; ret.data[2][1] = 0; ret.data[2][2] = 1; ret.data[2][3] = z;
		//-------------------------------------------------
		//ret.data[3][0] = 0; ret.data[3][1] = 0; ret.data[3][2] = 0; ret.data[3][3] = 1;
		//-------------------------------------------------
		
		return ret;
	}
	
	public static Matrix rotate(float angle, Vector axis, Matrix mat)
	{
		Matrix ret = createIdentityMatrix();
		
		float x = axis.get(Vector.X);
		float y = axis.get(Vector.Y);
		float z = axis.get(Vector.Z);
		
		float c = (float)Math.cos(angle);
		float s = (float)Math.sin(angle);
		float oneminusc = 1.0f - c;
		
		float xy = x * y;
		float yz = y * z;
		float xz = x * z;
		
		float xs = x * s;
		float ys = y * s;
		float zs = z * s;
		
		float f00 = MathHelper.square(x) * oneminusc + c;
		float f01 = xy * oneminusc + zs;
		float f02 = xz * oneminusc - ys;
		
		float f10 = xy * oneminusc - zs;
		float f11 = MathHelper.square(y) * oneminusc + c;
		float f12 = yz * oneminusc + xs;
		
		float f20 = xz * oneminusc + ys;
		float f21 = yz * oneminusc - xs;
		float f22 = MathHelper.square(z) * oneminusc + c;
		
		float t00 = mat.get(0) * f00 + mat.get(4) * f01 + mat.get(8) * f02;
		float t01 = mat.get(1) * f00 + mat.get(5) * f01 + mat.get(9) * f02;
		float t02 = mat.get(2) * f00 + mat.get(6) * f01 + mat.get(10) * f02;
		float t03 = mat.get(3) * f00 + mat.get(7) * f01 + mat.get(11) * f02;
		
		float t10 = mat.get(0) * f10 + mat.get(4) * f11 + mat.get(8) * f12;
		float t11 = mat.get(1) * f10 + mat.get(5) * f11 + mat.get(9) * f12;
		float t12 = mat.get(2) * f10 + mat.get(6) * f11 + mat.get(10) * f12;
		float t13 = mat.get(3) * f10 + mat.get(7) * f11 + mat.get(11) * f12;
		
		ret.set(8, mat.get(0) * f20 + mat.get(4) * f21 + mat.get(8) * f22);
		ret.set(9, mat.get(1) * f20 + mat.get(5) * f21 + mat.get(9) * f22);
		ret.set(10, mat.get(2) * f20 + mat.get(6) * f21 + mat.get(10) * f22);
		ret.set(11, mat.get(3) * f20 + mat.get(7) * f21 + mat.get(11) * f22);
		
		ret.set(0, t00);
		ret.set(1, t01);
		ret.set(2, t02);
		ret.set(3, t03);
		
		ret.set(4, t10);
		ret.set(5, t11);
		ret.set(6, t12);
		ret.set(7, t13);
		
		return ret;
	}
	
}
