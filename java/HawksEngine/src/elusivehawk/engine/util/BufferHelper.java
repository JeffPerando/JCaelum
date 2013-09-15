
package elusivehawk.engine.util;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.nio.ShortBuffer;
import java.util.ArrayList;
import java.util.List;
import org.lwjgl.BufferUtils;
import elusivehawk.engine.math.Matrix;
import elusivehawk.engine.math.Vectorf;
import elusivehawk.engine.math.Vectori;
import elusivehawk.engine.render.Color;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public final class BufferHelper
{
	private BufferHelper(){}
	
	public static ByteBuffer makeByteBuffer(byte... data)
	{
		ByteBuffer buf = BufferUtils.createByteBuffer(data.length);
		buf.put(data);
		buf.flip();
		
		return buf;
	}
	
	public static ByteBuffer makeByteBuffer(List<Byte> data)
	{
		ByteBuffer buf = BufferUtils.createByteBuffer(data.size());
		
		for (byte b : data)
		{
			buf.put(b);
			
		}
		
		buf.flip();
		
		return buf;
	}
	
	public static CharBuffer makeCharBuffer(char... data)
	{
		CharBuffer buf = BufferUtils.createCharBuffer(data.length);
		buf.put(data);
		buf.flip();
		
		return buf;
	}
	
	public static CharBuffer makeCharBuffer(List<Character> data)
	{
		CharBuffer buf = BufferUtils.createCharBuffer(data.size());
		
		for (Character c : data)
		{
			buf.put(c);
			
		}
		
		buf.flip();
		
		return buf;
	}
	
	public static ShortBuffer makeShortBuffer(short... data)
	{
		ShortBuffer buf = BufferUtils.createShortBuffer(data.length);
		buf.put(data);
		buf.flip();
		
		return buf;
	}
	
	public static ShortBuffer makeShortBuffer(List<Short> data)
	{
		ShortBuffer buf = BufferUtils.createShortBuffer(data.size());
		
		for (short s : data)
		{
			buf.put(s);
			
		}
		
		buf.flip();
		
		return buf;
	}
	
	public static IntBuffer makeIntBuffer(int... data)
	{
		IntBuffer buf = BufferUtils.createIntBuffer(data.length);
		buf.put(data);
		buf.flip();
		
		return buf;
	}
	
	public static IntBuffer makeIntBuffer(List<Integer> data)
	{
		IntBuffer buf = BufferUtils.createIntBuffer(data.size());
		
		for (int i : data)
		{
			buf.put(i);
			
		}
		
		buf.flip();
		
		return buf;
	}
	
	public static FloatBuffer makeFloatBuffer(float... data)
	{
		FloatBuffer buf = BufferUtils.createFloatBuffer(data.length);
		buf.put(data);
		buf.flip();
		
		return buf;
	}
	
	public static FloatBuffer makeFloatBuffer(List<Float> data)
	{
		FloatBuffer buf = BufferUtils.createFloatBuffer(data.size());
		
		for (float f : data)
		{
			buf.put(f);
			
		}
		
		buf.flip();
		
		return buf;
	}
	
	public static DoubleBuffer makeDoubleBuffer(double... data)
	{
		DoubleBuffer buf = BufferUtils.createDoubleBuffer(data.length);
		buf.put(data);
		buf.flip();
		
		return buf;
	}
	
	public static DoubleBuffer makeDoubleBuffer(List<Double> data)
	{
		DoubleBuffer buf = BufferUtils.createDoubleBuffer(data.size());
		
		for (double d : data)
		{
			buf.put(d);
			
		}
		
		buf.flip();
		
		return buf;
	}
	
	public static LongBuffer makeLongBuffer(long... data)
	{
		LongBuffer buf = BufferUtils.createLongBuffer(data.length);
		buf.put(data);
		buf.flip();
		
		return buf;
	}
	
	public static LongBuffer makeLongBuffer(List<Long> data)
	{
		LongBuffer buf = BufferUtils.createLongBuffer(data.size());
		
		for (long l : data)
		{
			buf.put(l);
			
		}
		
		buf.flip();
		
		return buf;
	}
	
	//Custom functions for library-specific implementations.
	
	public static ByteBuffer makeByteBufferFromColor(boolean alpha, Color... data)
	{
		ByteBuffer buf = BufferUtils.createByteBuffer(data.length * 4);
		
		for (Color col : data)
		{
			col.loadIntoBuffer(buf, alpha);
			
		}
		
		buf.flip();
		
		return buf;
	}
	
	public static ByteBuffer makeByteBufferFromColor(boolean alpha, List<Color> data)
	{
		ByteBuffer buf = BufferUtils.createByteBuffer(data.size() * 4);
		
		for (Color col : data)
		{
			col.loadIntoBuffer(buf, alpha);
			
		}
		
		buf.flip();
		
		return buf;
	}
	
	public static FloatBuffer makeFloatBufferFromColor(boolean alpha, Color... data)
	{
		FloatBuffer buf = BufferUtils.createFloatBuffer(data.length * 4);
		
		for (Color col : data)
		{
			col.loadIntoBuffer(buf, alpha);
			
		}
		
		buf.flip();
		
		return buf;
	}
	
	public static FloatBuffer makeFloatBufferFromColor(boolean alpha, List<Color> data)
	{
		FloatBuffer buf = BufferUtils.createFloatBuffer(data.size() * 4);
		
		for (Color col : data)
		{
			col.loadIntoBuffer(buf, alpha);
			
		}
		
		buf.flip();
		
		return buf;
	}
	
	public static FloatBuffer makeBufferFromMatrix(List<Matrix> data)
	{
		FloatBuffer buf = BufferUtils.createFloatBuffer(1);
		
		for (Matrix mat : data)
		{
			buf.limit(buf.limit() + (mat.w * mat.h));
			
			mat.store(buf);
			
		}
		
		buf.flip();
		
		return buf;
	}
	
	public static FloatBuffer makeBufferFromMatrix(Matrix... data)
	{
		FloatBuffer buf = BufferUtils.createFloatBuffer(1);
		
		for (Matrix mat : data)
		{
			buf.limit(buf.limit() + (mat.w * mat.h));
			
			mat.store(buf);
			
		}
		
		buf.flip();
		
		return buf;
	}
	
	public static FloatBuffer makeFloatBufferFromVec(Vectorf... data)
	{
		List<Float> list = new ArrayList<Float>();
		
		for (Vectorf vec : data)
		{
			for (float f : vec.array())
			{
				list.add(f);
				
			}
			
		}
		
		return makeFloatBuffer(list);
	}
	
	public static FloatBuffer makeFloatBufferFromVec(List<? extends Vectorf> data)
	{
		List<Float> list = new ArrayList<Float>();
		
		for (Vectorf vec : data)
		{
			for (float f : vec.array())
			{
				list.add(f);
				
			}
			
		}
		
		return makeFloatBuffer(list);
	}
	
	public static IntBuffer makeIntBufferFromVec(Vectori... data)
	{
		List<Integer> list = new ArrayList<Integer>();
		
		for (Vectori vec : data)
		{
			for (int i : vec.array())
			{
				list.add(i);
				
			}
			
		}
		
		return makeIntBuffer(list);
	}
	
	public static IntBuffer makeIntBufferFromVec(List<? extends Vectori> data)
	{
		List<Integer> list = new ArrayList<Integer>();
		
		for (Vectori vec : data)
		{
			for (int i : vec.array())
			{
				list.add(i);
				
			}
			
		}
		
		return makeIntBuffer(list);
	}
	
}
