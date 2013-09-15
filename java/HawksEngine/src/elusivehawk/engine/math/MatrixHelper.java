
package elusivehawk.engine.math;

/**
 * 
 * Just a small class I wrote to help with making matrices.
 * 
 * @author Elusivehawk
 */
public final class MatrixHelper
{
	private MatrixHelper(){}
	
	public static Matrix createTranslationMatrix(Vector3f vec)
	{
		return createTranslationMatrix(vec.x, vec.y, vec.z);
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
				
				ret.data[xPos][yPos] = info;
				
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
	
	public static Matrix createScalingMatrix(Vector3f vec)
	{
		return createScalingMatrix(vec.x, vec.y, vec.z);
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
				
				ret.data[xPos][yPos] = info;
				
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
	
	public static Matrix createRotationMatrix(Vector3f vec)
	{
		return createRotationMatrix(vec.x, vec.y, vec.z);
	}
	
	public static Matrix createRotationMatrix(float x, float y, float z)
	{
		return createRotationMatrix((int)(360 * x) % 360, (int)(360 * y) % 360, (int)(360 * z) % 360);
	}
	
	public static Matrix createRotationMatrix(int x, int y, int z)
	{
		Matrix ret = new Matrix(4, 4);
		
		//Hold on to your butts...
		
		float a = (float)Math.cos(x);
		float b = (float)Math.sin(x);
		float c = (float)Math.cos(y);
		float d = (float)Math.sin(y);
		float e = (float)Math.cos(z);
		float f = (float)Math.sin(z);
		
		float ad = a * d;
		float bd = b * d;
		
		ret.data[0][0] = c * e; ret.data[0][1] = -c * f; ret.data[0][2] = d; ret.data[0][3] = 0;
		//-------------------------------------------------
		ret.data[1][0] = bd * e + a * f; ret.data[1][1] = -bd * f + a * e; ret.data[1][2] = -b * c; ret.data[1][3] = 0;
		//-------------------------------------------------
		ret.data[2][0] = -ad * e + b * f; ret.data[2][1] = ad * f + b * e; ret.data[2][2] = a * c; ret.data[2][3] = 0;
		//-------------------------------------------------
		ret.data[3][0] = 0; ret.data[3][1] = 0; ret.data[3][2] = 0; ret.data[3][3] = 1;
		//-------------------------------------------------
		
		return ret;
	}
	
}
