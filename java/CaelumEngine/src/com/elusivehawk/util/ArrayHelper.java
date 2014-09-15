
package com.elusivehawk.util;

import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.nio.ShortBuffer;
import java.util.List;
import com.elusivehawk.util.storage.IArray;
import com.google.common.collect.Lists;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class ArrayHelper
{
	private ArrayHelper(){}
	
	public static boolean add(Object[] arr, Object obj)
	{
		int i = indexOf(arr, null);
		
		if (i == -1)
		{
			return false;
		}
		
		if (arr[i] != null)
		{
			System.arraycopy(arr, i, arr, i + 1, sizeof(i, arr));
			
		}
		
		arr[i] = obj;
				
		return true;
	}
	
	public static boolean contains(Object[] arr, Object obj)
	{
		return indexOf(arr, obj) != -1;
	}
	
	public static int indexOf(Object[] arr, Object obj)
	{
		if (obj == null)
		{
			for (int c = 0; c < arr.length; c++)
			{
				if (arr[c] == null)
				{
					return c;
				}
				
			}
			
		}
		else
		{
			for (int c = 0; c < arr.length; c++)
			{
				if (obj.equals(arr[c]))
				{
					return c;
				}
				
			}
			
		}
		
		return -1;
	}
	
	public static boolean remove(Object[] arr, Object obj)
	{
		int i = indexOf(arr, obj);
		
		if (i == -1)
		{
			return false;
		}
		
		arr[i] = null;
		
		System.arraycopy(arr, i + 1, arr, i, arr.length - i);
		
		return true;
	}
	
	public static boolean isEmpty(Object[] arr)
	{
		for (Object obj : arr)
		{
			if (obj != null)
			{
				return false;
			}
			
		}
		
		return true;
	}
	
	public static int sizeof(Object[] arr)
	{
		return sizeof(0, arr);
	}
	
	public static int sizeof(int start, Object[] arr)
	{
		assert start >= 0;
		
		int ret = 0;
		
		for (int c = start; c < arr.length; c++)
		{
			if (arr[c] != null)
			{
				ret++;
				
			}
			
		}
		
		return ret;
	}
	
	public static boolean isFull(Object[] arr)
	{
		for (int c = 0; c < arr.length; c++)
		{
			if (arr[c] == null)
			{
				return false;
			}
			
		}
		
		return true;
	}
	
	public static boolean isNullOrEmpty(Object[] arr)
	{
		return arr == null || arr.length == 0;
	}
	
	@SafeVarargs
	public static <T> List<T> concat(T[]... ts)
	{
		int size = 0;
		
		for (T[] ta : ts)
		{
			if (ta == null)
			{
				continue;
			}
			
			size += ta.length;
		}
		
		List<T> l = Lists.newArrayListWithCapacity(size);
		
		for (T[] ta : ts)
		{
			for (T t : ta)
			{
				l.add(t);
				
			}
			
		}
		
		return l;
	}
	
	public static byte[] asBytes(IArray<? extends Number> nums)
	{
		byte[] ret = new byte[nums.size()];
		
		for (int c = 0; c < ret.length; c++)
		{
			ret[c] = nums.get(c).byteValue();
			
		}
		
		return ret;
	}
	
	public static byte[] asBytes(ByteBuffer buf)
	{
		return asBytes(buf, buf.remaining());
	}
	
	public static byte[] asBytes(ByteBuffer buf, int count)
	{
		byte[] ret = new byte[Math.min(count, buf.remaining())];
		
		buf.get(ret, buf.position(), ret.length);
		
		return ret;
	}
	
	public static byte[] asBytes(List<? extends Number> nums)
	{
		byte[] ret = new byte[nums.size()];
		
		for (int c = 0; c < nums.size(); c++)
		{
			ret[c] = nums.get(c).byteValue();
			
		}
		
		return ret;
	}
	public static double[] asDoubles(IArray<? extends Number> nums)
	{
		double[] ret = new double[nums.size()];
		
		for (int c = 0; c < ret.length; c++)
		{
			ret[c] = nums.get(c).doubleValue();
			
		}
		
		return ret;
	}
	
	public static double[] asDoubles(List<? extends Number> nums)
	{
		double[] ret = new double[nums.size()];
		
		for (int c = 0; c < nums.size(); c++)
		{
			ret[c] = nums.get(c).doubleValue();
			
		}
		
		return ret;
	}
	
	public static double[] asDoubles(DoubleBuffer buf)
	{
		return asDoubles(buf, buf.remaining());
	}
	
	public static double[] asDoubles(DoubleBuffer buf, int count)
	{
		double[] ret = new double[Math.min(count, buf.remaining())];
		
		buf.get(ret, buf.position(), ret.length);
		
		return ret;
	}
	
	public static float[] asFloats(IArray<? extends Number> nums)
	{
		float[] ret = new float[nums.size()];
		
		for (int c = 0; c < ret.length; c++)
		{
			ret[c] = nums.get(c).floatValue();
			
		}
		
		return ret;
	}
	
	public static float[] asFloats(List<? extends Number> nums)
	{
		float[] ret = new float[nums.size()];
		
		for (int c = 0; c < nums.size(); c++)
		{
			ret[c] = nums.get(c).floatValue();
			
		}
		
		return ret;
	}
	
	public static float[] asFloats(FloatBuffer buf)
	{
		return asFloats(buf, buf.remaining());
	}
	
	public static float[] asFloats(FloatBuffer buf, int count)
	{
		float[] ret = new float[Math.min(count, buf.remaining())];
		
		buf.get(ret, buf.position(), ret.length);
		
		return ret;
	}
	
	public static int[] asInts(IArray<? extends Number> nums)
	{
		int[] ret = new int[nums.size()];
		
		for (int c = 0; c < ret.length; c++)
		{
			ret[c] = nums.get(c).intValue();
			
		}
		
		return ret;
	}
	
	public static int[] asInts(List<? extends Number> nums)
	{
		int[] ret = new int[nums.size()];
		
		for (int c = 0; c < nums.size(); c++)
		{
			ret[c] = nums.get(c).intValue();
			
		}
		
		return ret;
	}
	
	public static int[] asInts(IntBuffer buf)
	{
		return asInts(buf, buf.remaining());
	}
	
	public static int[] asInts(IntBuffer buf, int count)
	{
		int[] ret = new int[Math.min(count, buf.remaining())];
		
		buf.get(ret, buf.position(), ret.length);
		
		return ret;
	}
	
	public static long[] asLongs(IArray<? extends Number> nums)
	{
		long[] ret = new long[nums.size()];
		
		for (int c = 0; c < ret.length; c++)
		{
			ret[c] = nums.get(c).longValue();
			
		}
		
		return ret;
	}
	
	public static long[] asLongs(List<? extends Number> nums)
	{
		long[] ret = new long[nums.size()];
		
		for (int c = 0; c < nums.size(); c++)
		{
			ret[c] = nums.get(c).longValue();
			
		}
		
		return ret;
	}
	
	public static long[] asLongs(LongBuffer buf)
	{
		return asLongs(buf, buf.remaining());
	}
	
	public static long[] asLongs(LongBuffer buf, int count)
	{
		long[] ret = new long[Math.min(count, buf.remaining())];
		
		buf.get(ret, buf.position(), ret.length);
		
		return ret;
	}
	
	public static short[] asShorts(IArray<? extends Number> nums)
	{
		short[] ret = new short[nums.size()];
		
		for (int c = 0; c < ret.length; c++)
		{
			ret[c] = nums.get(c).shortValue();
			
		}
		
		return ret;
	}
	
	public static short[] asShorts(List<? extends Number> nums)
	{
		short[] ret = new short[nums.size()];
		
		for (int c = 0; c < nums.size(); c++)
		{
			ret[c] = nums.get(c).shortValue();
			
		}
		
		return ret;
	}
	
	public static short[] asShorts(ShortBuffer buf)
	{
		return asShorts(buf, buf.remaining());
	}
	
	public static short[] asShorts(ShortBuffer buf, int count)
	{
		short[] ret = new short[Math.min(count, buf.remaining())];
		
		buf.get(ret, buf.position(), ret.length);
		
		return ret;
	}
	
}
