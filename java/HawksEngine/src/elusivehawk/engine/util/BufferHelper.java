
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
		ByteBuffer ret = BufferUtils.createByteBuffer(data.length);
		ret.put(data);
		ret.flip();
		
		return ret;
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
		
		for (int c = count; c < (count + offset); c++)
		{
			ret.put(buf.get(c));
			
		}
		
		ret.flip();
		
		return ret;
	}
	
	public static CharBuffer makeCharBuffer(char... data)
	{
		CharBuffer ret = BufferUtils.createCharBuffer(data.length);
		ret.put(data);
		ret.flip();
		
		return ret;
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
		
		for (int c = count; c < (count + offset); c++)
		{
			ret.put(buf.get(c));
			
		}
		
		ret.flip();
		
		return ret;
	}
	
	public static ShortBuffer makeShortBuffer(short... data)
	{
		ShortBuffer ret = BufferUtils.createShortBuffer(data.length);
		ret.put(data);
		ret.flip();
		
		return ret;
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
		
		for (int c = count; c < (count + offset); c++)
		{
			ret.put(buf.get(c));
			
		}
		
		ret.flip();
		
		return ret;
	}
	
	public static IntBuffer makeIntBuffer(int... data)
	{
		IntBuffer ret = BufferUtils.createIntBuffer(data.length);
		ret.put(data);
		ret.flip();
		
		return ret;
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
		
		for (int c = count; c < (count + offset); c++)
		{
			ret.put(buf.get(c));
			
		}
		
		ret.flip();
		
		return ret;
	}
	
	public static DoubleBuffer makeDoubleBuffer(double... data)
	{
		DoubleBuffer ret = BufferUtils.createDoubleBuffer(data.length);
		ret.put(data);
		ret.flip();
		
		return ret;
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
		
		for (int c = count; c < (count + offset); c++)
		{
			ret.put(buf.get(c));
			
		}
		
		ret.flip();
		
		return ret;
	}
	
	public static FloatBuffer makeFloatBuffer(float... data)
	{
		FloatBuffer ret = BufferUtils.createFloatBuffer(data.length);
		ret.put(data);
		ret.flip();
		
		return ret;
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
		
		for (int c = count; c < (count + offset); c++)
		{
			ret.put(buf.get(c));
			
		}
		
		ret.flip();
		
		return ret;
	}
	
	public static LongBuffer makeLongBuffer(long... data)
	{
		LongBuffer ret = BufferUtils.createLongBuffer(data.length);
		ret.put(data);
		ret.flip();
		
		return ret;
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
		
		for (int c = count; c < (count + offset); c++)
		{
			ret.put(buf.get(c));
			
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
	
	public static FloatBuffer makeBufferFromMatrix(List<Matrix> data)
	{
		FloatBuffer ret = BufferUtils.createFloatBuffer(1);
		
		for (Matrix mat : data)
		{
			ret.limit(ret.limit() + (mat.w * mat.h));
			
			mat.store(ret);
			
		}
		
		ret.flip();
		
		return ret;
	}
	
	public static FloatBuffer makeBufferFromMatrix(Matrix... data)
	{
		FloatBuffer ret = BufferUtils.createFloatBuffer(1);
		
		for (Matrix mat : data)
		{
			ret.limit(ret.limit() + (mat.w * mat.h));
			
			mat.store(ret);
			
		}
		
		ret.flip();
		
		return ret;
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
