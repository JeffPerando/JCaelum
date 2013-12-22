
package com.elusivehawk.engine.math;

import com.elusivehawk.engine.core.Buffer;

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
				
				ret.set(info, xPos + (yPos * ret.h));
				
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
				
				ret.set(info, xPos + (yPos * ret.h));
				
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
		//Hold on to your butts...
		
		float a = (float)Math.cos(x);
		float b = (float)Math.sin(x);
		float c = (float)Math.cos(y);
		float d = (float)Math.sin(y);
		float e = (float)Math.cos(z);
		float f = (float)Math.sin(z);
		
		float ad = a * d;
		float bd = b * d;
		
		Buffer<Float> buf = new Buffer<Float>();
		
		buf.put(c * e).put(-c * f).put(d).put(0f);
		//-------------------------------------------------
		buf.put(bd * e + a * f).put(-bd * f + a * e).put(-b * c).put(0f);
		//-------------------------------------------------
		buf.put(-ad * e + b * f).put(ad * f + b * e).put(a * c).put(0f);
		//-------------------------------------------------
		buf.put(0f).put(0f).put(0f).put(1f);
		//-------------------------------------------------
		
		buf.rewind();
		
		return new Matrix(buf, 4, 4);
	}
	
	public static Matrix createHomogenousMatrix(Vector<Float> rot, Vector<Float> scl, Vector<Float> trans)
	{
		Matrix rotate = createRotationMatrix(rot);
		Matrix scale = createScalingMatrix(scl);
		Matrix translate = createTranslationMatrix(trans);
		
		return rotate.mul(scale).mul(translate);
	}
	
}
