
package com.elusivehawk.engine.core;

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
import com.elusivehawk.engine.math.IVector;
import com.elusivehawk.engine.render.Color;

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
		return makeByteBuffer(data.length, data);
	}
	
	public static ByteBuffer makeByteBuffer(int l, byte... data)
	{
		return (ByteBuffer)BufferUtils.createByteBuffer(l).put(data).flip();
	}
	
	public static ByteBuffer makeByteBuffer(List<Byte> data)
	{
		ByteBuffer ret = BufferUtils.createByteBuffer(data.size());
		
		for (byte b : data)
		{
			ret.put(b);
			
		}
		
		ret.flip();
		
		return ret;
	}
	
	public static ByteBuffer makeByteBuffer(ByteBuffer buf, int offset, int count)
	{
		ByteBuffer ret = BufferUtils.createByteBuffer(count);
		
		for (int c = 0; c < count; c++)
		{
			ret.put(buf.get(c + offset));
			
		}
		
		ret.flip();
		
		return ret;
	}
	
	public static CharBuffer makeCharBuffer(char... data)
	{
		return makeCharBuffer(data.length, data);
	}
	
	public static CharBuffer makeCharBuffer(int l, char... data)
	{
		return (CharBuffer)BufferUtils.createCharBuffer(l).put(data).flip();
	}
	
	public static CharBuffer makeCharBuffer(List<Character> data)
	{
		CharBuffer ret = BufferUtils.createCharBuffer(data.size());
		
		for (Character c : data)
		{
			ret.put(c);
			
		}
		
		ret.flip();
		
		return ret;
	}
	
	public static CharBuffer makeCharBuffer(CharBuffer buf, int offset, int count)
	{
		CharBuffer ret = BufferUtils.createCharBuffer(count);
		
		for (int c = 0; c < count; c++)
		{
			ret.put(buf.get(c + offset));
			
		}
		
		ret.flip();
		
		return ret;
	}
	
	public static ShortBuffer makeShortBuffer(short... data)
	{
		return makeShortBuffer(data.length, data);
	}
	
	public static ShortBuffer makeShortBuffer(int l, short... data)
	{
		return (ShortBuffer)BufferUtils.createShortBuffer(l).put(data).flip();
	}
	
	public static ShortBuffer makeShortBuffer(List<Short> data)
	{
		ShortBuffer ret = BufferUtils.createShortBuffer(data.size());
		
		for (short s : data)
		{
			ret.put(s);
			
		}
		
		ret.flip();
		
		return ret;
	}
	
	public static ShortBuffer makeShortBuffer(ShortBuffer buf, int offset, int count)
	{
		ShortBuffer ret = BufferUtils.createShortBuffer(count);
		
		for (int c = 0; c < count; c++)
		{
			ret.put(buf.get(c + offset));
			
		}
		
		ret.flip();
		
		return ret;
	}
	
	public static IntBuffer makeIntBuffer(int... data)
	{
		return makeIntBuffer(data.length, data);
	}
	
	public static IntBuffer makeIntBuffer(int l, int... data)
	{
		return (IntBuffer)BufferUtils.createIntBuffer(l).put(data).flip();
	}
	
	public static IntBuffer makeIntBuffer(List<Integer> data)
	{
		IntBuffer ret = BufferUtils.createIntBuffer(data.size());
		
		for (int i : data)
		{
			ret.put(i);
			
		}
		
		ret.flip();
		
		return ret;
	}
	
	public static IntBuffer makeIntBuffer(IntBuffer buf, int offset, int count)
	{
		IntBuffer ret = BufferUtils.createIntBuffer(count);
		
		for (int c = 0; c < count; c++)
		{
			ret.put(buf.get(c + offset));
			
		}
		
		ret.flip();
		
		return ret;
	}
	
	public static DoubleBuffer makeDoubleBuffer(double... data)
	{
		return makeDoubleBuffer(data.length, data);
	}
	
	public static DoubleBuffer makeDoubleBuffer(int l, double... data)
	{
		return (DoubleBuffer)BufferUtils.createDoubleBuffer(l).put(data).flip();
	}
	
	public static DoubleBuffer makeDoubleBuffer(List<Double> data)
	{
		DoubleBuffer ret = BufferUtils.createDoubleBuffer(data.size());
		
		for (double d : data)
		{
			ret.put(d);
			
		}
		
		ret.flip();
		
		return ret;
	}
	
	public static DoubleBuffer makeDoubleBuffer(DoubleBuffer buf, int offset, int count)
	{
		DoubleBuffer ret = BufferUtils.createDoubleBuffer(count);
		
		for (int c = 0; c < count; c++)
		{
			ret.put(buf.get(c + offset));
			
		}
		
		ret.flip();
		
		return ret;
	}
	
	public static FloatBuffer makeFloatBuffer(float... data)
	{
		return makeFloatBuffer(data.length, data);
	}
	
	public static FloatBuffer makeFloatBuffer(int l, float... data)
	{
		return (FloatBuffer)BufferUtils.createFloatBuffer(l).put(data).flip();
	}
	
	public static FloatBuffer makeFloatBuffer(List<Float> data)
	{
		FloatBuffer ret = BufferUtils.createFloatBuffer(data.size());
		
		for (float f : data)
		{
			ret.put(f);
			
		}
		
		ret.flip();
		
		return ret;
	}
	
	public static FloatBuffer makeFloatBuffer(FloatBuffer buf, int offset, int count)
	{
		FloatBuffer ret = BufferUtils.createFloatBuffer(count);
		
		for (int c = 0; c < count; c++)
		{
			ret.put(buf.get(c + offset));
			
		}
		
		ret.flip();
		
		return ret;
	}
	
	public static LongBuffer makeLongBuffer(long... data)
	{
		return makeLongBuffer(data.length, data);
	}
	
	public static LongBuffer makeLongBuffer(int l, long... data)
	{
		return (LongBuffer)BufferUtils.createLongBuffer(l).put(data).flip();
	}
	
	public static LongBuffer makeLongBuffer(List<Long> data)
	{
		LongBuffer ret = BufferUtils.createLongBuffer(data.size());
		
		for (long l : data)
		{
			ret.put(l);
			
		}
		
		ret.flip();
		
		return ret;
	}
	
	public static LongBuffer makeLongBuffer(LongBuffer buf, int offset, int count)
	{
		LongBuffer ret = BufferUtils.createLongBuffer(count);
		
		for (int c = 0; c < count; c++)
		{
			ret.put(buf.get(c + offset));
			
		}
		
		ret.flip();
		
		return ret;
	}
	
	//Custom functions for library-specific implementations.
	
	public static ByteBuffer makeByteBufferFromColor(Color... data)
	{
		ByteBuffer ret = BufferUtils.createByteBuffer(data.length * 4);
		
		for (Color col : data)
		{
			col.store(ret);
			
		}
		
		ret.flip();
		
		return ret;
	}
	
	public static ByteBuffer makeByteBufferFromColor(List<Color> data)
	{
		ByteBuffer ret = BufferUtils.createByteBuffer(data.size() * 4);
		
		for (Color col : data)
		{
			col.store(ret);
			
		}
		
		ret.flip();
		
		return ret;
	}
	
	public static FloatBuffer makeFloatBufferFromColor(Color... data)
	{
		FloatBuffer ret = BufferUtils.createFloatBuffer(data.length * 4);
		
		for (Color col : data)
		{
			col.store(ret);
			
		}
		
		ret.flip();
		
		return ret;
	}
	
	public static FloatBuffer makeFloatBufferFromColor(List<Color> data)
	{
		FloatBuffer ret = BufferUtils.createFloatBuffer(data.size() * 4);
		
		for (Color col : data)
		{
			col.store(ret);
			
		}
		
		ret.flip();
		
		return ret;
	}
	
	public static FloatBuffer makeFloatBufferFromVec(IVector... data)
	{
		List<Float> list = new ArrayList<Float>();
		
		for (IVector vec : data)
		{
			for (float f : vec.array())
			{
				list.add(f);
				
			}
			
		}
		
		return makeFloatBuffer(list);
	}
	
	public static FloatBuffer makeFloatBufferFromVec(List<? extends IVector> data)
	{
		List<Float> list = new ArrayList<Float>();
		
		for (IVector vec : data)
		{
			for (float f : vec.array())
			{
				list.add(f);
				
			}
			
		}
		
		return makeFloatBuffer(list);
	}
	
}
