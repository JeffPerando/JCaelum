
package com.elusivehawk.util;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.CharBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.nio.ShortBuffer;
import java.util.Collection;
import com.elusivehawk.util.io.IByteReader;
import com.elusivehawk.util.storage.Buffer;

/**
 * 
 * Helper class for creating NIO buffers.
 * <p>
 * If you just need something that emulates a buffer without the finite size, consider {@link Buffer} instead.
 * 
 * @author Elusivehawk
 */
public final class BufferHelper
{
	private BufferHelper(){}
	
	public static ByteBuffer createByteBuffer(int size)
	{
		return ByteBuffer.allocateDirect(size).order(ByteOrder.nativeOrder());
	}
	
	public static CharBuffer createCharBuffer(int size)
	{
		return createByteBuffer(size << 1).asCharBuffer();
	}
	
	public static DoubleBuffer createDoubleBuffer(int size)
	{
		return createByteBuffer(size << 3).asDoubleBuffer();
	}
	
	public static FloatBuffer createFloatBuffer(int size)
	{
		return createByteBuffer(size << 2).asFloatBuffer();
	}
	
	public static IntBuffer createIntBuffer(int size)
	{
		return createByteBuffer(size << 2).asIntBuffer();
	}
	
	public static LongBuffer createLongBuffer(int size)
	{
		return createByteBuffer(size << 3).asLongBuffer();
	}
	
	public static ShortBuffer createShortBuffer(int size)
	{
		return createByteBuffer(size << 1).asShortBuffer();
	}
	
	public static ByteBuffer makeByteBuffer(byte... data)
	{
		return makeByteBuffer(data.length, data);
	}
	
	public static ByteBuffer makeByteBuffer(int l, byte... data)
	{
		return (ByteBuffer)createByteBuffer(l).put(data).flip();
	}
	
	public static ByteBuffer makeByteBuffer(Collection<Byte> data)
	{
		ByteBuffer ret = createByteBuffer(data.size());
		
		for (byte b : data)
		{
			ret.put(b);
			
		}
		
		ret.flip();
		
		return ret;
	}
	
	public static ByteBuffer makeByteBuffer(ByteBuffer buf, int offset, int count)
	{
		ByteBuffer ret = createByteBuffer(count);
		
		for (int c = 0; c < count; c++)
		{
			ret.put(buf.get(c + offset));
			
		}
		
		ret.flip();
		
		return ret;
	}
	
	public static ByteBuffer makeByteBuffer(IByteReader r)
	{
		return makeByteBuffer(r.remaining(), r);
	}
	
	public static ByteBuffer makeByteBuffer(int length, IByteReader r)
	{
		ByteBuffer ret = createByteBuffer(length);
		
		for (int c = 0; c < length; c++)
		{
			ret.put(r.read());
			
		}
		
		ret.flip();
		
		return ret;
	}
	
	public static ByteBuffer makeByteBuffer(ByteBuffer... bufs)
	{
		int length = 0;
		
		for (ByteBuffer buf : bufs)
		{
			length += buf.remaining();
			
		}
		
		ByteBuffer ret = createByteBuffer(length);
		
		for (ByteBuffer buf : bufs)
		{
			ret.put(buf);
			
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
		return (CharBuffer)createCharBuffer(l).put(data).flip();
	}
	
	public static CharBuffer makeCharBuffer(Collection<Character> data)
	{
		CharBuffer ret = createCharBuffer(data.size());
		
		for (Character c : data)
		{
			ret.put(c);
			
		}
		
		ret.flip();
		
		return ret;
	}
	
	public static CharBuffer makeCharBuffer(CharBuffer buf, int offset, int count)
	{
		CharBuffer ret = createCharBuffer(count);
		
		for (int c = 0; c < count; c++)
		{
			ret.put(buf.get(c + offset));
			
		}
		
		ret.flip();
		
		return ret;
	}
	
	public static CharBuffer makeCharBuffer(CharBuffer... bufs)
	{
		int length = 0;
		
		for (CharBuffer buf : bufs)
		{
			length += buf.remaining();
			
		}
		
		CharBuffer ret = createCharBuffer(length);
		
		for (CharBuffer buf : bufs)
		{
			ret.put(buf);
			
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
		return (ShortBuffer)createShortBuffer(l).put(data).flip();
	}
	
	public static ShortBuffer makeShortBuffer(Collection<Short> data)
	{
		ShortBuffer ret = createShortBuffer(data.size());
		
		for (short s : data)
		{
			ret.put(s);
			
		}
		
		ret.flip();
		
		return ret;
	}
	
	public static ShortBuffer makeShortBuffer(ShortBuffer buf, int offset, int count)
	{
		ShortBuffer ret = createShortBuffer(count);
		
		for (int c = 0; c < count; c++)
		{
			ret.put(buf.get(c + offset));
			
		}
		
		ret.flip();
		
		return ret;
	}
	
	public static ShortBuffer makeShortBuffer(ShortBuffer... bufs)
	{
		int length = 0;
		
		for (ShortBuffer buf : bufs)
		{
			length += buf.remaining();
			
		}
		
		ShortBuffer ret = createShortBuffer(length);
		
		for (ShortBuffer buf : bufs)
		{
			ret.put(buf);
			
		}
		
		ret.flip();
		
		return ret;
	}
	
	public static IntBuffer makeIntBuffer(int... data)
	{
		return makeIntBuffer(data.length, data);
	}
	
	public static IntBuffer makeIntBuffer(int l, int[] data)
	{
		return (IntBuffer)createIntBuffer(l).put(data).flip();
	}
	
	public static IntBuffer makeIntBuffer(Collection<Integer> data)
	{
		IntBuffer ret = createIntBuffer(data.size());
		
		for (int i : data)
		{
			ret.put(i);
			
		}
		
		ret.flip();
		
		return ret;
	}
	
	public static IntBuffer makeIntBuffer(IntBuffer buf, int offset, int count)
	{
		IntBuffer ret = createIntBuffer(count);
		
		for (int c = 0; c < count; c++)
		{
			ret.put(buf.get(c + offset));
			
		}
		
		ret.flip();
		
		return ret;
	}
	
	public static IntBuffer makeIntBuffer(IntBuffer... bufs)
	{
		int length = 0;
		
		for (IntBuffer buf : bufs)
		{
			length += buf.remaining();
			
		}
		
		IntBuffer ret = createIntBuffer(length);
		
		for (IntBuffer buf : bufs)
		{
			ret.put(buf);
			
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
		return (DoubleBuffer)createDoubleBuffer(l).put(data).flip();
	}
	
	public static DoubleBuffer makeDoubleBuffer(Collection<Double> data)
	{
		DoubleBuffer ret = createDoubleBuffer(data.size());
		
		for (double d : data)
		{
			ret.put(d);
			
		}
		
		ret.flip();
		
		return ret;
	}
	
	public static DoubleBuffer makeDoubleBuffer(DoubleBuffer buf, int offset, int count)
	{
		DoubleBuffer ret = createDoubleBuffer(count);
		
		for (int c = 0; c < count; c++)
		{
			ret.put(buf.get(c + offset));
			
		}
		
		ret.flip();
		
		return ret;
	}
	
	public static DoubleBuffer makeDoubleBuffer(DoubleBuffer... bufs)
	{
		int length = 0;
		
		for (DoubleBuffer buf : bufs)
		{
			length += buf.remaining();
			
		}
		
		DoubleBuffer ret = createDoubleBuffer(length);
		
		for (DoubleBuffer buf : bufs)
		{
			ret.put(buf);
			
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
		return (FloatBuffer)createFloatBuffer(l).put(data).flip();
	}
	
	public static FloatBuffer makeFloatBuffer(Collection<Float> data)
	{
		FloatBuffer ret = createFloatBuffer(data.size());
		
		for (float f : data)
		{
			ret.put(f);
			
		}
		
		ret.flip();
		
		return ret;
	}
	
	public static FloatBuffer makeFloatBuffer(FloatBuffer buf, int offset, int count)
	{
		FloatBuffer ret = createFloatBuffer(count);
		
		for (int c = 0; c < count; c++)
		{
			ret.put(buf.get(c + offset));
			
		}
		
		ret.flip();
		
		return ret;
	}
	
	public static FloatBuffer makeFloatBuffer(FloatBuffer... bufs)
	{
		int length = 0;
		
		for (FloatBuffer buf : bufs)
		{
			length += buf.remaining();
			
		}
		
		FloatBuffer ret = createFloatBuffer(length);
		
		for (FloatBuffer buf : bufs)
		{
			ret.put(buf);
			
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
		return (LongBuffer)createLongBuffer(l).put(data).flip();
	}
	
	public static LongBuffer makeLongBuffer(Collection<Long> data)
	{
		LongBuffer ret = createLongBuffer(data.size());
		
		for (long l : data)
		{
			ret.put(l);
			
		}
		
		ret.flip();
		
		return ret;
	}
	
	public static LongBuffer makeLongBuffer(LongBuffer buf, int offset, int count)
	{
		LongBuffer ret = createLongBuffer(count);
		
		for (int c = 0; c < count; c++)
		{
			ret.put(buf.get(c + offset));
			
		}
		
		ret.flip();
		
		return ret;
	}
	
	public static LongBuffer makeLongBuffer(LongBuffer... bufs)
	{
		int length = 0;
		
		for (LongBuffer buf : bufs)
		{
			length += buf.remaining();
			
		}
		
		LongBuffer ret = createLongBuffer(length);
		
		for (LongBuffer buf : bufs)
		{
			ret.put(buf);
			
		}
		
		ret.flip();
		
		return ret;
	}
	
}
