
package com.elusivehawk.engine.math;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import com.elusivehawk.engine.util.IStoreable;

/**
 * 
 * Because LWJGL goofed theirs up!
 * 
 * @author Elusivehawk
 */
public class Matrix implements IStoreable
{
	public final float[][] data;
	public final int w, h;
	
	public Matrix(int size)
	{
		this((int)Math.sqrt(size), (int)Math.sqrt(size));
		
	}
	
	public Matrix(int x, int y)
	{
		data = new float[x][y];
		w = x;
		h = y;
		
	}
	
	public Matrix(float[][] info)
	{
		data = info;
		w = info.length;
		h = info[0].length;
		
	}
	
	public Matrix(float[] info, int x, int y)
	{
		this(x, y);
		
		for (int xPos = 0; xPos < x; xPos++)
		{
			for (int yPos = 0; yPos < y; yPos++)
			{
				data[xPos][yPos] = info[xPos + yPos * w];
				
			}
			
		}
		
	}
	
	public Matrix(FloatBuffer buf, int x, int y)
	{
		this(x, y);
		
		this.load(buf);
		
	}
	
	public Matrix(Matrix m)
	{
		this(m.data);
		
	}
	
	@Override
	public boolean store(ByteBuffer buf)
	{
		return false;
	}
	
	@Override
	public boolean store(FloatBuffer buf)
	{
		for (int x = 0; x < this.w; x++)
		{
			for (int y = 0; y < this.h; y++)
			{
				buf.put(this.data[x][y]);
				
			}
			
		}
		
		return true;
	}
	
	@Override
	public boolean store(IntBuffer buf)
	{
		return false;
	}
	
	public Matrix load(FloatBuffer buf)
	{
		if (buf.limit() < (this.w * this.h))
		{
			throw new RuntimeException("Buffer is too small to hold me!");
		}
		
		for (int x = 0; x < this.w; x++)
		{
			for (int y = 0; y < this.h; y++)
			{
				this.data[x][y] = buf.get();
				
			}
			
		}
		
		return this;
	}
	
	public Matrix add(Matrix m)
	{
		int xSize = Math.min(m.w, this.w);
		int ySize = Math.min(m.h, this.h);
		
		for (int x = 0; x < xSize; x++)
		{
			for (int y = 0; y < ySize; y++)
			{
				this.data[x][y] += m.data[x][y];
				
			}
			
		}
		
		return this;
	}
	
	public Matrix sub(Matrix m)
	{
		int xSize = Math.min(m.w, this.w);
		int ySize = Math.min(m.h, this.h);
		
		for (int x = 0; x < xSize; x++)
		{
			for (int y = 0; y < ySize; y++)
			{
				this.data[x][y] -= m.data[x][y];
				
			}
			
		}
		
		return this;
	}
	
	public Matrix mul(Matrix m)
	{
		int xSize = Math.min(m.w, this.w);
		int ySize = Math.min(m.h, this.h);
		
		for (int x = 0; x < xSize; x++)
		{
			for (int y = 0; y < ySize; y++)
			{
				this.data[x][y] *= m.data[x][y];
				
			}
			
		}
		
		return this;
	}
	
	public Matrix mul(Vector4f vec)
	{
		if (this.h < 4)
		{
			throw new ArrayIndexOutOfBoundsException("Matrix is too small!");
		}
		
		for (int x = 0; x < this.w; x++)
		{
			this.data[x][0] *= vec.x;
			this.data[x][1] *= vec.y;
			this.data[x][2] *= vec.z;
			this.data[x][3] *= vec.w;
			
		}
		
		return this;
	}
	
}
