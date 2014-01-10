
package com.elusivehawk.engine.math;

import com.elusivehawk.engine.util.Buffer;

/**
 * 
 * Helper class for integer conversion (i.e. long -> byte[], and vice versa).
 * 
 * @author Elusivehawk
 */
public class BitHelper
{
	public static Byte[] createBytes(double d)
	{
		return createBytes(Double.doubleToRawLongBits(d));
	}
	
	public static Byte[] createBytes(float f)
	{
		return createBytes(Float.floatToRawIntBits(f));
	}
	
	public static Byte[] createBytes(int i)
	{
		Byte[] ret = new Byte[4];
		
		for (int c = 0; c < 4; c++)
		{
			ret[c] = (byte)(i >> (c * 8) & 0xFF);
			
		}
		
		return ret;
	}
	
	public static Byte[] createBytes(long l)
	{
		Byte[] ret = new Byte[8];
		
		for (int c = 0; c < 8; c++)
		{
			ret[c] = (byte)(l >> (c * 8) & 0xFF);
			
		}
		
		return ret;
	}
	
	public static Byte[] createBytes(short s)
	{
		Byte[] ret = new Byte[2];
		
		for (int c = 0; c < 2; c++)
		{
			ret[c] = (byte)(s >> (c * 8) & 0xFF);
			
		}
		
		return ret;
	}
	
	public static int createInt(byte... b)
	{
		if (b == null || b.length == 0)
		{
			return 0;
		}
		
		int i = 0;
		
		for (int c = 0; c < Math.min(b.length, 4); c++)
		{
			i = (i << 8) | b[c];
			
		}
		
		return i;
	}
	
	public static int createInt(short... s)
	{
		if (s == null || s.length == 0)
		{
			return 0;
		}
		
		int i = 0;
		
		for (int c = 0; c < Math.min(s.length, 2); c++)
		{
			i = (i << 16) | s[c];
			
		}
		
		return i;
	}
	
	public static int createIntFromBytes(Buffer<Byte> buf)
	{
		if (buf == null || buf.remaining() == 0)
		{
			return 0;
		}
		
		int i = 0;
		
		for (int c = 0; c < 4 && buf.remaining() > 0; c++)
		{
			i = (i << 8) | buf.next();
			
		}
		
		return i;
	}
	
	public static int createIntFromShorts(Buffer<Short> buf)
	{
		if (buf == null || buf.remaining() == 0)
		{
			return 0;
		}
		
		int i = 0;
		
		for (int c = 0; c < 2 && buf.remaining() > 0; c++)
		{
			i = (i << 16) | buf.next();
			
		}
		
		return i;
	}
	
	public static long createLong(byte... b)
	{
		if (b == null || b.length == 0)
		{
			return 0;
		}
		
		long i = 0;
		
		for (int c = 0; c < Math.min(b.length, 8); c++)
		{
			i = (i << 8) | b[c];
			
		}
		
		return i;
	}
	
	public static long createLong(short... s)
	{
		if (s == null || s.length == 0)
		{
			return 0;
		}
		
		long i = 0;
		
		for (int c = 0; c < Math.min(s.length, 4); c++)
		{
			i = (i << 16) | s[c];
			
		}
		
		return i;
	}
	
	public static long createLongFromBytes(Buffer<Byte> buf)
	{
		if (buf == null || buf.remaining() == 0)
		{
			return 0;
		}
		
		long i = 0;
		
		for (int c = 0; c < 8 && buf.remaining() > 0; c++)
		{
			i = (i << 8) | buf.next();
			
		}
		
		return i;
	}
	
	public static long createLongFromShorts(Buffer<Short> buf)
	{
		if (buf == null || buf.remaining() == 0)
		{
			return 0;
		}
		
		long i = 0;
		
		for (int c = 0; c < 4 && buf.remaining() > 0; c++)
		{
			i = (i << 16) | buf.next();
			
		}
		
		return i;
	}
	
	public static short createShort(byte... b)
	{
		if (b == null || b.length == 0)
		{
			return 0;
		}
		
		short s = 0;
		
		for (int c = 0; c < Math.min(2, b.length); c++)
		{
			s = (short)((s << 8) | b[c]);
			
		}
		
		return s;
	}
	
	public static short createShort(Buffer<Byte> buf)
	{
		if (buf == null || buf.remaining() == 0)
		{
			return 0;
		}
		
		short s = 0;
		
		for (int c = 0; c < 2 & buf.remaining() > 0; c++)
		{
			s = (short)((s << 8) | buf.next());
			
		}
		
		return s;
	}
	
	public static Short[] createShorts(double d)
	{
		return createShorts(Double.doubleToRawLongBits(d));
	}
	
	public static Short[] createShorts(float f)
	{
		return createShorts(Float.floatToRawIntBits(f));
	}
	
	public static Short[] createShorts(int i)
	{
		Short[] ret = new Short[2];
		
		for (int c = 0; c < 2; c++)
		{
			ret[c] = (short)(i >> (c * 16) & 0xFF);
			
		}
		
		return ret;
	}
	
	public static Short[] createShorts(long l)
	{
		Short[] ret = new Short[4];
		
		for (int c = 0; c < 4; c++)
		{
			ret[c] = (short)(l >> (c * 16) & 0xFF);
			
		}
		
		return ret;
	}
	
}
