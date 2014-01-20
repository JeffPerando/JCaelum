
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
	
	public static Matrix createTranslationMatrix(Vector<Float> vec)
	{
		return createTranslationMatrix(vec.get(Vector.X), vec.get(Vector.Y), vec.get(Vector.Z));
	}
	
	public static Matrix createTranslationMatrix(float x, float y, float z)
	{
		Matrix ret = new Matrix(4, 4);
		float[] pos = new float[]{x, y, z};
		
		for (int xPos = 0; xPos < 4; xPos++)
		{
			for (int yPos = 0; yPos < 4; yPos++)
			{
				float info = 0;
				
				if (xPos == yPos)
				{
					info = 1;
					
				}
				else if (yPos == 3)
				{
					info = pos[xPos];
					
				}
				
				ret.set(xPos + (yPos * ret.h), info);
				
			}
			
		}
		
		//For a visual reference:
		
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
	
	public static Matrix createScalingMatrix(Vector<Float> vec)
	{
		return createScalingMatrix(vec.get(Vector.X), vec.get(Vector.Y), vec.get(Vector.Z));
	}
	
	public static Matrix createScalingMatrix(float x, float y, float z)
	{
		Matrix ret = new Matrix(4, 4);
		float[] pos = new float[]{x, y, z};
		
		for (int xPos = 0; xPos < 4; xPos++)
		{
			for (int yPos = 0; yPos < 4; yPos++)
			{
				float info = 0;
				
				if (xPos == yPos)
				{
					info = (xPos == 3 ? 1 : pos[xPos]);
					
				}
				
				ret.set(xPos + (yPos * ret.h), info);
				
			}
			
		}
		
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
	
	public static Matrix createRotationMatrix(Vector<Float> vec)
	{
		return createRotationMatrix(vec.get(Vector.X), vec.get(Vector.Y), vec.get(Vector.Z));
	}
	
	public static Matrix createRotationMatrix(float x, float y, float z)
	{
		/*
		 * Hold on to your butts...
		 * 
		 * No, seriously, for some reason using Caelum Buffers screws up the latter portion of
		 * this equation, even though the last 4 parts are constant.
		 * 
		 */
		
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
		
		return new Matrix(buf);
	}
	
	public static Matrix createHomogenousMatrix(Vector<Float> rot, Vector<Float> scl, Vector<Float> trans)
	{
		Matrix rotate = createRotationMatrix(rot);
		Matrix scale = createScalingMatrix(scl);
		Matrix translate = createTranslationMatrix(trans);
		
		return rotate.mul(scale).mul(translate);
	}
	
}
